package ir.yelloadwise.sample.activities.navideAds;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ir.yelloadwise.app.nativeads.YelloadwiseNativeVideoAdShowListener;
import ir.yelloadwise.sample.BuildConfig;
import ir.yelloadwise.sample.R;
import ir.yelloadwise.sample.utils.Tools;
import ir.yelloadwise.app.nativeads.YelloadwiseNativeVideoAd;
import ir.yelloadwise.app.nativeads.YelloadwiseNativeVideoAdLoadListener;
import ir.yelloadwise.app.nativeads.YelloadwiseNativeVideoAdLoader;

public class NativeVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "NativeVideoActivity";
    private FrameLayout adContainer;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_video);

        initView();
    }

    private void initView() {
        Button btnRequest = findViewById(R.id.btnRequest);
        adContainer = findViewById(R.id.adContainer);
        tvLog = findViewById(R.id.tvLog);

        btnRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        requestNativeVideoAd();
    }

    private void requestNativeVideoAd() {
        new YelloadwiseNativeVideoAdLoader.Builder()
                .setContentViewTemplate(R.layout.yelloadwise_content_video_ad_template)
                .setAutoStartVideoOnScreenEnabled(false)
                .setFullscreenBtnEnabled(true)
                .setMuteVideoBtnEnabled(false)
                .setMuteVideo(false)
                .loadAd(NativeVideoActivity.this, BuildConfig.YELLOADWISE_NATIVE_VIDEO, new YelloadwiseNativeVideoAdLoadListener() {
                    @Override
                    public void onNoNetwork() {
                        if (Tools.isActivityDestroyed(NativeVideoActivity.this)) {
                            return;
                        }

                        Log.d(TAG, "No Network Available");
                        tvLog.append("\nonNoNetwork");
                    }

                    @Override
                    public void onNoAdAvailable() {
                        if (Tools.isActivityDestroyed(NativeVideoActivity.this)) {
                            return;
                        }

                        Log.d(TAG, "No Native Video Ad Available");
                        tvLog.append("\nonNoAdAvailable");
                    }

                    @Override
                    public void onError(String error) {
                        if (Tools.isActivityDestroyed(NativeVideoActivity.this)) {
                            return;
                        }

                        Log.d(TAG, "Error: " + error);
                        tvLog.append("\nonError " + error);
                    }

                    @Override
                    public void onRequestFilled(YelloadwiseNativeVideoAd nativeVideoAd) {
                        if (Tools.isActivityDestroyed(NativeVideoActivity.this)) {
                            return;
                        }

                        Log.d(TAG, "onRequestFilled");
                        tvLog.append("\nonRequestFilled");

                        nativeVideoAd.setShowListener(showListener);
                        nativeVideoAd.addToParentView(adContainer);
                        nativeVideoAd.addToParentView(adContainer);
                    }
                });

    }

    private final YelloadwiseNativeVideoAdShowListener showListener = new YelloadwiseNativeVideoAdShowListener() {
        @Override
        public void onAdFinished(String adId) {
            Log.d(TAG, "onAdShowFinished: " + adId);
            tvLog.append("\nonAdShowFinished");
        }

        @Override
        public void onAdClicked() {
            Log.d(TAG, "onAdClicked");
            tvLog.append("\nonAdClicked");
        }
    };

}
