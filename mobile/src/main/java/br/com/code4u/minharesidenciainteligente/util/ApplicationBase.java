package br.com.code4u.minharesidenciainteligente.util;

import android.app.Application;
import android.content.Context;

public class ApplicationBase extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationBase.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ApplicationBase.context;
    }
}