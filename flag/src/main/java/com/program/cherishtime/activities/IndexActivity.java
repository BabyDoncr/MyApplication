package com.program.cherishtime.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;

import com.program.cherishtime.R;
import com.program.cherishtime.bean.Prompt;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class IndexActivity extends BaseActivity {

    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;
    @BindView(R.id.onboard_b_choice_1)
    Button mBChoice1;
    @BindView(R.id.onboard_b_choice_2)
    Button mBChoice2;
    @BindView(R.id.onboard_ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.onboard_iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.prompt_sentence)
    TextView promptSentence;
    @BindView(R.id.prompt_author)
    TextView promptAuthor;

    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);

        initData();
        initAnimation();

        mBChoice1.setOnClickListener(view -> {
            Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
            startActivity(intent);

        });

        mBChoice2.setOnClickListener(view -> {
            Intent intent = new Intent(IndexActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void initData() {
        mDisposable = NetworkWrapper.getInstance()
                .getDesertApiClient()
                .onloadPrompt()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onFail);
    }

    private void onSuccess(Prompt prompt) {
        promptSentence.setText(prompt.getSentence());
        promptAuthor.setText(String.format("--%s", prompt.getAuthor()));
    }

    private void onFail(Throwable throwable) {
        LogUtil.d("IndexActivity", throwable.toString());
        promptSentence.setText("时间就像海绵里的水，只要愿挤，总还是有的。");
        promptAuthor.setText("--鲁迅");
    }

    private void initAnimation() {
        ViewCompat.animate(mIvLogo)
                .translationY(-300)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION)
                .setInterpolator(new DecelerateInterpolator(1.2f))
                .start();

        for (int i = 0; i < mLlContainer.getChildCount(); i++) {
            View view = mLlContainer.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (!(view instanceof Button)) {
                viewAnimator = ViewCompat.animate(view)
                        .translationY(50).alpha(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(ANIM_ITEM_DURATION);
            } else {
                viewAnimator = ViewCompat.animate(view)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(ANIM_ITEM_DURATION / 2);
            }
            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        String sign = MyApplication.getContext().getString(R.string.loginSign);
        if (sp.getString(DataUtil.LOGIN_SIGN, "fail").equals(sign)) {
            MainActivity.actionStart(IndexActivity.this, sp.getString("nickname", "null"));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null)
            mDisposable.dispose();
        super.onDestroy();
    }
}
