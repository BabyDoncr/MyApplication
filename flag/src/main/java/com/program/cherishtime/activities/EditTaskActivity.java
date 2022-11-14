package com.program.cherishtime.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.program.cherishtime.R;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.bean.Task;
import com.program.cherishtime.data.ObservableTaskDb;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.MyApplication;
import com.program.cherishtime.utils.StatusBarUtil;
import com.program.cherishtime.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class EditTaskActivity extends BaseActivity {

    @BindView(R.id.edit_task_title)
    TextView editTaskTitle;
    @BindView(R.id.edit_task_title_layout)
    RelativeLayout editTaskTitleLayout;

    @BindView(R.id.edit_task_desc)
    TextView editTaskDesc;
    @BindView(R.id.edit_task_desc_layout)
    RelativeLayout editTaskDescLayout;

    @BindView(R.id.edit_task_start)
    TextView editTaskStart;
    @BindView(R.id.edit_task_start_layout)
    RelativeLayout editTaskStartLayout;

    @BindView(R.id.edit_task_end)
    TextView editTaskEnd;
    @BindView(R.id.edit_task_end_layout)
    RelativeLayout editTaskEndLayout;

    @BindView(R.id.edit_task_img)
    CircleImageView editTaskImg;

    private String startTime;
    private String endTime;

    private ObservableTaskDb mTaskDb;

    private List<Disposable> mDisposables = new ArrayList<>();

    private int tid;

    private Task mTask;

    private int[] images = {
            R.drawable.spongebob,
            R.drawable.img1, R.drawable.img2, R.drawable.img3,
            R.drawable.img4, R.drawable.img5, R.drawable.img6,
            R.drawable.img7, R.drawable.img8, R.drawable.img9
    };


    public static void startAction(Context context, int id) {
        Intent intent = new Intent(context, EditTaskActivity.class);
        intent.putExtra(DataUtil.TASK_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        ButterKnife.bind(this);

        StatusBarUtil.setStatusBarDarkTheme(this, false);

        tid = getIntent().getIntExtra(DataUtil.TASK_ID, 1);

        Toolbar toolbar = findViewById(R.id.toolbar);
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
        mTaskDb = new ObservableTaskDb(this);
        Disposable disposable = mTaskDb.getTask(tid, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setData, this::onError);

        fetchUpdates(tid);
        mDisposables.add(disposable);
    }

    private void fetchUpdates(int tid) {
        SharedPreferences sp = getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int uid = sp.getInt(DataUtil.USER_ID, 0);
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryTaskById2(uid, tid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(task -> mTaskDb.insertTask(task, 3), this::onError);
        mDisposables.add(disposable);
    }

    private void setData(Task task) {
        Glide.with(this).load(images[task.getId() % 10]).into(editTaskImg);
        editTaskTitle.setText(task.getTitle());
        editTaskDesc.setText(task.getContent());
        startTime = task.getStartTime();
        editTaskStart.setText(startTime);
        endTime = task.getEndTime();

        long start = TimeUtil.getMilliseconds(startTime.split(":")[0], startTime.split(":")[1]);
        long end = TimeUtil.getMilliseconds(endTime.split(":")[0], endTime.split(":")[1]);
        if (end < start) {
            endTime = TimeUtil.milliseconds2Time(start + 60 * 60 * 1000);
        }
        editTaskEnd.setText(endTime);

        mTask = task;
        LogUtil.d("TaskActivity", mTask.toString());
    }

    private void onError(Throwable throwable) {
        LogUtil.d("TaskActivity", throwable.getMessage());
    }

    private void init() {
        editTaskTitleLayout.setOnClickListener(view -> editTitleDialog());
        editTaskDescLayout.setOnClickListener(view -> editContentDialog());
        editTaskStartLayout.setOnClickListener(view -> editStartDialog());
        editTaskEndLayout.setOnClickListener(view -> editEndDialog());
    }

    private void editTitleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_task_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView editTitle = (TextView) view.findViewById(R.id.edit_task_dialog_title);
        editTitle.setText("任务名称");

        RelativeLayout editNickName = (RelativeLayout) view.findViewById(R.id.edit_task_dialog_name);
        editNickName.setVisibility(View.VISIBLE);

        EditText editTitleET = (EditText) view.findViewById(R.id.edit_task_dialog_name_et);
        ImageView editClear = (ImageView) view.findViewById(R.id.edit_task_dialog_name_clear);

        editClear.setOnClickListener(v -> editTitleET.setText(""));

        LinearLayout editOk = (LinearLayout) view.findViewById(R.id.edit_task_dialog_ok);
        editOk.setOnClickListener(v -> {
            if (!editTitleET.getText().toString().equals("")){
                updateTask("title", editTitleET.getText().toString());
            }
            dialog.dismiss();
        });
    }

    private void editContentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_task_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView editTitle = (TextView) view.findViewById(R.id.edit_task_dialog_title);
        editTitle.setText("任务细节");

        RelativeLayout editNickName = (RelativeLayout) view.findViewById(R.id.edit_task_dialog_desc);
        editNickName.setVisibility(View.VISIBLE);

        EditText editDescET = (EditText) view.findViewById(R.id.edit_task_dialog_desc_et);
        ImageView editClear = (ImageView) view.findViewById(R.id.edit_task_dialog_desc_et_clear);

        editClear.setOnClickListener(v -> editDescET.setText(""));

        LinearLayout editOk = (LinearLayout) view.findViewById(R.id.edit_task_dialog_ok);
        editOk.setOnClickListener(v -> {
            if (!editDescET.getText().toString().equals("")) {
                updateTask("content", editDescET.getText().toString());
            }
            dialog.dismiss();
        });
    }

    private void editStartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_task_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView editTitle = (TextView) view.findViewById(R.id.edit_task_dialog_title);
        editTitle.setText("开始时间");

        LinearLayout editStart = (LinearLayout) view.findViewById(R.id.edit_task_dialog_start);
        editStart.setVisibility(View.VISIBLE);

        TimePicker timePicker = (TimePicker) view.findViewById(R.id.edit_task_dialog_start_pick);

        startTime = editTaskStart.getText().toString();
        int h = Integer.valueOf(startTime.split(":")[0]);
        int m = Integer.valueOf(startTime.split(":")[1]);

        timePicker.setHour(h);
        timePicker.setMinute(m);
        timePicker.setOnTimeChangedListener((timePicker1, hour, minute) ->
                startTime = String.format(Locale.CHINA, "%02d:%02d", hour, minute));

        LinearLayout editOk = (LinearLayout) view.findViewById(R.id.edit_task_dialog_ok);
        editOk.setOnClickListener(v -> {
            if (!startTime.equals("")) {
                updateTask("startTime", startTime);
            }
            dialog.dismiss();
        });
    }


    private void editEndDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_task_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView editTitle = (TextView) view.findViewById(R.id.edit_task_dialog_title);
        editTitle.setText("结束时间");

        LinearLayout editStart = (LinearLayout) view.findViewById(R.id.edit_task_dialog_end);
        editStart.setVisibility(View.VISIBLE);

        TimePicker timePicker = (TimePicker) view.findViewById(R.id.edit_task_dialog_end_pick);

        endTime = editTaskEnd.getText().toString();
        int h = Integer.valueOf(endTime.split(":")[0]);
        int m = Integer.valueOf(endTime.split(":")[1]);

        timePicker.setHour(h);
        timePicker.setMinute(m);
        timePicker.setOnTimeChangedListener((timePicker1, hour, minute) ->
                endTime = String.format(Locale.CHINA, "%02d:%02d", hour, minute));

        LinearLayout editOk = (LinearLayout) view.findViewById(R.id.edit_task_dialog_ok);
        editOk.setOnClickListener(v -> {
            if (!endTime.equals("")) {
                long start = TimeUtil.getMilliseconds(startTime.split(":")[0], startTime.split(":")[0]);
                long end = TimeUtil.getMilliseconds(endTime.split(":")[0], endTime.split(":")[0]);
                if (end <= start) {
                    Toast.makeText(this, "结束时间不能小于开始时间", Toast.LENGTH_SHORT).show();
                } else {
                    updateTask("endTime", endTime);
                }
            }
            dialog.dismiss();
        });
    }

    private void updateTask(String key, Object value) {
        Map<String, Object> params = new HashMap<>();
        params.put("appKey", MyApplication.getContext().getString(R.string.updateTaskAppKey));

        SharedPreferences sp = getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int uid = sp.getInt(DataUtil.USER_ID, 0);

        params.put("uid", uid);
        params.put("tid", tid);
        params.put("type", mTask.getType());
        LogUtil.d("TaskActivity", "type = " + mTask.getType());
        if (tid <= 14) {
            params.put("startTime", mTask.getStartTime());
            params.put("endTime", mTask.getEndTime());
            params.put("title", mTask.getTitle());
            params.put("content", mTask.getContent());
            params.put("del", mTask.getDel());
        }

        params.put(key, value);

        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .updateUserTask(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateSuccess, this::onError);
        mDisposables.add(disposable);
    }

    private void updateSuccess(Msg msg) {
        if (msg.getCode() == 1) {
            int taskId = Integer.valueOf(msg.getMsg());
            tid = taskId;
            fetchUpdates(taskId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            LogUtil.d("TaskActivity", "edited tid = " + tid);
            Intent intent = new Intent();
            intent.putExtra(DataUtil.TASK_ID, tid);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(DataUtil.TASK_ID, tid);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Disposable disposable : mDisposables) {
            if (disposable != null)
                disposable.dispose();
        }
        Intent intent = new Intent();
        intent.putExtra(DataUtil.TASK_ID, tid);
        setResult(RESULT_OK, intent);
        finish();
    }

}
