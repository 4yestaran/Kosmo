package me.ayestaran.kosmo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by vince on 5/11/15.
 */
public class MyApplication extends Application {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
