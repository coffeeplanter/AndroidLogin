package ru.coffeeplanter.androidlogin.data.settings;

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

public interface SettingsSource {

    void saveTimeStamp(long timeStamp);
    long getTimeStamp();
    void saveEncryptedLogin(String encryptedLogin);
    String getLogin();
    void saveEncryptedPassword(String encryptedPassword);
    String getPassword();
    void saveTimeStampLoginAndPassword(long timeStamp, String encryptedLogin, String encryptedPassword);

}
