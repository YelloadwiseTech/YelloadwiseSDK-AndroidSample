package ir.yelloadwise.sample.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ir.yelloadwise.app.Yelloadwise;
import ir.yelloadwise.app.YelloadwiseAdRequestListener;
import ir.yelloadwise.app.YelloadwiseAdRequestOptions;
import ir.yelloadwise.app.YelloadwiseAdShowListener;
import ir.yelloadwise.app.YelloadwiseShowOptions;
import ir.yelloadwise.sample.BuildConfig;
import ir.yelloadwise.sample.R;
import ir.yelloadwise.sample.utils.Tools;

public class RewardActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "RewardActivity";
    private TextView tvLog;
    private String adId;
    private String zoneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        initView();
    }

    private void initView() {
        Button btnRequest = findViewById(R.id.btnRequest);
        Button btnShow = findViewById(R.id.btnShow);
        tvLog = findViewById(R.id.tvLog);

        btnRequest.setOnClickListener(this);
        btnShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRequest:
                requestAd();
                break;

            case R.id.btnShow:
                showAd();
                break;
        }
    }

    private void requestAd() {
        Yelloadwise.requestAd(this,
                BuildConfig.YELLOADWISE_REWARDED_VIDEO,
                new YelloadwiseAdRequestOptions(),
                new YelloadwiseAdRequestListener() {

                    @Override
                    public void onAdAvailable(String adId) {
                        Log.d(TAG, "on ad AdAvailable");
                        RewardActivity.this.adId = adId;
                        RewardActivity.this.zoneId = BuildConfig.YELLOADWISE_REWARDED_VIDEO;
                        if (Tools.isActivityDestroyed(RewardActivity.this)) {
                            return;
                        }

                        tvLog.append("\nonAdAvailable");
                    }

                    @Override
                    public void onError(String message) {
                        if (Tools.isActivityDestroyed(RewardActivity.this)) {
                            return;
                        }
                        Log.d(TAG, "on ad Error" + message);
                        tvLog.append("\nonError " + message);
                    }
                });
    }

    private void showAd() {
        Yelloadwise.showAd(this,
                zoneId,
                adId,
                new YelloadwiseShowOptions(),
                new YelloadwiseAdShowListener() {
                    @Override
                    public void onOpened() {
                        Log.d(TAG, "on ad opened");
                        tvLog.append("\nonOpened");
                    }

                    @Override
                    public void onClosed() {
                        Log.d(TAG, "on ad closed");
                        tvLog.append("\nonClosed");
                    }

                    @Override
                    public void onError(String message) {
                        Log.d(TAG, "on error " + message);
                        tvLog.append("\non error " + message);
                    }

                    @Override
                    public void onRewarded(boolean completed) {
                        Log.d(TAG, "on Rewarded " + completed);
                        tvLog.append("\non Rewarded " + completed);
                    }
                });
    }

}
