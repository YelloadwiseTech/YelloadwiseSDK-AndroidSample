package ir.yelloadwise.sample.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ir.yelloadwise.app.bannerads.YelloadwiseBannerType;
import ir.yelloadwise.app.bannerads.YelloadwiseBannerView;
import ir.yelloadwise.app.bannerads.YelloadwiseBannerViewEventListener;
import ir.yelloadwise.sample.BuildConfig;
import ir.yelloadwise.sample.R;

public class StandardActivity extends AppCompatActivity {

    private YelloadwiseBannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);

        bannerView = findViewById(R.id.bannerView);
        bannerView.setEventListener(new YelloadwiseBannerViewEventListener() {
            @Override
            public void onNoAdAvailable() {

                Log.e("onNoAdAvailable", "called!");
            }

            @Override
            public void onNoNetwork() {

                Log.e("onNoNetwork", "called!");
            }

            @Override
            public void onError(String errorMessage) {

                Log.e("onError", errorMessage);
            }

            @Override
            public void onRequestFilled() {

                Log.e("onRequestFilled", "called!");
            }

            @Override
            public void onHideBannerView() {

                Log.e("onHideBannerView", "called!");
            }

            @Override
            public void onAdClicked() {
                Log.e("onAdClicked", "called!");
            }
        });

        bannerView.loadAd(StandardActivity.this, BuildConfig.YELLOADWISE_STANDARD_BANNER, YelloadwiseBannerType.BANNER_320x50);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerView.destroy();
    }
}
