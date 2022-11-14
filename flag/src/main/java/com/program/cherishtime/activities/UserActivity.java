package com.program.cherishtime.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.program.cherishtime.R;
import com.program.cherishtime.adapter.TabFragmentAdapter;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.bean.User;
import com.program.cherishtime.bean.UserCard;
import com.program.cherishtime.data.ObservableUserCardDb;
import com.program.cherishtime.fragment.RankFragment;
import com.program.cherishtime.fragment.StatisticFragment;
import com.program.cherishtime.fragment.UserTaskFragment;
import com.program.cherishtime.network.DesertApiClient;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.MyApplication;
import com.program.cherishtime.utils.StatusBarUtil;
import com.program.cherishtime.views.CustomHorizontalProgresNoNum;

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
public class UserActivity extends BaseActivity {

    public static final int TYPE_EDIT_MY_INFORMATION = 1;        //编辑资料
    public static final int TYPE_FOCUSED_ON = 2;                //已关注
    public static final int TYPE_FOCUS_ON_EACH_OTHER = 3;       //已互粉
    public static final int TYPE_ADD_FOCUS_1 = 4;               //加关注
    public static final int TYPE_ADD_FOCUS_2 = 5;               //加关注

    @BindView(R.id.user_portrait)
    CircleImageView userPortrait;
    @BindView(R.id.fan_count)
    TextView fanCount;
    @BindView(R.id.fan_layout)
    LinearLayout fanLayout;
    @BindView(R.id.follow_count)
    TextView followCount;
    @BindView(R.id.follow_layout)
    LinearLayout followLayout;
    @BindView(R.id.likes_count)
    TextView likesCount;
    @BindView(R.id.likes_layout)
    LinearLayout likesLayout;

    @BindView(R.id.action_layout_img)
    ImageView actionLayoutImg;
    @BindView(R.id.action_layout_text)
    TextView actionLayoutText;
    @BindView(R.id.action_layout)
    LinearLayout actionLayout;

    @BindView(R.id.user_nickname)
    TextView userNickname;
    @BindView(R.id.user_sex)
    ImageView userSex;
    @BindView(R.id.user_vip)
    ImageView userVip;
    @BindView(R.id.user_level)
    TextView userLevel;
    @BindView(R.id.user_exp)
    TextView userExp;
    @BindView(R.id.user_exp_progress)
    CustomHorizontalProgresNoNum userExpProgress;
    @BindView(R.id.user_info_layout)
    FrameLayout userInfoLayout;

    @BindView(R.id.user_add)
    TextView userAdd;
    @BindView(R.id.user_add_layout)
    RelativeLayout userAddLayout;

    @BindView(R.id.user_toolbar)
    Toolbar userToolbar;
    @BindView(R.id.user_collapsing_toolbar)
    CollapsingToolbarLayout userCollapsingToolbar;
    @BindView(R.id.user_tab_layout)
    TabLayout userTabLayout;
    @BindView(R.id.user_app_bar)
    AppBarLayout userAppBar;
    @BindView(R.id.user_view_pager)
    ViewPager userViewPager;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    private CollapsingToolbarLayoutState state;

    private ObservableUserCardDb mUserCardDb;

    private MenuItem mSearchItem;

    private int uid;

    private int type;

    private List<Disposable> mDisposables = new ArrayList<>();

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    public static void startAction(Context context, int uid, int type) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(DataUtil.USER_ID, uid);
        intent.putExtra(DataUtil.TYPE, type);
        context.startActivity(intent);

