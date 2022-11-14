package com.example.jxwoer.myapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.CheckBox;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jxwoer.myapplication.adapter.MyBaseExpandableListAdapter;
import com.example.jxwoer.myapplication.pojo.Buy;
import com.example.jxwoer.myapplication.pojo.Client;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/6/14 0014.
 */



public class CartFragment extends BaseFragment {
    @Override


    protected View initView(LayoutInflater inflater) {
        TextView textView = new TextView(mContext);
//        textView.setText("购物车");
//        textView.setTextSize(30);
//        textView.setTextColor(Color.parseColor("#ff0000"));
//        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    //建议把它写一个公共的变量，这里方便阅读就不写了。
    private View mView;
    //   List<Map<String, Object>> parentMapList = new ArrayList<Map<String, Object>>();
    //定义子列表项List数据集合
    final Buy buy = new Buy();
    private List<Buy> childMapList_list = new ArrayList<Buy>();
    MyBaseExpandableListAdapter myBaseExpandableListAdapter;
    CheckBox id_cb_select_all;
    LinearLayout id_ll_normal_all_state;
    LinearLayout id_ll_editing_all_state;
    ListView expandableListView;
    RelativeLayout id_rl_cart_is_empty;
    RelativeLayout id_rl_foot;
    TextView id_tv_edit_all;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.cart, null);
//        init();
        return mView;
    }
    //定义父列表项List数据集合


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        setContentView(R.layout.activity_main);
//        public void init () {
        //   initData();
        SharedPreferences sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        int cid = sp.getInt("userid", 0);
        String url = "http://192.168.120.180:8080/spring4/BuyService/queryBuy.action";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("cid",String.valueOf(cid))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "加载错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        childMapList_list = gson.fromJson(response, new TypeToken<List<Buy>>() {
                        }.getType());
                        Toast.makeText(getActivity(), "加载成功" + childMapList_list.size(), Toast.LENGTH_LONG).show();
                        expandableListView = (ListView) mView.findViewById(R.id.id_elv_listview);
                        myBaseExpandableListAdapter = new MyBaseExpandableListAdapter(expandableListView.getContext(), childMapList_list);
                        expandableListView.setAdapter(myBaseExpandableListAdapter);
                        expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getActivity(), "click：" + position, Toast.LENGTH_SHORT).show();
                            }
                        });

                        ImageView id_iv_back = (ImageView) mView.findViewById(R.id.id_iv_back);
                        id_iv_back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity(), "click :back", Toast.LENGTH_SHORT).show();
                            }
                        });
                        id_ll_normal_all_state = (LinearLayout) mView.findViewById(R.id.id_ll_normal_all_state);
                        id_ll_editing_all_state = (LinearLayout) mView.findViewById(R.id.id_ll_editing_all_state);
                        id_rl_cart_is_empty = (RelativeLayout) mView.findViewById(R.id.id_rl_cart_is_empty);
                        TextView id_tv_save_star_all = (TextView) mView.findViewById(R.id.id_tv_save_star_all);
                        id_tv_save_star_all.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity(), "收藏多选商品", Toast.LENGTH_SHORT).show();
                            }
                        });
                        TextView id_tv_delete_all = (TextView) mView.findViewById(R.id.id_tv_delete_all);
                        id_tv_delete_all.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myBaseExpandableListAdapter.removeGoods();
                                // Toast.makeText(MainActivity.this, "删除多选商品", Toast.LENGTH_SHORT).show();
                            }
                        });

                        id_tv_edit_all = (TextView) mView.findViewById(R.id.id_tv_edit_all);

                        id_tv_edit_all.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (v instanceof TextView) {
                                    TextView tv = (TextView) v;
                                    if (MyBaseExpandableListAdapter.EDITING.equals(tv.getText())) {
                                        myBaseExpandableListAdapter.setupEditingAll(true);
                                        tv.setText(MyBaseExpandableListAdapter.FINISH_EDITING);
                                        changeFootShowDeleteView(true);//这边类似的功能 后期待使用观察者模式
                                    } else {
                                        myBaseExpandableListAdapter.setupEditingAll(false);
                                        tv.setText(MyBaseExpandableListAdapter.EDITING);
                                        changeFootShowDeleteView(false);//这边类似的功能 后期待使用观察者模式
                                    }

                                }
                            }
                        });

                        id_cb_select_all = (CheckBox) mView.findViewById(R.id.id_cb_select_all);
                        id_cb_select_all.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (v instanceof CheckBox) {
                                    CheckBox checkBox = (CheckBox) v;
                                    myBaseExpandableListAdapter.setupAllChecked(checkBox.isChecked());
                                }
                            }
                        });

                        final TextView id_tv_totalPrice = (TextView) mView.findViewById(R.id.id_tv_totalPrice);

                        final TextView id_tv_totalCount_jiesuan = (TextView) mView.findViewById(R.id.id_tv_totalCount_jiesuan);
                        id_tv_totalCount_jiesuan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getActivity(), "click：结算", Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
                                int cid = sp.getInt("userid", 0);
