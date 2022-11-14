package com.program.cherishtime.fragment;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.program.cherishtime.R;
import com.program.cherishtime.adapter.PickTaskAdapter;
import com.program.cherishtime.adapter.TaskAdapter;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.bean.Task;
import com.program.cherishtime.data.ObservableTaskDb;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class TaskFragment extends BaseFragment {

    @BindView(R.id.task_recyclerView)
    RecyclerView taskRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.add_task)
    FloatingActionButton addTask;
    @BindView(R.id.task_tip_layout)
    LinearLayout taskTipLayout;

//    private TaskAdapter mTaskAdapter;
//    private PickTaskAdapter mPickAdapter;
    private TaskAdapter mTaskAdapter;
    private PickTaskAdapter mPickAdapter;

//    private ObservableTaskDb mPickedTaskDb;
//    private ObservableTaskDb mPickTaskDb;
    private ObservableTaskDb mPickedTaskDb;
    private ObservableTaskDb mPickTaskDb;

    private List<Disposable> mDisposables = new ArrayList<>();

    private int uid;

    public TaskFragment(int uid) {
        this.uid = uid;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.task_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        taskRecyclerView.setLayoutManager(layoutManager);
        mTaskAdapter = new TaskAdapter();
        taskRecyclerView.setAdapter(mTaskAdapter);

        addTask.setOnClickListener(view -> pickUpTaskDialog());

        swipeRefresh.setColorSchemeColors(Color.LTGRAY);
        swipeRefresh.setOnRefreshListener(this::fetchPickedTaskUpdates);
    }

    private void pickUpTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.pick_task_dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        RecyclerView recyclerView = view.findViewById(R.id.pick_task_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        mPickAdapter = new PickTaskAdapter(dialog);
        recyclerView.setAdapter(mPickAdapter);

        Disposable disposable = mPickTaskDb.getObservable(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setPickTaskData);

        fetchPickTaskUpdates();
        mDisposables.add(disposable);

        dialog.setOnDismissListener(dialogInterface -> {
            fetchPickedTaskUpdates();
            fetchPickTaskUpdates();
        });
    }

    private void fetchPickTaskUpdates() {
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryUserTask(uid, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                    mPickTaskDb.insertPickedTaskList(tasks, 1);
                    LogUtil.d("TaskActivity", "fetchPickTaskUpdates success!");
                }, this::onError);
        mDisposables.add(disposable);
    }

    private void fetchPickedTaskUpdates() {
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryUserTask(uid, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> mPickedTaskDb.insertPickedTaskList(tasks, 2), this::onError);
        mDisposables.add(disposable);
    }

    private void onError(Throwable throwable) {
        LogUtil.d("TaskFragment", throwable.getMessage());
    }

    private void setPickTaskData(List<Task> taskList) {
        mPickAdapter.setTaskInfos(taskList);
    }

    @Override
    public void onResume() {
        super.onResume();

        mPickTaskDb = new ObservableTaskDb(mContext);
        mPickedTaskDb = new ObservableTaskDb(mContext);

        Disposable disposable = mPickedTaskDb.getObservable(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setData);

        fetchPickedTaskUpdates();
        mDisposables.add(disposable);

        Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                .queryPickTaskNum(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showTipOrNot, this::onError);
        mDisposables.add(subscribe);

    }

    private void showTipOrNot(Msg msg) {
        if (msg.getCode() == 1) {
            if (Integer.valueOf(msg.getMsg()) == 0) {
                taskTipLayout.setVisibility(View.VISIBLE);
            } else {
                taskTipLayout.setVisibility(View.GONE);
            }
        }
    }

    private void setData(ArrayList<Task> tasks) {
        mTaskAdapter.setTaskInfos(tasks);
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onDestroy() {
        for (Disposable disposable : mDisposables) {
            if (disposable != null)
                disposable.dispose();
        }
        super.onDestroy();
    }
}
