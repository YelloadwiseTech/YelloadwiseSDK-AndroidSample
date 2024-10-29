package ir.yelloadwise.sample.activities.navideAds;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ir.yelloadwise.app.YelloadwiseAdRequestListener;
import ir.yelloadwise.app.nativeads.YelloadwiseNativeBannerManager;
import ir.yelloadwise.app.nativeads.YelloadwiseNativeBannerType;
import ir.yelloadwise.app.nativeads.YelloadwiseNativeBannerViewManager;
import ir.yelloadwise.sample.R;
import ir.yelloadwise.sample.utils.Tools;

public class NativeBannerActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "NativeBannerActivity";
    private Button btnShow;
    private YelloadwiseNativeBannerViewManager nativeBannerViewManager;
    private String adId = null;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_banner);
        initView();
    }

    private void initView() {
        FrameLayout adContainer = findViewById(R.id.adContainer);
        Button btnRequest = findViewById(R.id.btnRequest);
        btnShow = findViewById(R.id.btnShow);
        tvLog = findViewById(R.id.tvLog);

        btnRequest.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnShow.setEnabled(false);

        nativeBannerViewManager = new YelloadwiseNativeBannerManager.Builder()
                .setParentView(adContainer)
                .setContentViewTemplate(R.layout.yelloadwise_content_banner_ad_template)
                .inflateTemplate(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRequest:
                requestNativeBannerAd();
                break;

            case R.id.btnShow:
                showAd();
                break;
        }
    }

    private void requestNativeBannerAd() {
        YelloadwiseNativeBannerManager.getAd(this, BuildConfig.YELLOADWISE_NATIVE_BANNER,
                YelloadwiseNativeBannerType.BANNER_1136x640,
                new YelloadwiseAdRequestListener() {
                    @Override
                    public void onAdAvailable(String adId) {
                        Log.d(TAG, "onResponse");

                        if (Tools.isActivityDestroyed(NativeBannerActivity.this)) {
                            return;
                        }

                        tvLog.append("\nonAdAvailable");
                        NativeBannerActivity.this.adId = adId;
                        btnShow.setEnabled(true);
                    }

                    @Override
                    public void onError(String message) {
                        if (Tools.isActivityDestroyed(NativeBannerActivity.this)) {
                            return;
                        }

                        Log.d(TAG, "onFailed" + message);
                        tvLog.append("\nonFailed " + message);
                    }
                });
    }

    private void showAd() {
        if (adId == null) {
            return;
        }

        YelloadwiseNativeBannerManager.bindAd(
                this,
                nativeBannerViewManager,
                BuildConfig.YELLOADWISE_NATIVE_BANNER,
                adId);

        btnShow.setEnabled(false);
    }
}
