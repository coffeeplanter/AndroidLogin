package ru.coffeeplanter.androidlogin.presentation.fragments.loggedin

import ru.coffeeplanter.androidlogin.domain.loggedin.LoggedInInteractor
import ru.coffeeplanter.androidlogin.domain.loggedin.LoggedInInteractorImpl

internal class LoggedInPresenterImpl(private var loggedInView: LoggedInView?) : LoggedInPresenter, LoggedInInteractor.OnUserForgottenListener {
    private val interactor: LoggedInInteractor

    init {
        interactor = LoggedInInteractorImpl(this)
    }

    override fun onFragmentCreateView() {
        if (loggedInView != null) {
            loggedInView!!.getDataFromFragmentArguments()
            loggedInView!!.setGreetingMessage()
        }
    }

    override fun exitButtonClicked() {
        if (loggedInView != null) {
            loggedInView!!.closeApp()
        }
    }

    override fun forgetUserButtonClicked() {
        interactor.forgetUser()
    }

    override fun onFragmentDestroy() {
        loggedInView = null
    }

    override fun onUserForget() {
        if (loggedInView != null) {
            loggedInView!!.navigateToLoginFragment()
        }
    }

}
