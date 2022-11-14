package com.program.cherishtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.program.cherishtime.R;
import com.program.cherishtime.activities.TaskActivity;
import com.program.cherishtime.bean.History;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.bean.Task;
import com.program.cherishtime.bean.UserInfo;
import com.program.cherishtime.data.ObservableHistoryDb;
import com.program.cherishtime.data.ObservableUserInfoDb;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.service.AlarmService;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.MyApplication;
import com.program.cherishtime.utils.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context mContext;

    private List<Task> mTaskList;

    public TaskAdapter() {
        mTaskList = new ArrayList<>();
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = mTaskList.get(position);
        holder.bindTo(mContext, task);
        holder.taskGiveUp.setOnClickListener(view ->
                Snackbar.make(view, "确定放弃？", Snackbar.LENGTH_LONG)
                        .setAction("确定", v -> {
                            actionTask(task.getId(), position, 3);
                            Toast.makeText(mContext, "放弃任务！", Toast.LENGTH_SHORT).show();
                        })
                        .show());

        holder.taskDone.setOnClickListener(view -> {
            String endTime = task.getEndTime();

            int hour = Integer.valueOf(endTime.split(":")[0]);
            int minute = Integer.valueOf(endTime.split(":")[1]);
            long given = TimeUtil.getMilliseconds(hour, minute);
            long now = TimeUtil.getMilliseconds();
            if (now - given < 0) {
                Toast.makeText(mContext, "未到打卡时间", Toast.LENGTH_SHORT).show();
            } else if (now - given > 10 * 60 * 1000) {
                Toast.makeText(mContext, "打卡时间已过，视作超时完成", Toast.LENGTH_SHORT).show();
                actionTask(task.getId(), position, 2);
            } else {
                actionTask(task.getId(), position, 2);
                Toast.makeText(mContext, "完成任务！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressWarnings("unused")
    private void actionTask(int tid, int position, int complete) {
        String appKey = MyApplication.getContext().getString(R.string.updateTaskAppKey);
        SharedPreferences sp = mContext.getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        int uid = sp.getInt(DataUtil.USER_ID, 0);
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .updateUserTaskComplete(appKey, uid, tid, complete)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(msg -> onSuccess(msg, position, complete, uid), this::onError);

        ObservableHistoryDb historyDb = new ObservableHistoryDb(mContext);
        History history = new History();
        history.setUid(uid);
        history.setTid(tid);
        history.setTitle(mTaskList.get(position).getTitle());
        history.setComplete(complete);
        history.setDel(0);
        history.setDateTime(System.currentTimeMillis());
        historyDb.insertHistory(history);

        int count = sp.getInt(DataUtil.TASK_HISTORY_COUNT, 4);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(DataUtil.TASK_HISTORY_COUNT, count + 1);
        editor.apply();

        if (complete == 3) {
            Task task = mTaskList.get(position);
            Intent intentToAlarmService = new Intent(mContext, AlarmService.class);
            intentToAlarmService.putExtra(DataUtil.TASK_DATA, task);
            intentToAlarmService.putExtra(DataUtil.SET_ALARM, false);
            mContext.startService(intentToAlarmService);
        }
    }

    @SuppressWarnings("unused")
    private void updateDetail(UserInfo userInfo, int complete, int uid, int position) {
        int point = userInfo.getPoint();
        int exp = userInfo.getExp();
        int level = userInfo.getLevel();
        float expRate = 1.0f;
        float pointRate = 1.0f;
        if (complete == 2) {
            point = point + Math.round(10 * pointRate);
            exp = exp + Math.round(10 * expRate);
        } else {
            point = point + Math.round(2 * pointRate);
            exp = exp + Math.round(2 * expRate);
        }
        if (exp > 1000) {
            exp = exp % 1000;
            level++;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", uid);
        params.put("point", point);
        params.put("exp", exp);
        params.put("level", level);


        Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                .updateDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(msg -> {
                    if (msg.getCode() == 1) {
                        LogUtil.d("TaskFragment", "task update success");
                        mTaskList.remove(position);
                        notifyDataSetChanged();
                    }
                }, this::onError);
    }

    @SuppressWarnings("unused")
    private void onSuccess(Msg msg, int position, int complete, int uid) {
        if (msg.getCode() == 1) {
            ObservableUserInfoDb infoDb = new ObservableUserInfoDb(mContext);
            Disposable subscribe = infoDb.getObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userInfo -> updateDetail(userInfo, complete, uid, position));
        }
    }

    private void onError(Throwable throwable) {
        LogUtil.d("TaskActvivty", throwable.getMessage());
    }


    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        @BindView(R.id.task_circleImageView)
        CircleImageView taskImage;
        @BindView(R.id.task_tv_title)
        TextView taskTitle;
        @BindView(R.id.task_tv_time)
        TextView taskTime;
        @BindView(R.id.task_tv_detail)
        TextView taskDetail;
        @BindView(R.id.task_iv_done)
        ImageView taskDone;
        @BindView(R.id.task_iv_give_up)
        ImageView taskGiveUp;
        @BindView(R.id.task_id_tv)
        TextView taskId;
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

        void bindTo(Context context, Task task) {
            int id = task.getId();
            Glide.with(context).load(images[id % 10]).into(taskImage);

            taskTitle.setText(String.format("任务：%s", task.getTitle()));
            taskId.setText(String.valueOf(task.getId()));
            taskTime.setText(String.format("时间：%s - %s", task.getStartTime(), task.getEndTime()));
            taskDetail.setText(String.format("详情：%s", task.getContent()));
            int date = task.getTaskDate() % 10000;
            taskDate.setText(String.format(Locale.CHINA, "%02d-%02d", date / 100 , date % 100));
            cardView.setOnClickListener(view -> TaskActivity.startAction(context, TaskActivity.TYPE_TASK_CARD_SHOW, id));
        }
    }
}
