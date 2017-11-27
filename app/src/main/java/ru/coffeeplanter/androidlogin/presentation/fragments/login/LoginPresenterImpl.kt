package ru.coffeeplanter.androidlogin.presentation.fragments.login

import ru.coffeeplanter.androidlogin.domain.login.LoginInteractor
import ru.coffeeplanter.androidlogin.domain.login.LoginInteractorImpl
import ru.coffeeplanter.androidlogin.platform.TimerService

internal class LoginPresenterImpl(private var loginView: LoginView?) : LoginPresenter, LoginInteractor.OnLoginFinishedListener {
    private val interactor: LoginInteractor?

    private var login: String? = null
    private var password: String? = null

    init {
        interactor = LoginInteractorImpl(this)
    }

    override fun validateLoginAndPassword(login: String, password: String) {
        interactor?.validateLoginAndPassword(login, password)
    }

    override fun tryToLogin(login: String, password: String) {
        this.login = login
        this.password = password
        if (loginView != null) {
            loginView!!.switchToWaitingMode()
        }
        interactor?.tryToLogin(login, password)
    }

    override fun onFragmentPause() {
        // Removing entered data due to safety reasons.
        if (loginView != null) {
            loginView!!.clearFieldsData()
        }
    }

    override fun onFragmentDestroy() {
        loginView = null
    }

    override fun onLoginError(error: String?) {
        loginView?.setLoginError(error)
        loginView?.setLoginFocused()
    }

    override fun onPasswordError(error: String?) {
        loginView?.setPasswordError(error)
        loginView?.setPasswordFocused()
    }

    override fun onLoginSuccess() {
        if (loginView != null) {
            loginView!!.switchOffWaitingMode()
            loginView!!.hideKeyboard()
            loginView!!.navigateToLoggedInFragment(login!!, password!!)
            TimerService.start()
        }
    }

    override fun onLoginFail() {
        login = ""
        password = ""
        if (loginView != null) {
            loginView!!.switchOffWaitingMode()
            loginView!!.setFocusedView()
            loginView!!.showKeyboard()
        }
    }

}
