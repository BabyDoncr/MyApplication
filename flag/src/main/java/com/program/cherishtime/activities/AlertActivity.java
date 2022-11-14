package com.program.cherishtime.activities;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.program.cherishtime.R;
import com.program.cherishtime.bean.Task;
import com.program.cherishtime.utils.DataUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertActivity extends AppCompatActivity {

    @BindView(R.id.alert_dialog_title)
    TextView alertDialogTitle;
    @BindView(R.id.alert_dialog_time)
    TextView alertDialogTime;
    @BindView(R.id.alert_dialog_content)
    TextView alertDialogContent;
    @BindView(R.id.alert_dialog_ok)
    LinearLayout alertDialogOk;

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Task task = (Task) getIntent().getParcelableExtra(DataUtil.TASK_DATA);
        mediaPlayer = MediaPlayer.create(this, R.raw.bell);
        mediaPlayer.start();
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(30000);
        }

        if (task != null)
            initDialog(task);
    }

    private void initDialog(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        AlertDialog dialog = builder.create();
        dialog.show();

        alertDialogTitle.setText(task.getTitle());
        alertDialogTime.setText(String.format("%s - %s", task.getStartTime(), task.getEndTime()));
        alertDialogContent.setText(task.getContent());
        alertDialogOk.setOnClickListener(v -> {
            startActivity(new Intent(AlertActivity.this, MainActivity.class));
            dialog.dismiss();
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vibrator.hasVibrator()) {
            vibrator.cancel();
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