//                BroadCastManager.getInstance().sendBroadCast(Icream_detail.this, intent_cart);
                                String url = "http://192.168.120.180:8080/spring4/List/saveList.action";
                                Log.e("abbsbs",String.valueOf(cid));
                                //OkHttpClient client =new OkHttpClient();
                                OkHttpUtils
                                        .post()
                                        .url(url)
                                        .addParams("cid", String.valueOf(cid))
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_LONG).show();
                                            }
                                            @Override
                                            public void onResponse(String response, int id) {
                                                Gson gson = new Gson();
                                                Client c = gson.fromJson(response, Client.class);
                                                Toast.makeText(getActivity(), "结算成功", Toast.LENGTH_LONG).show();
                                            }
                                        });

                            }
                        });
                        myBaseExpandableListAdapter.setOnGoodsCheckedChangeListener(new MyBaseExpandableListAdapter.OnGoodsCheckedChangeListener() {
                            @Override
                            public void onGoodsCheckedChange(int totalCount, float totalPrice) {
                                id_tv_totalPrice.setText(String.format(getString(R.string.total), totalPrice));
                                id_tv_totalCount_jiesuan.setText(String.format(getString(R.string.jiesuan), totalCount));
                            }
                        });

                        myBaseExpandableListAdapter.setOnAllCheckedBoxNeedChangeListener(new MyBaseExpandableListAdapter.OnAllCheckedBoxNeedChangeListener() {
                            @Override
                            public void onCheckedBoxNeedChange(boolean allParentIsChecked) {
                                id_cb_select_all.setChecked(allParentIsChecked);
                            }
                        });

                        myBaseExpandableListAdapter.setOnEditingTvChangeListener(new MyBaseExpandableListAdapter.OnEditingTvChangeListener() {
                            @Override
                            public void onEditingTvChange(boolean allIsEditing) {

                                changeFootShowDeleteView(allIsEditing);//这边类似的功能 后期待使用观察者模式

                            }
                        });

                        myBaseExpandableListAdapter.setOnCheckHasGoodsListener(new MyBaseExpandableListAdapter.OnCheckHasGoodsListener() {
                            @Override
                            public void onCheckHasGoods(boolean isHasGoods) {
                                setupViewsShow(isHasGoods);
                            }
                        });

                        /**====include进来方式可能会导致view覆盖listview的最后一个item 解决*/
                        //在onCreate方法中一般没办法直接调用view.getHeight方法来获取到控件的高度
                        id_rl_foot = (RelativeLayout) mView.findViewById(R.id.id_rl_foot);
                        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        id_rl_foot.measure(w, h);
                        int r_width = id_rl_foot.getMeasuredWidth();
                        int r_height = id_rl_foot.getMeasuredHeight();
                        Log.i("MeasureSpec", "MeasureSpec r_width = " + r_width);
                        Log.i("MeasureSpec", "MeasureSpec r_height = " + r_height);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        int top = dp2px(getActivity(), 48);
                        lp.setMargins(0, top, 0, r_height);//48
                        expandableListView.setLayoutParams(lp);
                        /**==========*/

                        if (childMapList_list != null && childMapList_list.size() > 0) {
                            setupViewsShow(true);
                        } else {
                            setupViewsShow(false);
                        }
                    }
                });
    }
        private void setupViewsShow(boolean isHasGoods) {
            if (isHasGoods) {
                expandableListView.setVisibility(View.VISIBLE);
                id_rl_cart_is_empty.setVisibility(View.GONE);
                id_rl_foot.setVisibility(View.VISIBLE);
                id_tv_edit_all.setVisibility(View.VISIBLE);
            } else {
                expandableListView.setVisibility(View.GONE);
                id_rl_cart_is_empty.setVisibility(View.VISIBLE);
                id_rl_foot.setVisibility(View.GONE);
                id_tv_edit_all.setVisibility(View.GONE);
            }
        }

    public void changeFootShowDeleteView(boolean showDeleteView) {

        if (showDeleteView) {
            id_tv_edit_all.setText(MyBaseExpandableListAdapter.FINISH_EDITING);
            id_ll_normal_all_state.setVisibility(View.INVISIBLE);
            id_ll_editing_all_state.setVisibility(View.VISIBLE);
        } else {
            id_tv_edit_all.setText(MyBaseExpandableListAdapter.EDITING);
            id_ll_normal_all_state.setVisibility(View.VISIBLE);
            id_ll_editing_all_state.setVisibility(View.INVISIBLE);
        }
    }

    public int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


        }



