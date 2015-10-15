package com.github.prefanatic.rxfirebasesample;

import android.app.Application;

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RxFirebase.setAndroidContext(this);
    }
}
