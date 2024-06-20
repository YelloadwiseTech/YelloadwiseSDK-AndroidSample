package ir.yelloadwise.sample;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import ir.yelloadwise.app.Yelloadwise;

public class MainApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Yelloadwise.initialize(this, BuildConfig.YELLOADWISE_KEY);
    }
}
