package ru.coffeeplanter.androidlogin.presentation.fragments.login

import ru.coffeeplanter.androidlogin.presentation.BaseView

internal interface LoginView : BaseView {

    fun switchToWaitingMode()
    fun switchOffWaitingMode()
    fun setLoginError(error: String?)
    fun setPasswordError(error: String?)
    fun clearFieldsData()
    fun setLoginFocused()
    fun setPasswordFocused()
    fun setFocusedView()
    fun navigateToLoggedInFragment(login: String, password: String)
    fun showKeyboard()
    fun hideKeyboard()
    fun fadeStatusBar()
    fun unfadeStatusBar()

}
