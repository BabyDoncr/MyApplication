package com.program.cherishtime.data;

import android.content.Context;
import android.database.Cursor;

import com.program.cherishtime.bean.UserCard;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ObservableUserCardDb {
    private PublishSubject<UserCard> mPublishSubject;
    private InfoDbHelper mDBHelper;

    public ObservableUserCardDb(Context context) {
        mDBHelper = new InfoDbHelper(context);
        mPublishSubject = PublishSubject.create();
    }

    public Observable<UserCard> getObservable(int uid) {
        Observable<UserCard> observable = Observable.fromCallable(() -> getUserCardByUid(uid));
        return observable.concatWith(mPublishSubject);
    }

    private UserCard getUserCardByUid(int uid) {
        mDBHelper.open();
        UserCard userCard = new UserCard();
        Cursor c = mDBHelper.getUserCardByUserId(uid);
        if (!c.moveToFirst()) {
            return userCard;
        } else {
            userCard.setId(c.getInt(InfoDbHelper.USERCARD_ID_COLUMN_POSITION));
            userCard.setNickname(c.getString(InfoDbHelper.USERCARD_NICKNAME_COLUMN_POSITION));
            userCard.setSex(c.getInt(InfoDbHelper.USERCARD_SEX_COLUMN_POSITION));
            userCard.setPortrait(c.getString(InfoDbHelper.USERCARD_PORTRAIT_COLUMN_POSITION));
            userCard.setFan(c.getInt(InfoDbHelper.USERCARD_FAN_COLUMN_POSITION));
            userCard.setFollow(c.getInt(InfoDbHelper.USERCARD_FOLLOW_COLUMN_POSITION));
            userCard.setLikes(c.getInt(InfoDbHelper.USERCARD_LIKES_COLUMN_POSITION));
            userCard.setVip(c.getInt(InfoDbHelper.USERCARD_VIP_COLUMN_POSITION));
            userCard.setLevel(c.getInt(InfoDbHelper.USERCARD_LEVEL_COLUMN_POSITION));
            userCard.setExp(c.getInt(InfoDbHelper.USERCARD_EXP_COLUMN_POSITION));
            userCard.setType(c.getInt(InfoDbHelper.USERCARD_TYPE_COLUMN_POSITION));
        }
        c.close();
        mDBHelper.close();
        return userCard;
    }

    public void insertUserCardList(UserCard userCard) {
        mDBHelper.open();
        mDBHelper.removeUserCardByUserId(userCard.getId());
        mDBHelper.addUserCard(
                userCard.getId(),
                userCard.getNickname(),
                userCard.getSex(),
                userCard.getPortrait(),
                userCard.getFan(),
                userCard.getFollow(),
                userCard.getLikes(),
                userCard.getVip(),
                userCard.getLevel(),
                userCard.getExp(),
                userCard.getType()
        );
        mDBHelper.close();
        mPublishSubject.onNext(userCard);
    }
}
