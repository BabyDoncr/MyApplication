package com.program.cherishtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.program.cherishtime.R;
import com.program.cherishtime.activities.TaskActivity;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.bean.Task;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.service.AlarmService;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class PickTaskAdapter extends RecyclerView.Adapter<PickTaskAdapter.ViewHolder> {

    private Context mContext;

    private List<Task> mTaskList;

    private AlertDialog mDialog;

    public PickTaskAdapter(AlertDialog dialog) {
        mTaskList = new ArrayList<>();
        mDialog = dialog;
    }

    public void setTaskInfos(List<Task> tasks) {
        mTaskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = mTaskList.get(position);
        holder.bindTo(mContext, task, mDialog);
        holder.taskInfoAction.setOnClickListener(view -> {
            Toast.makeText(mContext, "接取成功", Toast.LENGTH_SHORT).show();
            pickTask(task.getId(), position);
        });
    }

    @SuppressWarnings("unused")
    private void pickTask(int tid, int position) {
        String appKey = MyApplication.getContext().getString(R.string.updateTaskAppKey);
        SharedPreferences sp = mContext.getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        int uid = sp.getInt(DataUtil.USER_ID, 0);
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .updateUserTaskComplete(appKey, uid, tid, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(msg -> onSuccess(msg, position), this::onError);
    }

    private void onSuccess(Msg msg, int position) {
        if (msg.getCode() == 1) {
            Task task = mTaskList.get(position);
            mTaskList.remove(position);
            notifyDataSetChanged();

            Intent intentToService = new Intent(mContext, AlarmService.class);
            intentToService.putExtra(DataUtil.TASK_DATA, task);
            intentToService.putExtra(DataUtil.SET_ALARM, true);
            mContext.startService(intentToService);
        }
    }

    private void onError(Throwable throwable) {
        LogUtil.d("TaskActivity", throwable.getMessage());
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        @BindView(R.id.task_info_image)
        CircleImageView taskInfoImage;
        @BindView(R.id.task_info_title)
        TextView taskInfoTitle;
        @BindView(R.id.task_info_time)
        TextView taskInfoTime;
        @BindView(R.id.task_info_content)
        TextView taskInfoContent;
        @BindView(R.id.task_info_action)
        LinearLayout taskInfoAction;
        @BindView(R.id.task_info_tag)
        TextView taskInfoTag;
        @BindView(R.id.task_info_id)
        TextView taskInfoId;
        @BindView(R.id.task_date_tag)
        TextView taskDate;

        private int[] images = {
                R.drawable.spongebob,
                R.drawable.img1, R.drawable.img2, R.drawable.img3,
                R.drawable.img4, R.drawable.img5, R.drawable.img6,
                R.drawable.img7, R.drawable.img8, R.drawable.img9
        };

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            cardView = (CardView) view;
        }

        void bindTo(Context context, Task task, AlertDialog dialog) {
            int id = task.getId();
            Glide.with(context).load(images[id % 10]).into(taskInfoImage);
            taskInfoTitle.setText(String.format("任务：%s", task.getTitle()));
            taskInfoTime.setText(String.format("时间：%s - %s", task.getStartTime(), task.getEndTime()));
            taskInfoContent.setText(String.format("详情：%s", task.getContent()));

            if (task.getType() == 1)
                taskInfoTag.setText("休息");
            if (task.getType() == 2)
                taskInfoTag.setText("学习");
            if (task.getType() == 3)
                taskInfoTag.setText("运动");
            taskInfoId.setText(String.valueOf(task.getId()));

            int date = task.getTaskDate() % 10000;
            taskDate.setText(String.format(Locale.CHINA, "%02d-%02d", date / 100 , date % 100));

            cardView.setOnClickListener(view -> {
                TaskActivity.startAction(context, TaskActivity.TYPE_TASK_CARD_EDIT, id);
                dialog.dismiss();
            });
        }

    }
}
