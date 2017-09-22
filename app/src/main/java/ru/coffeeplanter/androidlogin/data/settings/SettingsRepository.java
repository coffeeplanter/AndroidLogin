package ru.coffeeplanter.androidlogin.data.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import ru.coffeeplanter.androidlogin.domain.Crypter;
import ru.coffeeplanter.androidlogin.platform.App;

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

public class SettingsRepository implements SettingsSource {

    @SuppressWarnings("FieldCanBeLocal")
    private final String APP_PREFS = "settings";
    private final String APP_PREFS_LOGIN = "login";
    private final String APP_PREFS_PASSWORD = "password";
    private final String APP_TIMESTAMP = "timestamp";

    private SharedPreferences prefs;

    private Crypter crypter;

    public SettingsRepository() {
        prefs = App.getContext().getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        byte[] cryptKey = getCryptKey();
        crypter = new Crypter((cryptKey));
    }

    private byte[] getCryptKey() {
        @SuppressLint("HardwareIds")
        String secureId = Settings.Secure.getString(App.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return secureId.getBytes();
    }

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
    public void saveLogin(String nonEncryptedLogin) {
        prefs
                .edit()
                .putString(APP_PREFS_LOGIN, crypter.encrypt(nonEncryptedLogin))
                .apply();
    }

    @Override
    public String getLogin() {
        return crypter.decrypt(prefs.getString(APP_PREFS_LOGIN, ""));
    }

    @Override
    public void savePassword(String nonEncryptedPassword) {
        prefs
                .edit()
                .putString(APP_PREFS_PASSWORD, crypter.encrypt(nonEncryptedPassword))
                .apply();
    }

    @Override
    public String getPassword() {
        return crypter.decrypt(prefs.getString(APP_PREFS_PASSWORD, ""));
    }

    @Override
    public void saveAuthorizationData(long timeStamp, String nonEncryptedLogin, String nonEncryptedPassword) {
        prefs
                .edit()
                .putLong(APP_TIMESTAMP, timeStamp)
                .putString(APP_PREFS_LOGIN, crypter.encrypt(nonEncryptedLogin))
                .putString(APP_PREFS_PASSWORD, crypter.encrypt(nonEncryptedPassword))
                .apply();
    }

    @Override
    public void clearAuthorizationData() {
        prefs
                .edit()
                .remove(APP_PREFS_LOGIN)
                .remove(APP_PREFS_PASSWORD)
                .remove(APP_TIMESTAMP)
                .apply();
    }

}
