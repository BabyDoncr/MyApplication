package com.program.cherishtime.data;

import android.content.Context;
import android.database.Cursor;

import com.program.cherishtime.bean.Rank;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ObservableRankDb {
    private PublishSubject<ArrayList<Rank>> mPublishSubject;
    private InfoDbHelper mDBHelper;

    public ObservableRankDb(Context context) {
        mDBHelper = new InfoDbHelper(context);
        mPublishSubject = PublishSubject.create();
    }

    public Observable<ArrayList<Rank>> getObservable() {
        Observable<ArrayList<Rank>> observable = Observable.fromCallable(this::getRankList);
        return observable.concatWith(mPublishSubject);
    }

    private ArrayList<Rank>  getRankList() {
        mDBHelper.open();
        ArrayList<Rank> ranks = new ArrayList<>();
        Cursor c = mDBHelper.getAllRank();
        if (!c.moveToFirst()) {
            return ranks;
        }

        do {
            Rank rank = new Rank();
            rank.setId(c.getInt(InfoDbHelper.RANK_ID_COLUMN_POSITION));
            rank.setImg(c.getString(InfoDbHelper.RANK_IMG_COLUMN_POSITION));
            rank.setName(c.getString(InfoDbHelper.RANK_NAME_COLUMN_POSITION));
            rank.setNum(c.getInt(InfoDbHelper.RANK_NUM_COLUMN_POSITION));
            rank.setPoint(c.getInt(InfoDbHelper.RANK_POINT_COLUMN_POSITION));
            rank.setDays(c.getInt(InfoDbHelper.RANK_DAYS_COLUMN_POSITION));
            ranks.add(rank);
        } while (c.moveToNext());
        c.close();
        mDBHelper.close();
        return ranks;
    }

    public void insertRankList(ArrayList<Rank> ranks) {
        mDBHelper.open();
        mDBHelper.removeAllRank();
        for (Rank rank : ranks) {
            mDBHelper.addRank(
                    rank.getId(),
                    rank.getImg(),
                    rank.getName(),
                    rank.getNum(),
                    rank.getPoint(),
                    rank.getDays()
            );
        }
        mDBHelper.close();
        mPublishSubject.onNext(ranks);
    }
}
