package com.lkl.liukailong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lkl.liukailong.R;
import com.lkl.liukailong.holdere.UserHolder;
import com.lkl.liukailong.model.User;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private final LayoutInflater minflater;
    //定义变量
    ListView listView;
    Context context;
    public List<User> list;

    //构造方法
    public UserAdapter(Context context, List<User> list, ListView listView){
        this.minflater=LayoutInflater.from(context);
        this.list=list;
        this.listView=listView;
        this.context=context;
    }

    public int getCount(){
        return list.size();
    }
    public Object getItem(int i){
        return null;
    }
    public long getItemId(int i){
        return 0;
    }
    public View getView(int i, View view, ViewGroup viewGroup){

        UserHolder userHolder;
        if(view==null){
            userHolder=new UserHolder();
            //加载新布局
            view=minflater.inflate(R.layout.user_list_item,null);
            userHolder.tv_id=view.findViewById(R.id.tv_id);
            userHolder.tv_theme=view.findViewById(R.id.tv_theme);
            userHolder.tv_article=view.findViewById(R.id.tv_article);
            userHolder.tv_writer=view.findViewById(R.id.tv_writer);
            userHolder.tv_time=view.findViewById(R.id.tv_time);
            userHolder.user_del=view.findViewById(R.id.user_del);
            userHolder.user_update=view.findViewById(R.id.user_update);
            view.setTag(userHolder);

        }else {
            userHolder=(UserHolder) view.getTag();
        }

        userHolder.tv_id.setText((String.valueOf(list.get(i).getId())));
        userHolder.tv_theme.setText(list.get(i).getTheme());
        userHolder.tv_article.setText(list.get(i).getArticle());
        userHolder.tv_writer.setText(list.get(i).getWriter());
        userHolder.tv_time.setText(list.get(i).getTime());
        userHolder.user_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userHolder.update(i,context,list,listView);
            }
        });
        userHolder.user_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userHolder.delete(i,context,list,listView);
            }
        });
        return view;
    }


}
