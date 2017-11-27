package ru.coffeeplanter.androidlogin.presentation.activities

import ru.coffeeplanter.androidlogin.presentation.BasePresenter

/**
 * Created by Ilya Solovyov on 20.09.2017.
 * is3k@ya.ru
 */

interface MainActivityPresenter : BasePresenter {

    fun onActivityCreate()
    fun addLoginFragment()
    fun addLoggedInFragment(login: String)
    fun onSignedIn(login: String)
    fun onForgetUserPressed()
    fun onActivityDestroy()
    fun onBackPressed()
    fun onTimeOutBroadcastReceived()

}
