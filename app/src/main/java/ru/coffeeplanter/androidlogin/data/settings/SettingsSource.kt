package ru.coffeeplanter.androidlogin.data.settings

import ru.coffeeplanter.androidlogin.data.BaseDataSource

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

interface SettingsSource : BaseDataSource {
    val timeStamp: Long
    val login: String
    val password: String

    fun saveTimeStamp(timeStamp: Long)
    fun saveLogin(nonEncryptedLogin: String)
    fun savePassword(nonEncryptedPassword: String)
    fun saveAuthorizationData(timeStamp: Long, nonEncryptedLogin: String, nonEncryptedPassword: String)
    fun clearAuthorizationData()

    companion object {
        val TIME_TO_KEEP_LOGIN: Long = 300
    }

}
