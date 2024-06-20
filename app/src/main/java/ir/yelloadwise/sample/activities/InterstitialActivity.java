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

public class InterstitialActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "InterstitialActivity";
    private Button btnShowAd;
    private TextView tvLog;
    private String adId;
    private String zoneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        initView();
    }

    private void initView() {
        Button btnInterstitialBanner = findViewById(R.id.btnInterstitialBanner);
        Button btnInterstitialVideo = findViewById(R.id.btnInterstitialVideo);

        btnShowAd = findViewById(R.id.btnShowAd);
        tvLog = findViewById(R.id.tvLog);

        btnInterstitialBanner.setOnClickListener(this);
        btnInterstitialVideo.setOnClickListener(this);

        btnShowAd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInterstitialBanner:
                requestInterstitialBannerAd(BuildConfig.YELLOADWISE_INTERSTITIAL_BANNER);
                break;

            case R.id.btnInterstitialVideo:
                requestInterstitialBannerAd(BuildConfig.YELLOADWISE_INTERSTITIAL_VIDEO);
                break;

            case R.id.btnShowAd:
                showAd();
                break;
        }
    }

    private void requestInterstitialBannerAd(final String zoneId) {
        Yelloadwise.requestAd(this,
                zoneId,
                new YelloadwiseAdRequestOptions(),
                new YelloadwiseAdRequestListener() {
                    @Override
                    public void onAdAvailable(String adId) {
                        Log.d(TAG, "on ad AdAvailable");
                        InterstitialActivity.this.adId = adId;
                        InterstitialActivity.this.zoneId = zoneId;

                        if (Tools.isActivityDestroyed(InterstitialActivity.this)) {
                            return;
                        }

                        tvLog.append("\nonAdAvailable");
                        btnShowAd.setEnabled(true);
                    }

                    @Override
                    public void onError(String message) {
                        if (Tools.isActivityDestroyed(InterstitialActivity.this)) {
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

                });
    }
}