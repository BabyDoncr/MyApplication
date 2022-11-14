package com.program.cherishtime.data;

import android.content.Context;
import android.database.Cursor;

import com.program.cherishtime.bean.History;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ObservableHistoryDb {
    private PublishSubject<ArrayList<History>> mPublishSubject;
    private HistoryDbHelper mDBHelper;

    public ObservableHistoryDb(Context context) {
        mDBHelper = new HistoryDbHelper(context);
        mPublishSubject = PublishSubject.create();
    }

    public Observable<ArrayList<History>> getObservable(int uid) {
        Observable<ArrayList<History>> observable = Observable.fromCallable(() -> getHistoryList(uid));
        return observable.concatWith(mPublishSubject);
    }

    private ArrayList<History>  getHistoryList(int uid) {
        mDBHelper.open();
        ArrayList<History> histories = new ArrayList<>();
        Cursor c = mDBHelper.getAllHistory(uid);
        if (!c.moveToFirst()) {
            return histories;
        }
        do {
            History history = new History();
            history.setUid(c.getInt(HistoryDbHelper.HISTORY_UID_COLUMN_POSITION));
            history.setTid(c.getInt(HistoryDbHelper.HISTORY_TID_COLUMN_POSITION));
            history.setDateTime(c.getLong(HistoryDbHelper.HISTORY_DATETIME_COLUMN_POSITION));
            history.setTitle(c.getString(HistoryDbHelper.HISTORY_TITLE_COLUMN_POSITION));
            history.setComplete(c.getInt(HistoryDbHelper.HISTORY_COMPLETE_COLUMN_POSITION));
            history.setDel(c.getInt(HistoryDbHelper.HISTORY_DEL_COLUMN_POSITION));
            histories.add(history);
        } while (c.moveToNext());
        c.close();
        mDBHelper.close();
        return histories;
    }

    public void insertHistory(History history) {
        mDBHelper.open();

        mDBHelper.addHistory(
                history.getUid(),
                history.getTid(),
                history.getDateTime(),
                history.getTitle(),
                history.getComplete(),
                history.getDel()
        );
        mDBHelper.close();
        mPublishSubject.onNext(getHistoryList(history.getUid()));
    }
}
