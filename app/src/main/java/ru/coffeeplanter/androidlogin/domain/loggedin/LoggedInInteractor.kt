package ru.coffeeplanter.androidlogin.domain.loggedin

import ru.coffeeplanter.androidlogin.domain.BaseInteractor

/**
 * Created by Ilya Solovyov on 21.11.2017.
 * is3k@ya.ru
 */

interface LoggedInInteractor : BaseInteractor {

    interface OnUserForgottenListener {
        fun onUserForget()
    }

    fun forgetUser()

}
