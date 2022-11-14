package com.program.cherishtime.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.program.cherishtime.R;
import com.program.cherishtime.activities.UserActivity;
import com.program.cherishtime.adapter.HistoryAdapter;
import com.program.cherishtime.adapter.RelationAdapter;
import com.program.cherishtime.bean.History;
import com.program.cherishtime.bean.Relation;
import com.program.cherishtime.bean.UserInfo;
import com.program.cherishtime.data.ObservableFansDb;
import com.program.cherishtime.data.ObservableFollowsDb;
import com.program.cherishtime.data.ObservableHistoryDb;
import com.program.cherishtime.data.ObservableUserInfoDb;
import com.program.cherishtime.network.DesertApiClient;
import com.program.cherishtime.network.NetworkWrapper;
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
public class MineFragment extends BaseFragment {

    @BindView(R.id.portrait)
    CircleImageView portrait;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.vip)
    ImageView vip;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.turnToMinePage)
    LinearLayout turnToMinePage;
    @BindView(R.id.my_follow_count)
    TextView myFollowCount;
    @BindView(R.id.my_follow)
    LinearLayout myFollow;
    @BindView(R.id.my_fans_count)
    TextView myFansCount;
    @BindView(R.id.my_fun)
    LinearLayout myFun;
    @BindView(R.id.history_count)
    TextView historyCount;
    @BindView(R.id.history)
    LinearLayout history;

    @BindView(R.id.follows_recycler_view)
    RecyclerView followsRecyclerView;

    @BindView(R.id.fans_recycler_view)
    RecyclerView fansRecyclerView;

    @BindView(R.id.history_recycler_view)
    RecyclerView historyRecyclerView;

    @BindView(R.id.show_follows)
    RadioButton showFollows;
    @BindView(R.id.show_fans)
    RadioButton showFans;
    @BindView(R.id.show_history)
    RadioButton showHistory;
    @BindView(R.id.show_radio_group)
    RadioGroup showRadioGroup;

    private List<Disposable> mDisposables = new ArrayList<>();

    private ObservableFollowsDb mFollowsDb;
    private ObservableFansDb mFansDb;
    private ObservableUserInfoDb mUserInfoDb;
    private ObservableHistoryDb mHistoryDb;

    private RelationAdapter mFollowsAdapter;
    private RelationAdapter mFansAdapter;
    private HistoryAdapter mHistoryAdapter;

    private int uid;

    public MineFragment(int uid) {
        this.uid = uid;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.mine_fragment, null);
        ButterKnife.bind(this, view);

        ArrayList<Relation> arrayList = new ArrayList<>();
        ArrayList<History> historyList = new ArrayList<>();

        followsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mFollowsAdapter = new RelationAdapter(arrayList, RelationAdapter.TYPE_FOLLOWS);
        mFollowsAdapter.setUserId(uid);
        followsRecyclerView.setAdapter(mFollowsAdapter);

        fansRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mFansAdapter = new RelationAdapter(arrayList, RelationAdapter.TYPE_FANS);
        mFansAdapter.setUserId(uid);
        fansRecyclerView.setAdapter(mFansAdapter);

        historyRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mHistoryAdapter = new HistoryAdapter(historyList);
        historyRecyclerView.setAdapter(mHistoryAdapter);

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.d("MineFragment", "onActivityCreated");

        LogUtil.d("MineFragment", "mine uid = " + uid);

        mFollowsDb = new ObservableFollowsDb(mContext);
        mFansDb = new ObservableFansDb(mContext);
        mUserInfoDb = new ObservableUserInfoDb(mContext);
        mHistoryDb = new ObservableHistoryDb(mContext);

        turnToMinePage.setOnClickListener(view -> UserActivity.startAction(mContext, uid,
                UserActivity.TYPE_EDIT_MY_INFORMATION));

        myFollow.setOnClickListener(view -> {
            showFollows.setChecked(true);
            fetchUpdates();
        });

        myFun.setOnClickListener(view -> {
            showFans.setChecked(true);
            fetchUpdates();
        });

        history.setOnClickListener(view -> {
            showHistory.setChecked(true);
            fetchUpdates();
        });

        init();
    }

    private void init() {
        if (showFollows.isChecked()) {
            showRecyclerView(View.VISIBLE, View.GONE, View.GONE);
            LogUtil.d("MineFragment", "follow");
            if (followsRecyclerView.getVisibility() == View.VISIBLE)
                onloadFollow();
        }
        if (showFans.isChecked()) {
            showRecyclerView(View.GONE, View.VISIBLE, View.GONE);
            LogUtil.d("MineFragment", "fan");
            if (fansRecyclerView.getVisibility() == View.VISIBLE)
                onloadFan();
        }
        if (showHistory.isChecked()) {
            showRecyclerView(View.GONE, View.GONE, View.VISIBLE);
            LogUtil.d("MineFragment", "history");
            if (historyRecyclerView.getVisibility() == View.VISIBLE)
                onloadHistory();
        }

        showRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.show_follows:
                    LogUtil.d("MineFragment", "follow");
                    showRecyclerView(View.VISIBLE, View.GONE, View.GONE);
                    onloadFollow();
                    break;
                case R.id.show_fans:
                    LogUtil.d("MineFragment", "fan");
                    showRecyclerView(View.GONE, View.VISIBLE, View.GONE);
                    onloadFan();
                    break;
                case R.id.show_history:
                    LogUtil.d("MineFragment", "history");
                    showRecyclerView(View.GONE, View.GONE, View.VISIBLE);
                    onloadHistory();
                    break;
                default:
            }
        });
    }

    private void showRecyclerView(int follow, int fan, int history) {
        followsRecyclerView.setVisibility(follow);
        fansRecyclerView.setVisibility(fan);
        historyRecyclerView.setVisibility(history);
    }

    private void onloadFollow() {
        Disposable subscribe = mFollowsDb.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setFollowsData);

        updateFollowsData();
        mDisposables.add(subscribe);
    }

    private void onloadFan() {
        Disposable disposable = mFansDb.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setFansData);

        updateFansData();
        mDisposables.add(disposable);
    }

    private void onloadHistory() {
        Disposable subscribe = mHistoryDb.getObservable(uid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setHistoryData);

        mDisposables.add(subscribe);
    }

    private void setHistoryData(ArrayList<History> histories) {
        if (histories != null)
            mHistoryAdapter.setHistories(histories);
    }


    private void updateFollowsData() {
        LogUtil.d("MineFragment", "updateFollowsData");
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryFollowsInfoByUserId(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mFollowsDb::insertRelationList, this::onFail1);
        mDisposables.add(disposable);
    }

    private void updateFansData() {
        LogUtil.d("MineFragment", "updateFans" +
                "Data");
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryFansInfoByUserId(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mFansDb::insertRelationList, this::onFail2);
        mDisposables.add(disposable);
    }

    private void onFail1(Throwable throwable) {
        LogUtil.d("MineFragment", "throwable follow - " + throwable);
    }

    private void onFail2(Throwable throwable) {
        LogUtil.d("MineFragment", "throwable fan - " + throwable);
    }

    private void onFail3(Throwable throwable) {
        LogUtil.d("MineFragment", "throwable user - " + throwable);
    }


    @Override
    public void onResume() {
        super.onResume();

        Disposable disposable = mUserInfoDb.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::bindData);

        fetchUpdates();
        mDisposables.add(disposable);
        LogUtil.d("MineFragment", "MineFragment onResume");

        updateFansData();
        updateFollowsData();
    }

    private void setFansData(ArrayList<Relation> relations) {
        mFansAdapter.setRelations(relations);
    }

    private void setFollowsData(ArrayList<Relation> relations) {
        mFollowsAdapter.setRelations(relations);
    }

    private void fetchUpdates() {
        SharedPreferences sp = mContext.getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        String account = sp.getString(DataUtil.USER_ACCOUNT, "null");

        LogUtil.d("MineFragment", account);
        String appKey = MyApplication.getContext().getString(R.string.userInfoDetailsAppKey);
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryUserInfoDetails(appKey, account)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mUserInfoDb::insertUserInfo, this::onFail3);
        mDisposables.add(disposable);
    }

    private void bindData(UserInfo userInfo) {
        nickname.setText(userInfo.getNickname());
        level.setText(String.format(Locale.CHINA, "Lv.%d", userInfo.getLevel()));
        if (userInfo.getVip() == 1) {
            Glide.with(mContext).load(R.drawable.vip1).into(vip);
        } else {
            Glide.with(mContext).load(R.drawable.fvip).into(vip);
        }
        String url = DesertApiClient.URL + "uploads" + userInfo.getPortrait();
        Glide.with(mContext).load(url).into(portrait);

        myFollowCount.setText(String.valueOf(userInfo.getFollow()));
        myFansCount.setText(String.valueOf(userInfo.getFan()));

        SharedPreferences sp = mContext.getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        historyCount.setText(String.valueOf(sp.getInt(DataUtil.TASK_HISTORY_COUNT, 4)));
    }


    @Override
    public void onDestroy() {
        for (Disposable disposable : mDisposables) {
            if (disposable != null) {
                disposable.dispose();
            }
        }
        super.onDestroy();
    }
}
