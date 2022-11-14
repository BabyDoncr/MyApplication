package com.program.cherishtime.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.program.cherishtime.R;
import com.program.cherishtime.activities.TaskActivity;
import com.program.cherishtime.bean.History;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mContext;

    private ArrayList<History> mHistoryList;

    public HistoryAdapter(ArrayList<History> historyList) {
        mHistoryList = historyList;
    }

    public void setHistories(ArrayList<History> histories) {
        mHistoryList = histories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = mHistoryList.get(position);
        holder.bindTo(mContext, history);

    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        @BindView(R.id.history_image)
        CircleImageView historyImage;
        @BindView(R.id.history_title)
        TextView historyTitle;
        @BindView(R.id.history_date)
        TextView historyDate;
        @BindView(R.id.history_complete_img)
        ImageView historyCompleteImg;
        @BindView(R.id.history_task_id)
        TextView historyTaskId;

        private int[] images = {
                R.drawable.spongebob,
                R.drawable.img1, R.drawable.img2, R.drawable.img3,
                R.drawable.img4, R.drawable.img5, R.drawable.img6,
                R.drawable.img7, R.drawable.img8, R.drawable.img9
        };

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            cardView = (CardView) view;
        }

        void bindTo(Context context, History history) {
            Glide.with(context).load(images[history.getTid() % 10]).into(historyImage);
            historyTitle.setText(history.getTitle());

            historyTaskId.setText(String.valueOf(history.getTid()));

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            historyDate.setText(format.format(history.getDateTime()));

            if (history.getComplete() == 2) {
                Glide.with(context).load(R.drawable.success).into(historyCompleteImg);
            } else {
                Glide.with(context).load(R.drawable.fail).into(historyCompleteImg);
            }

            cardView.setOnClickListener(view -> TaskActivity.startAction(context, TaskActivity.TYPE_TASK_CARD_HISTORY, history.getTid(), history.getDateTime()));
        }
    }
}