package com.program.cherishtime.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import com.program.cherishtime.R;
import com.program.cherishtime.activities.MainActivity;
import com.program.cherishtime.bean.Task;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AlarmService extends Service {

    private Context mContext;
    private AlarmBinder alarmBinder;

    private Map<String, PendingIntent> mMap;

    private int hour;
    private int minute;
    private int tid;

    public AlarmService() {
    }

    @Override
    public void onCreate() {

        LogUtil.d("AlarmService", "onCreate");

        super.onCreate();
        mContext = this;
        mMap = new HashMap<>();
        setForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LogUtil.d("AlarmService", "onStartCommand");

        Task task = (Task) intent.getParcelableExtra(DataUtil.TASK_DATA);
        boolean isSetAlarm = intent.getBooleanExtra(DataUtil.SET_ALARM, true);
        if (task != null) {
            hour = Integer.valueOf(task.getStartTime().split(":")[0]);
            minute = Integer.valueOf(task.getStartTime().split(":")[1]);
            tid = task.getId();
        }
        LogUtil.d("AlarmService", "hour = " + hour + " minute = " + minute);

        alarmBinder = new AlarmBinder();
        if (isSetAlarm) {
            alarmBinder.setAlarm(task, "key" + tid, tid);
        } else {
            alarmBinder.cancelAlarm("key" + tid);
        }


        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        int five = 5 * 60 * 1000;
        long triggerAtTime = System.currentTimeMillis() + five;
        Intent i = new Intent(this, AlarmService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, START_REDELIVER_INTENT);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return alarmBinder;
    }

    class AlarmBinder extends Binder {
        private AlarmManager alarmManager;
        private Intent intent;
        private PendingIntent pendingIntent;

        public void setAlarm(Task task, String key, int tid) {

            LogUtil.d("AlarmService", "setAlarm");

            alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);

            long targetTime;
            if (TimeUtil.compareTime(hour, minute))
                targetTime = TimeUtil.getMilliseconds(hour, minute) - 3 * 60 * 1000;
            else
                targetTime = TimeUtil.getTomorrow(hour, minute) - 3 * 60 * 1000;

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH:mm", Locale.getDefault());

            LogUtil.d("AlarmService", "targetTime = " + format.format(targetTime));

            intent = new Intent("android.intent.action.alarm");

            intent.putExtra("title", task.getTitle());
            intent.putExtra("content", task.getContent());
            intent.putExtra("start", task.getStartTime());
            intent.putExtra("end", task.getEndTime());

            intent.setComponent(new ComponentName("com.program.cherishtime",
                    "com.program.cherishtime.broadcast.AlarmReceiver"));
            pendingIntent = PendingIntent.getBroadcast(mContext, tid, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mMap.put(key, pendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, targetTime, pendingIntent);
            intent = null;
            pendingIntent = null;
        }

        public void cancelAlarm(String key) {
            if (!mMap.isEmpty()) {

                LogUtil.d("AlarmService", "cancelAlarm");

                alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
                PendingIntent pendingIntent = mMap.get(key);
                mMap.remove(key);
                alarmManager.cancel(pendingIntent);
            }
        }
    }

    private void setForeground() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
        Intent i = new Intent(this, MainActivity.class);

        builder.setContentIntent(PendingIntent.getActivity(this, 0, i, 0))
                .setSmallIcon(R.mipmap.ic_logo_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_logo))
                .setContentTitle("提示")
                .setContentText("当前有任务正在执行,请不要关闭软件")
                .setWhen(System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "mine", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
            builder.setChannelId("1");
        }
        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;

        startForeground(110, notification);
    }
}
