package ru.coffeeplanter.androidlogin.data.settings;

import android.content.Context;
import android.content.SharedPreferences;

import ru.coffeeplanter.androidlogin.platform.App;

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

public class SettingsRepository implements SettingsSource {

    private final String APP_PREFS = "settings";
    private final String APP_PREFS_LOGIN = "login";
    private final String APP_PREFS_PASSWORD = "password";
    private final String APP_TIMESTAMP = "timestamp";

    private SharedPreferences prefs = App.getContext().getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);

    @Override
    public void saveTimeStamp(long timeStamp) {
        prefs
                .edit()
                .putLong(APP_TIMESTAMP, timeStamp)
                .apply();
    }

    @Override
    public long getTimeStamp() {
        return prefs.getLong(APP_TIMESTAMP, 0L);
    }

    @Override
    public void saveEncryptedLogin(String encryptedLogin) {
        prefs
                .edit()
                .putString(APP_PREFS_LOGIN, encryptedLogin)
                .apply();
    }

    @Override
    public String getLogin() {
        return prefs.getString(APP_PREFS_LOGIN, "");
    }

    @Override
    public void saveEncryptedPassword(String encryptedPassword) {
        prefs
                .edit()
                .putString(APP_PREFS_PASSWORD, encryptedPassword)
                .apply();
    }

    @Override
    public String getPassword() {
        return prefs.getString(APP_PREFS_PASSWORD, "");
    }

    @Override
    public void saveTimeStampLoginAndPassword(long timeStamp, String encryptedLogin, String encryptedPassword) {
        prefs
                .edit()
                .putLong(APP_TIMESTAMP, timeStamp)
                .putString(APP_PREFS_LOGIN, encryptedLogin)
                .putString(APP_PREFS_PASSWORD, encryptedPassword)
                .apply();
    }

}
