package ru.coffeeplanter.androidlogin.platform;

import android.os.Handler;

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

public class LoginMocker {

    private static final int DEFAULT_LOGIN_MOCK_TIME = 2000; // Delay in milliseconds.

    public static void execute(Runnable runnable) {
        execute(runnable, DEFAULT_LOGIN_MOCK_TIME);
    }

    public static void execute(Runnable runnable, int delay) {
        new Handler().postDelayed(runnable, delay);
    }

}
