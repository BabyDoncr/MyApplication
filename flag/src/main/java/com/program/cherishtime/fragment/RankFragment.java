package com.program.cherishtime.fragment;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.program.cherishtime.R;
import com.program.cherishtime.adapter.RankAdapter;
import com.program.cherishtime.bean.Rank;
import com.program.cherishtime.data.ObservableRankDb;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class RankFragment extends BaseFragment {

    @BindView(R.id.rank_recycler_view)
    RecyclerView rankRecyclerView;
    @BindView(R.id.rank_swipe_refresh)
    SwipeRefreshLayout rankSwipeRefresh;
    @BindView(R.id.begin_rank)
    FloatingActionButton beginRank;
    @BindView(R.id.rank_my_rank)
    TextView rankNum;
    @BindView(R.id.rank_my_point)
    TextView rankPoint;
    @BindView(R.id.rank_days)
    TextView rankDays;

    private RankAdapter adapter;

    private ObservableRankDb mRankDb;

    private List<Disposable> mDisposables = new ArrayList<>();

    private int uid;

    public RankFragment(int uid) {
        this.uid = uid;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.rank_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

//        LogUtil.d("RankFragment", "user id = " + uid);

        mRankDb = new ObservableRankDb(mContext);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rankRecyclerView.setLayoutManager(layoutManager);
        adapter = new RankAdapter(uid);
        rankRecyclerView.setAdapter(adapter);

        beginRank.setOnClickListener(view -> Toast.makeText(mContext, "尚未开启！", Toast.LENGTH_SHORT).show());

        rankSwipeRefresh.setColorSchemeColors(Color.LTGRAY);
        rankSwipeRefresh.setOnRefreshListener(this::fetchUpdates);

        onloadRank();
    }

    private void onloadRank() {
        LogUtil.d("RankFragment", "user id = " + uid);

        Disposable disposable = NetworkWrapper.getInstance()
                .getDesertApiClient()
                .queryMyRankInfo(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::queryMyRankSuccess, this::onError);
        mDisposables.add(disposable);
    }

    private void queryMyRankSuccess(Rank rank) {
        LogUtil.d("RankFragment", rank.toString());
        LogUtil.d("RankFragment", "queryMyRankSuccess");
        rankNum.setText(String.valueOf(rank.getNum()));
        rankPoint.setText(String.valueOf(rank.getPoint()));
        rankDays.setText(String.format(Locale.CHINA, "已坚持：%d天", rank.getDays()));
    }


    private void onError(Throwable throwable) {
        LogUtil.d("RankFragment", throwable.toString());
        if (rankSwipeRefresh.isRefreshing()) {
            rankSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Disposable disposable = mRankDb.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setRanksData);

        fetchUpdates();
        mDisposables.add(disposable);

    }

    private void fetchUpdates() {
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryRankList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mRankDb::insertRankList, this::onError, this::onComplete);

        mDisposables.add(disposable);

    }

    private void onComplete() {
        if (rankSwipeRefresh.isRefreshing()) {
            rankSwipeRefresh.setRefreshing(false);
        }
    }

    private void setRanksData(ArrayList<Rank> ranks) {
        adapter.setRanks(ranks);
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
