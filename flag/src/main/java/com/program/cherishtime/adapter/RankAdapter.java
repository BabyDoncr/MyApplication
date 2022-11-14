package com.program.cherishtime.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.program.cherishtime.R;
import com.program.cherishtime.activities.UserActivity;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.bean.Rank;
import com.program.cherishtime.network.DesertApiClient;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.LogUtil;

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
public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    private Context mContext;

    private List<Rank> mRankList;

    private int uid;

    public RankAdapter(int uid) {
        mRankList = new ArrayList<>();
        this.uid = uid;
    }

    public void setRanks(List<Rank> ranks) {
        mRankList = ranks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.rank_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rank rank = mRankList.get(position);
        holder.bindTo(mContext, rank, uid);

    }

    @Override
    public int getItemCount() {
        return mRankList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        @BindView(R.id.rank_image)
        CircleImageView rankImage;
        @BindView(R.id.rank_name)
        TextView rankName;
        @BindView(R.id.rank_num)
        TextView rankNum;
        @BindView(R.id.rank_point)
        TextView rankPoint;
        @BindView(R.id.rank_id)
        TextView rankId;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            cardView = (CardView) view;
        }

        @SuppressWarnings("unused")
        void bindTo(Context context, Rank rank, int uid) {
            String url = DesertApiClient.URL + "uploads" + rank.getImg();
            Glide.with(context).load(url).into(rankImage);
            rankName.setText(String.format("昵称：%s", rank.getName()));
            rankNum.setText(String.format(Locale.CHINA, "排名：%d", rank.getNum()));
            rankPoint.setText(String.format(Locale.CHINA, "分数：%.2f", (float)rank.getPoint() / rank.getDays()));
            rankId.setText(String.valueOf(rank.getId()));

            if (uid == rank.getId()) {
                cardView.setOnClickListener(view -> UserActivity.startAction(context, uid, UserActivity.TYPE_EDIT_MY_INFORMATION));
            } else {
                Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                        .queryRelationType(uid, rank.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(msg -> querySuccess(msg, context, rank.getId()), this::onError);
            }
        }

        private void querySuccess(Msg msg, Context context, int id) {
            cardView.setOnClickListener(view -> UserActivity.startAction(context, id, msg.getCode()));
        }

        private void onError(Throwable throwable) {
            LogUtil.d("RankActivity", throwable.getMessage());
        }
    }
}