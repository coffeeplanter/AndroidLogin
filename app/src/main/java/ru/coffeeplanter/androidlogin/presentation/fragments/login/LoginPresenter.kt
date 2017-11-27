package ru.coffeeplanter.androidlogin.presentation.fragments.login

import ru.coffeeplanter.androidlogin.presentation.BasePresenter

internal interface LoginPresenter : BasePresenter {

    fun validateLoginAndPassword(login: String, password: String)
    fun tryToLogin(login: String, password: String)
    fun onFragmentPause()
    fun onFragmentDestroy()

}
