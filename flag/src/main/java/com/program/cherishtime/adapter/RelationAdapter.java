package com.program.cherishtime.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.program.cherishtime.R;
import com.program.cherishtime.activities.UserActivity;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.bean.Relation;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class RelationAdapter extends RecyclerView.Adapter<RelationAdapter.ViewHolder> {

    public static final int TYPE_FOLLOWS = 0;
    public static final int TYPE_FANS = 1;

    private Context mContext;

    private List<Relation> mRelations;
    private int mType;

    private int userId;

    public RelationAdapter(List<Relation> relations, int type) {
        mRelations = relations;
        mType = type;
    }

    public RelationAdapter(int type) {
        mRelations = new ArrayList<>();
        mType = type;
    }

    public void setRelations(List<Relation> relations) {
        mRelations = relations;
        notifyDataSetChanged();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.relation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Relation relation = mRelations.get(position);
        holder.bindTo(mContext, relation, mType, userId);
    }

    @Override
    public int getItemCount() {
        return mRelations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        @BindView(R.id.relation_image)
        CircleImageView relationImage;
        @BindView(R.id.relation_name)
        TextView relationName;
        @BindView(R.id.relation_some_info)
        TextView relationSomeInfo;
        @BindView(R.id.relation_action_img)
        ImageView relationActionImg;
        @BindView(R.id.relation_action_text)
        TextView relationActionText;
        @BindView(R.id.relation_action)
        LinearLayout relationAction;
        @BindView(R.id.relation_id)
        TextView relationId;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            cardView = (CardView) view;
        }

        void bindTo(Context context, Relation relation, int type, int uid) {
            String baseUrl = "http://www.unnic.top:8080/desert/uploads";

            relationSomeInfo.setVisibility(View.GONE);
            String url = baseUrl + relation.getImg();
            Glide.with(context).load(url).into(relationImage);
            relationName.setText(relation.getName());
            relationId.setText(String.valueOf(relation.getId()));

            if (relation.getVip() == 1) {
                relationName.setTextColor(context.getColor(R.color.grilpink));
            } else {
                relationName.setTextColor(Color.BLACK);
            }

            setRelationActionStyle(context, relation, type);
            cardView.setOnClickListener(view -> turnToUserPage(context, relation, type));

            action(context, relation, type, uid);
        }

        private void turnToUserPage(Context context, Relation relation, int type) {
            int buttonType;
            if (relation.getType() == 1)
                buttonType = UserActivity.TYPE_FOCUS_ON_EACH_OTHER;
            else {
                if (type == TYPE_FOLLOWS)
                    buttonType = UserActivity.TYPE_FOCUSED_ON;
                else
                    buttonType = UserActivity.TYPE_ADD_FOCUS_1;
            }
            UserActivity.startAction(context, relation.getId(), buttonType);
        }

        private void setRelationActionStyle(Context context, Relation relation, int type) {
            if (type == TYPE_FOLLOWS) {
                if (relation.getType() == 1) {
                    actionBarStyle(context, "已互粉", R.drawable.ic_menu, R.color.grey);
                } else {
                    actionBarStyle(context, "已关注", R.drawable.ic_menu, R.color.grey);
                }
            } else {
                if (relation.getType() == 1) {
                    actionBarStyle(context, "已互粉", R.drawable.ic_menu, R.color.grey);
                } else {
                    actionBarStyle(context, "关注", R.drawable.ic_done, R.color.grilpink);
                }
            }
        }

        private void action(Context context, Relation relation, int type, int uid) {
            if (type == TYPE_FOLLOWS) {
                relationAction.setOnClickListener(view -> {
                    if (relationActionText.getText().toString().equals("已互粉")) {
                        actionBarStyle(context, "关注", R.drawable.ic_done, R.color.grilpink);
                        deleteAttention(uid, relation);
                    } else if (relationActionText.getText().toString().equals("已关注")) {
                        actionBarStyle(context, "关注", R.drawable.ic_add_hd, R.color.grilpink);
                        deleteAttention(uid, relation);
                    } else {
                        if (relation.getType() == 1) {
                            actionBarStyle(context, "已互粉", R.drawable.ic_menu, R.color.grey);
                            addAttention(uid, relation);
                        } else {
                            actionBarStyle(context, "已关注", R.drawable.ic_menu, R.color.grey);
                            addAttention(uid, relation);
                        }
                    }
                });
            } else {
                relationAction.setOnClickListener(view -> {
                    if (relationActionText.getText().toString().equals("已互粉")) {
                        actionBarStyle(context, "关注", R.drawable.ic_done, R.color.grilpink);
                        deleteAttention(uid, relation);
                    } else {
                        actionBarStyle(context, "已互粉", R.drawable.ic_menu, R.color.grey);
                        addAttention(uid, relation);
                    }
                });
            }
        }

        private void actionBarStyle(Context context, String text, int resource, int color) {
            relationActionText.setText(text);
            Glide.with(context).load(resource).into(relationActionImg);
            relationActionImg.setColorFilter(context.getColor(color));
            relationActionText.setTextColor(context.getColor(color));
            if (color == R.color.grey) {
                relationAction.setBackground(context.getDrawable(R.drawable.round_corner_grey));
            } else {
                relationAction.setBackground(context.getDrawable(R.drawable.round_corner_pink1));
            }
        }

        @SuppressWarnings("unused")
        private void deleteAttention(int userId, Relation relation) {
            Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                    .deleteAttention(userId, relation.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onFail);
        }

        @SuppressWarnings("unused")
        private void addAttention(int userId, Relation relation) {
            Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                    .addAttention(userId, relation.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onFail);
        }

        private void onSuccess(Msg msg) {
            LogUtil.d("MineActivity", msg.toString());
        }

        private void onFail(Throwable throwable) {
            LogUtil.d("MineActivity", throwable.getMessage());
        }
    }

}