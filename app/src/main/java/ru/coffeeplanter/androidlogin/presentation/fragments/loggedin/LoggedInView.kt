package ru.coffeeplanter.androidlogin.presentation.fragments.loggedin

import ru.coffeeplanter.androidlogin.presentation.BaseView

internal interface LoggedInView : BaseView {

    fun getDataFromFragmentArguments()
    fun setGreetingMessage()
    fun closeApp()
    fun navigateToLoginFragment()

}
