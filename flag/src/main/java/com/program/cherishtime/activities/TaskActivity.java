package com.program.cherishtime.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.program.cherishtime.R;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.bean.Task;
import com.program.cherishtime.data.ObservableTaskDb;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.service.AlarmService;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.MyApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class TaskActivity extends BaseActivity {

    public static final int TYPE_TASK_CARD_EDIT = 1;
    public static final int TYPE_TASK_CARD_SHOW = 2;
    public static final int TYPE_TASK_CARD_COPY = 3;
    public static final int TYPE_TASK_CARD_HISTORY = 4;

    @BindView(R.id.task_image_view)
    ImageView taskImageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.task_id)
    TextView taskId;
    @BindView(R.id.task_time)
    TextView taskTime;
    @BindView(R.id.task_desc)
    TextView taskDesc;
    @BindView(R.id.give_up_fab)
    FloatingActionButton giveUpFab;
    @BindView(R.id.task_remarks)
    TextView taskRemarks;
    @BindView(R.id.task_points)
    TextView taskPoints;
    @BindView(R.id.task_edit_layout)
    LinearLayout taskEditLayout;
    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.date_ll)
    LinearLayout dateLl;

    private int tid;
    private int type;

    private Task mTask;

    private ObservableTaskDb mTaskDb;

    private List<Disposable> mDisposables = new ArrayList<>();

    private int[] images = {
            R.drawable.spongebob,
            R.drawable.img1, R.drawable.img2, R.drawable.img3,
            R.drawable.img4, R.drawable.img5, R.drawable.img6,
            R.drawable.img7, R.drawable.img8, R.drawable.img9
    };

    public static void startAction(Context context, int type, int tid) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(DataUtil.TYPE, type);
        intent.putExtra(DataUtil.TASK_ID, tid);
        context.startActivity(intent);
    }

    public static void startAction(Context context, int type, int tid, long date) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(DataUtil.TYPE, type);
        intent.putExtra(DataUtil.TASK_ID, tid);
        intent.putExtra("date", date);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

        LogUtil.d("TaskActivity", "onCreate");

        tid = getIntent().getIntExtra(DataUtil.TASK_ID, 1);

        type = getIntent().getIntExtra(DataUtil.TYPE, 3);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        LogUtil.d("TaskActivity", "onResume");

        mTaskDb = new ObservableTaskDb(this);

        Disposable disposable = mTaskDb.getTask(tid, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setData);

        fetchUpdates();
        mDisposables.add(disposable);
    }

    private void fetchUpdates() {
        LogUtil.d("TaskFragment", "fetchUpdates");
        SharedPreferences sp = getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int uid = sp.getInt(DataUtil.USER_ID, 0);
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryTaskById2(uid, tid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(task -> mTaskDb.insertTask(task, type), this::onError);
        mDisposables.add(disposable);
    }

    private void onError(Throwable throwable) {
        LogUtil.d("TaskActivity", throwable.getMessage());
    }

    private void setData(Task task) {
        collapsingToolbar.setTitle("任务：" + task.getTitle());
        Glide.with(this).load(images[task.getId() % 10]).into(taskImageView);
        taskId.setText(String.valueOf(task.getId()));
        taskTitle.setText(task.getTitle());
        taskDesc.setText(task.getContent());
        taskTime.setText(String.format("%s - %s", task.getStartTime(), task.getEndTime()));
        taskRemarks.setText(String.format(Locale.CHINA, "任务期间玩机次数【%d】", 0));
        taskPoints.setText(String.valueOf(10));

        switch (type) {
            case TYPE_TASK_CARD_COPY:
                dateLl.setVisibility(View.GONE);
                break;
            case TYPE_TASK_CARD_HISTORY:
                long hDate = getIntent().getLongExtra("date", 0);
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                taskDate.setText(format.format(hDate));
                break;
            default:
                int date = task.getTaskDate() % 10000;
                taskDate.setText(String.format(Locale.CHINA, "%04d/%02d/%02d", task.getTaskDate() / 10000, date / 100, date % 100));
                break;
        }

        tid = task.getId();
        LogUtil.d("TaskActivity", "tid = " + tid);

        mTask = task;
    }


    private void init() {
        if (type == TYPE_TASK_CARD_EDIT) {
            taskEditLayout.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.ic_done).into(giveUpFab);
            taskEditLayout.setOnClickListener(view -> {
                Intent intent = new Intent(TaskActivity.this, EditTaskActivity.class);
                intent.putExtra(DataUtil.TASK_ID, tid);
                startActivityForResult(intent, 0x001);
            });
            giveUpFab.setOnClickListener(view -> pickTask());
        } else if (type == TYPE_TASK_CARD_SHOW) {
            taskEditLayout.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.ic_give_up).into(giveUpFab);
            giveUpTask();
        } else if (type == TYPE_TASK_CARD_COPY) {
            taskEditLayout.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.ic_copy).into(giveUpFab);
            giveUpFab.setOnClickListener(view -> Toast.makeText(this, "copy", Toast.LENGTH_SHORT).show());
        } else {
            taskEditLayout.setVisibility(View.GONE);
            giveUpFab.setVisibility(View.INVISIBLE);
        }
    }

    private void pickTask() {
        String appKey = MyApplication.getContext().getString(R.string.updateTaskAppKey);
        SharedPreferences sp = getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int uid = sp.getInt(DataUtil.USER_ID, 0);
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .updateUserTaskComplete(appKey, uid, tid, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        mDisposables.add(disposable);
    }

    private void onSuccess(Msg msg) {
        if (msg.getCode() == 1) {
            Toast.makeText(this, "接取成功！", Toast.LENGTH_SHORT).show();
            Intent intentToService = new Intent(TaskActivity.this, AlarmService.class);
            intentToService.putExtra(DataUtil.TASK_DATA, mTask);
            intentToService.putExtra(DataUtil.SET_ALARM, true);
            startService(intentToService);
        }
    }

    private void giveUpTask() {
        giveUpFab.setOnClickListener(view ->
                Snackbar.make(view, "确定放弃？", Snackbar.LENGTH_LONG)
                        .setAction("确定", v -> {
                            deleteTask();
                            Toast.makeText(this, "放弃任务！", Toast.LENGTH_SHORT).show();
                        })
                        .show());
    }

    private void deleteTask() {
        /* ... */
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Disposable disposable : mDisposables) {
            if (disposable != null)
                disposable.dispose();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0x001 && resultCode == RESULT_OK) {
            tid = data.getIntExtra(DataUtil.TASK_ID, 1);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