        LogUtil.d("UserActivity", "uid = " + uid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        StatusBarUtil.setStatusBarDarkTheme(this, false);

        mUserCardDb = new ObservableUserCardDb(this);

        uid = getIntent().getIntExtra(DataUtil.USER_ID, 0);
        type = getIntent().getIntExtra(DataUtil.TYPE, 0);

        setSupportActionBar(userToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Disposable disposable1 = mUserCardDb.getObservable(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::bindData);

        fetchUpdates();
        mDisposables.add(disposable1);
    }

    private void fetchUpdates() {
        String appKey = MyApplication.getContext().getString(R.string.userCardAppKey);
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryUserCard(appKey, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mUserCardDb::insertUserCardList, this::onFail);
        mDisposables.add(disposable);
    }

    private void bindData(UserCard userCard) {
        String url = DesertApiClient.URL + "uploads" + userCard.getPortrait();
        Glide.with(this).load(url).into(userPortrait);

        userNickname.setText(userCard.getNickname());
        int sex = userCard.getSex();
        if (sex == 1) {
            //male
            Glide.with(this).load(R.drawable.male).into(userSex);
        } else if (sex == 2) {
            //female
            Glide.with(this).load(R.drawable.female).into(userSex);
        } else {
            //unknown
            Glide.with(this).load(R.drawable.ic_sex).into(userSex);
        }
        if (userCard.getVip() == 1) {
            Glide.with(this).load(R.drawable.vip1).into(userVip);
        } else {
            Glide.with(this).load(R.drawable.fvip).into(userVip);
        }
        userLevel.setText(String.format(Locale.CHINA, "Lv%d", userCard.getLevel()));

        int exp = userCard.getExp();
        userExp.setText(String.format(Locale.CHINA, "%d/1000", exp));
        userExpProgress.setMax(1000);
        userExpProgress.setProgress(exp);

        fanCount.setText(String.valueOf(userCard.getFan()));
        followCount.setText(String.valueOf(userCard.getFollow()));
        likesCount.setText(String.valueOf(userCard.getLikes()));

        userAdd.setText(userCard.getNickname());
    }

    private void onFail(Throwable throwable) {
        LogUtil.d("UserActivity", throwable.toString());
    }

    private void init() {
        initAppBar();
        initTabLayout();
        initActionButton();

        actionActionButton();
    }

    private void actionActionButton() {

        SharedPreferences sp = getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int userId = sp.getInt(DataUtil.USER_ID, 0);

        actionLayout.setOnClickListener(view -> {
            if (type == TYPE_FOCUSED_ON) {
                type = TYPE_ADD_FOCUS_2;
                deleteAttention(userId);
            } else if (type == TYPE_FOCUS_ON_EACH_OTHER) {
                type = TYPE_ADD_FOCUS_1;
                deleteAttention(userId);
            } else if (type == TYPE_ADD_FOCUS_1) {
                type = TYPE_FOCUS_ON_EACH_OTHER;
                addAttention(userId);
            } else if (type == TYPE_ADD_FOCUS_2) {
                type = TYPE_FOCUSED_ON;
                addAttention(userId);
            } else if (type == TYPE_EDIT_MY_INFORMATION) {
                initActionButton();
            }
        });
    }

    private void deleteAttention(int userId) {
        Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                .deleteAttention(userId, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(msg -> initActionButton(), this::onFail);
        mDisposables.add(subscribe);
    }

    private void addAttention(int userId) {
        Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                .addAttention(userId, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(msg -> initActionButton(), this::onFail);
        mDisposables.add(subscribe);
    }

    private void initActionButton() {
        switch (type) {
            case TYPE_EDIT_MY_INFORMATION:
                setActionButtonStyle("编辑资料", R.drawable.ic_edit, R.color.grilpink);
                actionLayout.setOnClickListener(view -> EditActivity.startAction(this));
                break;
            case TYPE_FOCUSED_ON:
                setActionButtonStyle("已关注", R.drawable.ic_menu, R.color.grey);
                break;
            case TYPE_FOCUS_ON_EACH_OTHER:
                setActionButtonStyle("已互粉", R.drawable.ic_menu, R.color.grey);
                break;
            case TYPE_ADD_FOCUS_1:
                setActionButtonStyle("关注", R.drawable.ic_done, R.color.grilpink);
                break;
            case TYPE_ADD_FOCUS_2:
                setActionButtonStyle("关注", R.drawable.ic_add_hd, R.color.grilpink);
                break;
            default:
        }
    }

    private void setActionButtonStyle(String text, int resource, int color) {
        actionLayoutText.setText(text);
        Glide.with(this).load(resource).into(actionLayoutImg);
        actionLayoutText.setTextColor(getColor(color));
        actionLayoutImg.setColorFilter(getColor(color));

        if (color == R.color.grilpink) {
            actionLayout.setBackground(getDrawable(R.drawable.round_corner_pink1));
        } else {
            actionLayout.setBackground(getDrawable(R.drawable.round_corner_grey));
        }
    }


    private void initAppBar() {
        state = CollapsingToolbarLayoutState.EXPANDED;
        userInfoLayout.setVisibility(View.VISIBLE);
        userAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0) {
                if (state != CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED;
                    userInfoLayout.setVisibility(View.VISIBLE);
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    state = CollapsingToolbarLayoutState.COLLAPSED;
                    userCollapsingToolbar.setTitle("");
                    userAddLayout.setVisibility(View.VISIBLE);
                    userInfoLayout.setVisibility(View.GONE);
                }
            } else {
                if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                    if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                        userAddLayout.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                        userInfoLayout.setVisibility(View.VISIBLE);
                    }
                    state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                }
            }
            LogUtil.d("UserActivity", "state = " + state);
        });
    }

    private void initTabLayout() {
        List<String> mTitles = new ArrayList<>();
        mTitles.add("任务");
        mTitles.add("排行");
        mTitles.add("统计");
        for (String s : mTitles) {
            userTabLayout.addTab(userTabLayout.newTab().setText(s));
        }
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new UserTaskFragment(uid));
        fragments.add(new RankFragment(uid));
        fragments.add(new StatisticFragment(uid));



        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), mTitles, fragments, UserActivity.this);
        userViewPager.setAdapter(tabFragmentAdapter);
        userTabLayout.setupWithViewPager(userViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_toolbar, menu);
        mSearchItem = menu.findItem(R.id.toolbar_search);
        tintSearchMenuItem();
        initSearchView();
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void tintSearchMenuItem() {
        int color = ContextCompat.getColor(this, android.R.color.white);
        mSearchItem.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    private void initSearchView() {
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String appKey = MyApplication.getContext().getString(R.string.queryUserInfoAppKey);
                MenuItemCompat.collapseActionView(mSearchItem);
                Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                        .queryUserInfoByAccount(appKey, s)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::queryUserSuccess, this::onError);
                mDisposables.add(subscribe);
                return true;
            }

            private void queryUserSuccess(User user) {
                if (user != null) {
                    Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                            .queryRelationType(uid, user.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(msg -> querySuccess(msg, user.getId()), this::onError);
                    mDisposables.add(subscribe);
                } else {
                    Toast.makeText(UserActivity.this, "该账号不存在！", Toast.LENGTH_SHORT).show();
                }
            }

            private void querySuccess(Msg msg, int id) {
                UserActivity.startAction(UserActivity.this, id, msg.getCode());
            }


            private void onError(Throwable throwable) {
                LogUtil.d("UserActivity", throwable.getMessage());
                Toast.makeText(UserActivity.this, "该账号不存在！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Disposable disposable : mDisposables) {
            if (disposable != null)
                disposable.dispose();
        }
    }
}
