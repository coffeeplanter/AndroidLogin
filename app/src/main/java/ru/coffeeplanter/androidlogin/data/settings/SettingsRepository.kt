package ru.coffeeplanter.androidlogin.data.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings

import ru.coffeeplanter.androidlogin.platform.App

@Suppress("PrivatePropertyName")
/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

class SettingsRepository : SettingsSource {

    private val APP_PREFS = "settings"
    private val APP_PREFS_LOGIN = "login"
    private val APP_PREFS_PASSWORD = "password"
    private val APP_TIMESTAMP = "timestamp"

    private val preferences: SharedPreferences

    private val crypter: Crypter

    private val cryptKey: ByteArray
        @SuppressLint("HardwareIds")
        get() {
            @SuppressLint("HardwareIds")
            val secureId = Settings.Secure.getString(App.context?.contentResolver, Settings.Secure.ANDROID_ID)
            return secureId.toByteArray()
        }

    override val timeStamp: Long
        get() = preferences.getLong(APP_TIMESTAMP, 0L)

    override val login: String
        get() = crypter.decrypt(preferences.getString(APP_PREFS_LOGIN, ""))

    override val password: String
        get() = crypter.decrypt(preferences.getString(APP_PREFS_PASSWORD, ""))

    init {
        preferences = App.context?.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)!!
        val cryptKey = cryptKey
        crypter = Crypter(cryptKey)
    }

    override fun saveTimeStamp(timeStamp: Long) {
        preferences
                .edit()
                .putLong(APP_TIMESTAMP, timeStamp)
                .apply()
    }

    override fun saveLogin(nonEncryptedLogin: String) {
        preferences
                .edit()
                .putString(APP_PREFS_LOGIN, crypter.encrypt(nonEncryptedLogin))
                .apply()
    }

    override fun savePassword(nonEncryptedPassword: String) {
        preferences
                .edit()
                .putString(APP_PREFS_PASSWORD, crypter.encrypt(nonEncryptedPassword))
                .apply()
    }

    override fun saveAuthorizationData(timeStamp: Long, nonEncryptedLogin: String, nonEncryptedPassword: String) {
        preferences
                .edit()
                .putLong(APP_TIMESTAMP, timeStamp)
                .putString(APP_PREFS_LOGIN, crypter.encrypt(nonEncryptedLogin))
                .putString(APP_PREFS_PASSWORD, crypter.encrypt(nonEncryptedPassword))
                .apply()
    }

    override fun clearAuthorizationData() {
        preferences
                .edit()
                .remove(APP_PREFS_LOGIN)
                .remove(APP_PREFS_PASSWORD)
                .remove(APP_TIMESTAMP)
                .apply()
    }

}
