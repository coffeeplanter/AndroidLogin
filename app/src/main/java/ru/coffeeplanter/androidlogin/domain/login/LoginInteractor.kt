package ru.coffeeplanter.androidlogin.domain.login

import ru.coffeeplanter.androidlogin.domain.BaseInteractor

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

interface LoginInteractor : BaseInteractor {

    interface OnLoginFinishedListener {
        fun onLoginError(error: String?)
        fun onPasswordError(error: String?)
        fun onLoginSuccess()
        fun onLoginFail()
    }

    fun validateLoginAndPassword(login: String, password: String): Boolean

    fun tryToLogin(login: String, password: String)

}
