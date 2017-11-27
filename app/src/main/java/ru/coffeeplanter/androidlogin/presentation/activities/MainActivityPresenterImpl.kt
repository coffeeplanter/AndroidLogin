package ru.coffeeplanter.androidlogin.presentation.activities

import ru.coffeeplanter.androidlogin.domain.mainactivity.MainActivityInteractor
import ru.coffeeplanter.androidlogin.domain.mainactivity.MainActivityInteractorImpl

/**
 * Created by Ilya Solovyov on 20.09.2017.
 * is3k@ya.ru
 */

internal class MainActivityPresenterImpl(private var mainActivityView: MainActivityView?) : MainActivityPresenter {
    private val interactor: MainActivityInteractor

    init {
        interactor = MainActivityInteractorImpl(this)
    }

    override fun onActivityCreate() {
        interactor.chooseFragmentToLoadAtStart()
        mainActivityView!!.registerTimeOutReceiver()
    }

    override fun onActivityDestroy() {
        mainActivityView!!.unregisterTimeOutReceiver()
        mainActivityView = null
    }

    override fun onBackPressed() {
        mainActivityView!!.processBackPressed()
    }

    override fun onTimeOutBroadcastReceived() {
        interactor.clearAuthorizationData()
        mainActivityView!!.chooseFragmentOnBroadcastReceived()
    }

    override fun addLoginFragment() {
        mainActivityView!!.addLoginFragment()
    }

    override fun addLoggedInFragment(login: String) {
        mainActivityView!!.addLoggedInFragment(login)
    }

    override fun onSignedIn(login: String) {
        mainActivityView!!.replaceFromLoginToLoggedInFragment(login)
    }

    override fun onForgetUserPressed() {
        mainActivityView!!.replaceFromLoggedInToLoginFragment()
    }

}
