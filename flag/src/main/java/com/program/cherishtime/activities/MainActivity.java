package com.program.cherishtime.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.program.cherishtime.R;
import com.program.cherishtime.adapter.TabFragmentAdapter;
import com.program.cherishtime.bean.UserInfo;
import com.program.cherishtime.bean.WeatherData;
import com.program.cherishtime.data.ObservableUserInfoDb;
import com.program.cherishtime.fragment.MineFragment;
import com.program.cherishtime.fragment.RankFragment;
import com.program.cherishtime.fragment.TaskFragment;
import com.program.cherishtime.network.DesertApiClient;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.ActivityController;
import com.program.cherishtime.utils.ChineseToPinyinUtil;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RequiresApi(api = Build.VERSION_CODES.M)
@RuntimePermissions
public class MainActivity extends BaseActivity {

    @BindView(R.id.home_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.home_toolBar)
    Toolbar mToolBar;
    @BindView(R.id.home_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.home_nav_view)
    NavigationView mNavView;
    @BindView(R.id.home_drawerLayout)
    DrawerLayout mDrawerLayout;

    private List<String> mTitles = new ArrayList<>();
    private boolean isAllowPermissions = false;

    public LocationClient mLocationClient;

    private List<Disposable> mDisposables = new ArrayList<>();

    private ObservableUserInfoDb mUserInfoDb;

    private int uid;

    private String userAccount;

    public static void actionStart(Context context, String data) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("nickname", data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SharedPreferences sp = getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        uid = sp.getInt(DataUtil.USER_ID, 0);
        String account = sp.getString(DataUtil.USER_ACCOUNT, "");

        LogUtil.d("MainActivity", "uid = " + uid);
        LogUtil.d("MainActivity", "user account = " + account);

        mUserInfoDb = new ObservableUserInfoDb(this);

        setSupportActionBar(mToolBar);
        mNavView.setCheckedItem(R.id.nav_task);
        mNavView.setNavigationItemSelectedListener(new MyNavItemSelectedListener());

        init();

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        if (!isAllowPermissions) {
            MainActivityPermissionsDispatcher.NeedsMethodWithPermissionCheck(this);
        }

        mDrawerLayout.setDrawerListener(new MyDrawerListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNavView();
        LogUtil.d("MainActivity", "MainActivity onResume");
    }

    private void initNavView() {
        Disposable disposable = mUserInfoDb.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::bindData);

        fetchUpdates();
        mDisposables.add(disposable);
    }

    private void init() {
        mTitles.add("我的");
        mTitles.add("任务");
        mTitles.add("排行");

        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new MineFragment());
        fragments.add(new MineFragment(uid));
        fragments.add(new TaskFragment(uid));
        fragments.add(new RankFragment(uid));

