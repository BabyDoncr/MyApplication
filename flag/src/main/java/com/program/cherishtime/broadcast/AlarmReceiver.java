package com.program.cherishtime.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.program.cherishtime.activities.AlertActivity;
import com.program.cherishtime.bean.Task;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Task task = new Task();
        task.setTitle(intent.getStringExtra("title"));
        task.setContent(intent.getStringExtra("content"));
        task.setStartTime(intent.getStringExtra("start"));
        task.setEndTime(intent.getStringExtra("end"));

        LogUtil.d("AlarmService", "AlarmReceiver onReceive");

        Intent toAlert = new Intent(context, AlertActivity.class);
        toAlert.putExtra(DataUtil.TASK_DATA, task);
        toAlert.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(toAlert);
    }
}
