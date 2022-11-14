package com.program.cherishtime.data;

import android.content.Context;
import android.database.Cursor;

import com.program.cherishtime.bean.Relation;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ObservableFansDb {
    private PublishSubject<ArrayList<Relation>> mPublishSubject;
    private InfoDbHelper mDBHelper;

    public ObservableFansDb(Context context) {
        mDBHelper = new InfoDbHelper(context);
        mPublishSubject = PublishSubject.create();
    }

    public Observable<ArrayList<Relation>> getObservable() {
        Observable<ArrayList<Relation>> observable = Observable.fromCallable(this::getRelationList);
        return observable.concatWith(mPublishSubject);
    }

    private ArrayList<Relation>  getRelationList() {
        mDBHelper.open();
        ArrayList<Relation> relations = new ArrayList<>();
        Cursor c = mDBHelper.getAllFans();
        if (!c.moveToFirst()) {
            return relations;
        }

        do {
            Relation relation = new Relation();
            relation.setId(c.getInt(InfoDbHelper.FANS_ID_COLUMN_POSITION));
            relation.setName(c.getString(InfoDbHelper.FANS_NAME_COLUMN_POSITION));
            relation.setImg(c.getString(InfoDbHelper.FANS_IMG_COLUMN_POSITION));
            relation.setVip(c.getInt(InfoDbHelper.FANS_VIP_COLUMN_POSITION));
            relation.setType(c.getInt(InfoDbHelper.FANS_TYPE_COLUMN_POSITION));
            relation.setState(c.getInt(InfoDbHelper.FANS_STATE_COLUMN_POSITION));
            relations.add(relation);
        } while (c.moveToNext());
        c.close();
        mDBHelper.close();
        return relations;
    }

    public void insertRelationList(ArrayList<Relation> relations) {
        mDBHelper.open();
        mDBHelper.removeAllFans();
        for (Relation relation : relations) {
            mDBHelper.addFans(
                    relation.getId(),
                    relation.getName(),
                    relation.getImg(),
                    relation.getVip(),
                    relation.getType(),
                    relation.getState()
            );
        }
        mDBHelper.close();
        mPublishSubject.onNext(relations);
    }
}
