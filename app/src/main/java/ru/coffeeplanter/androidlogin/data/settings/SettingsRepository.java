package ru.coffeeplanter.androidlogin.data.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

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

    private SharedPreferences preferences;

    private Crypter crypter;

    public SettingsRepository() {
        preferences = App.getContext().getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
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
        preferences
                .edit()
                .putLong(APP_TIMESTAMP, timeStamp)
                .apply();
    }

    @Override
    public long getTimeStamp() {
        return preferences.getLong(APP_TIMESTAMP, 0L);
    }

    @Override
    public void saveLogin(String unEncryptedLogin) {
        preferences
                .edit()
                .putString(APP_PREFS_LOGIN, crypter.encrypt(unEncryptedLogin))
                .apply();
    }

    @Override
    public String getLogin() {
        return crypter.decrypt(preferences.getString(APP_PREFS_LOGIN, ""));
    }

    @Override
    public void savePassword(String unEncryptedPassword) {
        preferences
                .edit()
                .putString(APP_PREFS_PASSWORD, crypter.encrypt(unEncryptedPassword))
                .apply();
    }

    @Override
    public String getPassword() {
        return crypter.decrypt(preferences.getString(APP_PREFS_PASSWORD, ""));
    }

    @Override
    public void saveAuthorizationData(long timeStamp, String unEncryptedLogin, String unEncryptedPassword) {
        preferences
                .edit()
                .putLong(APP_TIMESTAMP, timeStamp)
                .putString(APP_PREFS_LOGIN, crypter.encrypt(unEncryptedLogin))
                .putString(APP_PREFS_PASSWORD, crypter.encrypt(unEncryptedPassword))
                .apply();
    }

    @Override
    public void clearAuthorizationData() {
        preferences
                .edit()
                .remove(APP_PREFS_LOGIN)
                .remove(APP_PREFS_PASSWORD)
                .remove(APP_TIMESTAMP)
                .apply();
    }

}
