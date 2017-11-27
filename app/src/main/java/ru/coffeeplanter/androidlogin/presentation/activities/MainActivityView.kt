package ru.coffeeplanter.androidlogin.presentation.activities

import ru.coffeeplanter.androidlogin.presentation.BaseView

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

internal interface MainActivityView : BaseView {

    fun addLoginFragment()
    fun addLoggedInFragment(login: String)
    fun replaceFromLoginToLoggedInFragment(login: String)
    fun replaceFromLoggedInToLoginFragment()
    fun registerTimeOutReceiver()
    fun unregisterTimeOutReceiver()
    fun chooseFragmentOnBroadcastReceived()
    fun processBackPressed()

}
