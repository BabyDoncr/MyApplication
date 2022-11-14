package com.program.cherishtime.data;

import android.content.Context;
import android.database.Cursor;

import com.program.cherishtime.bean.Relation;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ObservableFollowsDb {
    private PublishSubject<ArrayList<Relation>> mPublishSubject;
    private InfoDbHelper mDBHelper;

    public ObservableFollowsDb(Context context) {
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
        Cursor c = mDBHelper.getAllFollows();
        if (!c.moveToFirst()) {
            return relations;
        }

        do {
            Relation relation = new Relation();
            relation.setId(c.getInt(InfoDbHelper.FOLLOWS_ID_COLUMN_POSITION));
            relation.setName(c.getString(InfoDbHelper.FOLLOWS_NAME_COLUMN_POSITION));
            relation.setImg(c.getString(InfoDbHelper.FOLLOWS_IMG_COLUMN_POSITION));
            relation.setVip(c.getInt(InfoDbHelper.FOLLOWS_VIP_COLUMN_POSITION));
            relation.setType(c.getInt(InfoDbHelper.FOLLOWS_TYPE_COLUMN_POSITION));
            relation.setState(c.getInt(InfoDbHelper.FOLLOWS_STATE_COLUMN_POSITION));
            relations.add(relation);
        } while (c.moveToNext());
        c.close();
        mDBHelper.close();
        return relations;
    }

    public void insertRelationList(ArrayList<Relation> relations) {
        mDBHelper.open();
        mDBHelper.removeAllFollows();
        for (Relation relation : relations) {
            mDBHelper.addFollows(
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
