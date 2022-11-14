package com.example.jxwoer.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jxwoer.myapplication.adapter.GoodAdapter;
import com.example.jxwoer.myapplication.adapter.IcreamAdapter;
import com.example.jxwoer.myapplication.pojo.Good;
import com.example.jxwoer.myapplication.pojo.Icream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;

import static android.R.id.list;


/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class HomeFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater) {
        TextView textView = new TextView(mContext);
//        textView.setText("首页");
//        textView.setTextSize(30);
//        textView.setTextColor(Color.parseColor("#ff0000"));
//        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private View mView;
    private ViewPager mViewPaper;
    private ArrayList<ImageView> images;
    private ArrayList<View> dots;
    private int currentItem;
    private List<Good> list = new ArrayList<Good>();
    private List<Icream> icreamList = new ArrayList<Icream>();
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.timg,
            R.drawable.timg1,
            R.drawable.timg2,
            R.drawable.timg,
            R.drawable.timg1
    };
    //存放图片的标题
    private String[] titles = new String[]{
            "轮播1",
            "轮播2",
            "轮播3",
            "轮播4",
            "轮播5"
    };
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
    private String[] data = {"Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.home_list, null);
        setView();
//        initgood();
        return mView;

    }

    private void setView() {
        mViewPaper = (ViewPager) mView.findViewById(R.id.vp);

        //显示的图片
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(mView.findViewById(R.id.dot_0));
        dots.add(mView.findViewById(R.id.dot_1));
        dots.add(mView.findViewById(R.id.dot_2));
        dots.add(mView.findViewById(R.id.dot_3));
        dots.add(mView.findViewById(R.id.dot_4));

        title = (TextView) mView.findViewById(R.id.title);
        title.setText(titles[0]);

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.shape_yes);
                dots.get(oldPosition).setBackgroundResource(R.drawable.shape_no);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /*定义的适配器*/
    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//          super.destroyItem(container, position, object);
//          view.removeView(view.getChildAt(position));
//          view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }

    /**
     * 图片轮播任务
     *
     * @author liuyazhuang
     */
    private class ViewPageTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        }

        ;
    };

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

public  void onResume() {

    super.onResume();


        String url = "http://192.168.120.180:8080/spring4/GoodService/queryGood.action";
        OkHttpUtils
                .post()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "加载错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        list = gson.fromJson(response, new TypeToken<List<Good>>() {
                        }.getType());
                        ListView mListView = (ListView) mView.findViewById(R.id.list_view);
                        GoodAdapter adaper = new GoodAdapter(list, mListView.getContext());
                        mListView.setAdapter(adaper);
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                int tvid = list.get(position).getPid();
//                                int tstore = list.get(position).getInventory();
                                TextView tvname = (TextView) view.findViewById(R.id.icream_name);
                                String tname = tvname.getText().toString();
                                TextView tvprice = (TextView) view.findViewById(R.id.icream_price);
                                String tprice = tvprice.getText().toString();
                                TextView tvstore = (TextView) view.findViewById(R.id.icream_store);
                                String tstore = tvstore.getText().toString();
                                ImageView iv = (ImageView) view.findViewById(R.id.icream_image);
                                int imgid = (int) iv.getTag();
                                TextView tvnumber = (TextView) view.findViewById(R.id.icream_number);
                                String tnumber = tvnumber.getText().toString();
                                Intent intent = new Intent(getActivity(), Icream_detail.class);
//                                Log.e("goodid", "goodid" + tvid);
//                                intent.putExtra("goodid", tvid);
                                intent.putExtra("icream_image", imgid);
                                intent.putExtra("icream_name", tname);
                                intent.putExtra("icream_store", tstore);
                                intent.putExtra("icream_price", tprice);
                                intent.putExtra("icream_number", Integer.valueOf(tnumber));
                                startActivity(intent);
                            }
                        });
                    }
                });
    }

}

