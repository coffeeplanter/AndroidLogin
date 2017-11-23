package ru.coffeeplanter.androidlogin.data.settings;

import ru.coffeeplanter.androidlogin.data.BaseDataSource;

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public interface SettingsSource extends BaseDataSource {

    long TIME_TO_KEEP_LOGIN = 300;

    void saveTimeStamp(long timeStamp);
    long getTimeStamp();
    void saveLogin(String nonEncryptedLogin);
    String getLogin();
    void savePassword(String nonEncryptedPassword);
    String getPassword();
    void saveAuthorizationData(long timeStamp, String nonEncryptedLogin, String nonEncryptedPassword);
    void clearAuthorizationData();

}
