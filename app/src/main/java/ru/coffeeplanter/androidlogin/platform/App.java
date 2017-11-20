package ru.coffeeplanter.androidlogin.platform;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

@SuppressWarnings("SpellCheckingInspection")
public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}