        for (String s : mTitles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(s));
        }
        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), mTitles, fragments, MainActivity.this);
        mViewPager.setAdapter(tabFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(1).select();

//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
//                    fragments.get(0).onResume();
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {}
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {}
//        });
    }

    private void fetchUpdates() {
        SharedPreferences sp = getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        String account = sp.getString(DataUtil.USER_ACCOUNT, "null");

        LogUtil.d("MainActivity", account);
        String appKey = MyApplication.getContext().getString(R.string.userInfoDetailsAppKey);
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryUserInfoDetails(appKey, account)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mUserInfoDb::insertUserInfo, this::onFail);
        mDisposables.add(disposable);
    }

    private void bindData(UserInfo userInfo) {
        View headerView = mNavView.getHeaderView(0);
        TextView nicknameTv = (TextView) headerView.findViewById(R.id.user_nickname);
        TextView pointTv = (TextView) headerView.findViewById(R.id.user_points);
        ImageView vipIv = (ImageView) headerView.findViewById(R.id.user_vip);
        ImageView portraitIv = (ImageView) headerView.findViewById(R.id.user_image);

        nicknameTv.setText(userInfo.getNickname());
        pointTv.setText("积分 " + userInfo.getPoint());
        if (userInfo.getVip() == 1) {
            Glide.with(this).load(R.drawable.vip1).into(vipIv);
        } else {
            Glide.with(this).load(R.drawable.fvip).into(vipIv);
        }
        String url = DesertApiClient.URL + "uploads" + userInfo.getPortrait();
        Glide.with(this).load(url).into(portraitIv);

        userAccount = userInfo.getAccount();
        uid = userInfo.getId();
    }

    private void onFail(Throwable throwable) {
        LogUtil.d("MainActivity", throwable.toString());
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.toolbar_today:
                Toast.makeText(MainActivity.this, "today", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }



    //权限获取成功后执行
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void NeedsMethod() {
        isAllowPermissions = true;
        requestLocation();
    }

    //权限获取失败后重新申请权限时执行
    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void RationaleMethod(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("是否授予权限？")
                .setPositiveButton("确定", (dialogInterface, i) -> request.proceed())
                .setNegativeButton("取消", (dialogInterface, i) -> request.cancel())
                .show();
    }

    //权限获取失败后执行
    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void DeniedMethod() {
        Toast.makeText(MainActivity.this, "权限获取失败！", Toast.LENGTH_SHORT).show();
    }

    //勾选不再提醒后执行
    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void AskMethod() {
        new AlertDialog.Builder(this)
                .setTitle("权限设置提醒")
                .setMessage("应用权限被拒绝,为了不影响您的正常使用，请在 设置-权限 中开启对应权限")
                .setPositiveButton("去设置", (dialogInterface, i) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    dialogInterface.dismiss();
                })
                .setNegativeButton("取消", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    Toast.makeText(MainActivity.this, "因权限问题，部分功能受限！", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    public class MyNavItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();
            if (id == R.id.nav_chart) {
                mDrawerLayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_task) {
                mDrawerLayout.closeDrawers();
                mTabLayout.getTabAt(1).select();
            } else if (id == R.id.nav_logout) {
                mDrawerLayout.closeDrawers();
                logout();
            }else if (id == R.id.nav_change_pwd) {
                ForgetActivity.actionStart(MainActivity.this, userAccount);
            } else {
                mDrawerLayout.closeDrawers();
            }
            return true;
        }
    }

    public class MyDrawerListener implements DrawerLayout.DrawerListener {

        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {}

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
            initNavView();
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {}

        @Override
        public void onDrawerStateChanged(int newState) {}
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确认退出？")
                .setPositiveButton("退出", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    SharedPreferences.Editor editor = getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME,
                            MODE_PRIVATE).edit();
                    editor.putString(DataUtil.LOGIN_SIGN, "login-false");
                    editor.putString("nickname", null);
                    editor.apply();
                    ActivityController.finishAll();
                })
                .setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            String city = location.getCity();
            String district = location.getDistrict();

            if (city.contains("市")) {
                city = city.split("市")[0];
            }
            if (district.contains("市")) {
                district = district.split("市")[0];
            }

            getWeather(city, district);
        }
    }

    private void getWeather(String city, String district) {
        Disposable disposable = NetworkWrapper.getInstance().getOpenWeatherApiClient()
                .getWeatherForCity(ChineseToPinyinUtil.getFullSpell(district),
                        MyApplication.getContext().getString(R.string.openWeatherApiKey))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherData -> getWeatherSuccess(weatherData, district),
                        throwable -> getWeatherError(throwable, city));
        mDisposables.add(disposable);
    }

    private void getWeatherSuccess(WeatherData weatherData, String district) {
        loadWeatherData(district, weatherData.getWeatherDescription(), weatherData.getTemperatureCelsius());
    }

    private void getWeatherError(Throwable throwable, String city) {
        LogUtil.d("MainActivity", throwable.toString());
        String name = ChineseToPinyinUtil.getFullSpell(city);
        Disposable disposable1 = NetworkWrapper.getInstance().getOpenWeatherApiClient()
                .getWeatherForCity(name, MyApplication.getContext().getString(R.string.openWeatherApiKey))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherData -> getWeatherSuccess(weatherData, city), throwable1 -> {
                    LinearLayout ll = (LinearLayout) mNavView.getHeaderView(0).findViewById(R.id.ll_weather);
                    ll.setVisibility(View.INVISIBLE);
                    LogUtil.d("MainActivity", throwable1.toString());
                });
        mDisposables.add(disposable1);
    }

    private void loadWeatherData(String name, String weather, String temp) {
        LinearLayout ll = (LinearLayout) mNavView.getHeaderView(0).findViewById(R.id.ll_weather);
        ll.setVisibility(View.VISIBLE);
        TextView cityTv = (TextView) mNavView.getHeaderView(0).findViewById(R.id.city);
        TextView weatherTv = (TextView) mNavView.getHeaderView(0).findViewById(R.id.weather);
        TextView tempTv = (TextView) mNavView.getHeaderView(0).findViewById(R.id.temp);

        cityTv.setText(name);
        weatherTv.setText(weather);
        tempTv.setText(temp);
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        for (Disposable disposable : mDisposables) {
            if (disposable != null)
                disposable.dispose();
        }
        super.onDestroy();
    }
}
