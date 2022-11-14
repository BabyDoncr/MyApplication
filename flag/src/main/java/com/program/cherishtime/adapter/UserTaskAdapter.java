package com.program.cherishtime.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.program.cherishtime.R;
import com.program.cherishtime.activities.TaskActivity;
import com.program.cherishtime.bean.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class UserTaskAdapter extends RecyclerView.Adapter<UserTaskAdapter.ViewHolder> {

    public static final int TYPE_MINE = 1;

    public static final int TYPE_OTHERS = 2;

    private int mType;

    private Context mContext;

    private List<Task> mTaskList;

    public UserTaskAdapter(int type) {
        mTaskList = new ArrayList<>();
        mType = type;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = mTaskList.get(position);
        holder.bindTo(mContext, task, mType);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        @BindView(R.id.user_task_tag)
        TextView userTaskTag;
        @BindView(R.id.user_task_image)
        CircleImageView userTaskImage;
        @BindView(R.id.user_task_title)
        TextView userTaskTitle;
        @BindView(R.id.user_task_time)
        TextView userTaskTime;
        @BindView(R.id.user_task_detail)
        TextView userTaskDetail;
        @BindView(R.id.user_task_id)
        TextView userTaskId;

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

        void bindTo(Context context, Task task, int type) {
            int id = task.getId();
            Glide.with(context).load(images[id % 10]).into(userTaskImage);
            userTaskTitle.setText(String.format("任务：%s", task.getTitle()));
            userTaskTime.setText(String.format("时间：%s - %s", task.getStartTime(), task.getEndTime()));
            userTaskDetail.setText(String.format("详情：%s", task.getContent()));

            if (task.getType() == 1)
                userTaskTag.setText("休息");
            if (task.getType() == 2)
                userTaskTag.setText("学习");
            if (task.getType() == 3)
                userTaskTag.setText("运动");
            userTaskId.setText(String.valueOf(task.getId()));

            if (type == TYPE_MINE) {
                cardView.setOnClickListener(view -> TaskActivity.startAction(context, TaskActivity.TYPE_TASK_CARD_EDIT, id));
            } else {
                cardView.setOnClickListener(view -> TaskActivity.startAction(context, TaskActivity.TYPE_TASK_CARD_COPY, id));
            }
        }
    }
}
