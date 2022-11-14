package com.program.cherishtime.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.program.cherishtime.R;
import com.program.cherishtime.adapter.UserTaskAdapter;
import com.program.cherishtime.bean.Task;
import com.program.cherishtime.data.ObservableTaskDb;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class UserTaskFragment extends BaseFragment {

    @BindView(R.id.task_recyclerView)
    RecyclerView taskRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private UserTaskAdapter mTaskAdapter;

    private ObservableTaskDb mUserTaskDb;

    private List<Disposable> mDisposables = new ArrayList<>();

    private int uid;

    public UserTaskFragment(int uid) {
        this.uid = uid;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.user_task_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        SharedPreferences sp = mContext.getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        int userId = sp.getInt(DataUtil.USER_ID, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        taskRecyclerView.setLayoutManager(layoutManager);
        if (userId == uid) {
            mTaskAdapter = new UserTaskAdapter(UserTaskAdapter.TYPE_MINE);
        } else {
            mTaskAdapter = new UserTaskAdapter(UserTaskAdapter.TYPE_OTHERS);
        }
        taskRecyclerView.setAdapter(mTaskAdapter);

        swipeRefresh.setColorSchemeColors(Color.LTGRAY);
        swipeRefresh.setOnRefreshListener(this::fetchUpdates);
    }


    @Override
    public void onResume() {
        super.onResume();

        mUserTaskDb = new ObservableTaskDb(mContext);

        LogUtil.d("TaskFragment", "user id = " + uid);

        Disposable disposable = mUserTaskDb.getObservable(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setData);

        fetchUpdates();
        mDisposables.add(disposable);
    }

    private void setData(ArrayList<Task> tasks) {
        mTaskAdapter.setTaskInfos(tasks);

        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    private void fetchUpdates() {
        LogUtil.d("TaskFragment", "fetchUpdates");
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryAllUserTask(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> mUserTaskDb.insertPickedTaskList(tasks, 3), this::onError, this::onComplete);
        mDisposables.add(disposable);
    }

    private void onError(Throwable throwable) {
        LogUtil.d("TaskFragment", throwable.getMessage());
    }

    private void onComplete() {
        if (swipeRefresh.isRefreshing())
            swipeRefresh.setRefreshing(false);
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
