package com.program.cherishtime.data;

import android.content.Context;
import android.database.Cursor;

import com.program.cherishtime.bean.UserInfo;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ObservableUserInfoDb {
    private PublishSubject<UserInfo> mPublishSubject;
    private InfoDbHelper mDBHelper;

    public ObservableUserInfoDb(Context context) {
        mDBHelper = new InfoDbHelper(context);
        mPublishSubject = PublishSubject.create();
    }

    public Observable<UserInfo> getObservable() {
        Observable<UserInfo> observable = Observable.fromCallable(this::getUserInfo);
        return observable.concatWith(mPublishSubject);
    }

    private UserInfo getUserInfo() {
        mDBHelper.open();
        UserInfo userInfo = new UserInfo();
        Cursor c = mDBHelper.getAllUserInfo();
        if (!c.moveToFirst()) {
            return userInfo;
        } else {
            userInfo.setId(c.getInt(InfoDbHelper.USERINFO_ID_COLUMN_POSITION));
            userInfo.setAccount(c.getString(InfoDbHelper.USERINFO_ACCOUNT_COLUMN_POSITION));
            userInfo.setNickname(c.getString(InfoDbHelper.USERINFO_NICKNAME_COLUMN_POSITION));
            userInfo.setEmail(c.getString(InfoDbHelper.USERINFO_EMAIL_COLUMN_POSITION));
            userInfo.setSex(c.getInt(InfoDbHelper.USERINFO_SEX_COLUMN_POSITION));
            userInfo.setBirthday(c.getString(InfoDbHelper.USERINFO_BIRTHDAY_COLUMN_POSITION));
            userInfo.setPortrait(c.getString(InfoDbHelper.USERINFO_PORTRAIT_COLUMN_POSITION));
            userInfo.setPhone(c.getString(InfoDbHelper.USERINFO_PHONE_COLUMN_POSITION));
            userInfo.setFan(c.getInt(InfoDbHelper.USERINFO_FAN_COLUMN_POSITION));
            userInfo.setFollow(c.getInt(InfoDbHelper.USERINFO_FOLLOW_COLUMN_POSITION));
            userInfo.setLikes(c.getInt(InfoDbHelper.USERINFO_LIKES_COLUMN_POSITION));
            userInfo.setVip(c.getInt(InfoDbHelper.USERINFO_VIP_COLUMN_POSITION));
            userInfo.setLevel(c.getInt(InfoDbHelper.USERINFO_LEVEL_COLUMN_POSITION));
            userInfo.setExp(c.getInt(InfoDbHelper.USERINFO_EXP_COLUMN_POSITION));
            userInfo.setPoint(c.getInt(InfoDbHelper.USERINFO_POINT_COLUMN_POSITION));
            userInfo.setDays(c.getInt(InfoDbHelper.USERINFO_DAYS_COLUMN_POSITION));
        }
        c.close();
        mDBHelper.close();
        return userInfo;
    }

    public void insertUserInfo(UserInfo userInfo) {
        mDBHelper.open();
        mDBHelper.removeAllUserInfo();

        mDBHelper.addUserInfo(
                userInfo.getId(),
                userInfo.getAccount(),
                userInfo.getNickname(),
                userInfo.getEmail(),
                userInfo.getSex(),
                userInfo.getBirthday(),
                userInfo.getPortrait(),
                userInfo.getPhone(),
                userInfo.getFan(),
                userInfo.getFollow(),
                userInfo.getLikes(),
                userInfo.getVip(),
                userInfo.getLevel(),
                userInfo.getExp(),
                userInfo.getPoint(),
                userInfo.getDays()
        );
        mDBHelper.close();
        mPublishSubject.onNext(userInfo);
    }

    public void insertUserInfoWithoutPublish(UserInfo userInfo) {
        mDBHelper.open();
        mDBHelper.removeAllUserInfo();

        mDBHelper.addUserInfo(
                userInfo.getId(),
                userInfo.getAccount(),
                userInfo.getNickname(),
                userInfo.getEmail(),
                userInfo.getSex(),
                userInfo.getBirthday(),
                userInfo.getPortrait(),
                userInfo.getPhone(),
                userInfo.getFan(),
                userInfo.getFollow(),
                userInfo.getLikes(),
                userInfo.getVip(),
                userInfo.getLevel(),
                userInfo.getExp(),
                userInfo.getPoint(),
                userInfo.getDays()
        );
        mDBHelper.close();
//        mPublishSubject.onNext(userInfo);
    }
}
