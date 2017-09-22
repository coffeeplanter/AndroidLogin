package ru.coffeeplanter.androidlogin.presentation.fragments.login;

import ru.coffeeplanter.androidlogin.domain.login.LoginInteractor;
import ru.coffeeplanter.androidlogin.domain.login.LoginInteractorImpl;

class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor interactor;

    private String login;
    private String password;

    LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl(this, this);
    }

    @Override
    public void validateLoginAndPassword(String login, String password) {
        if (interactor != null) {
            interactor.validateLoginAndPassword(login, password);
        }
    }

    @Override
    public void tryToLogin(final String login, final String password) {
        this.login = login;
        this.password = password;
        if (loginView != null) {
            loginView.switchToWaitingMode();
        }
        if (interactor != null) {
            interactor.tryToLogin(login, password);
        }
    }

    @Override
    public void onFragmentPause() {
        // Removing entered data due to safety reasons.
        if (loginView != null) {
            loginView.clearFieldsData();
        }
    }

    @Override
    public void onFragmentDestroy() {
        loginView = null;
    }

    @Override
    public void onLoginError(String error) {
        if (loginView != null) {
            loginView.setLoginError(error);
            loginView.setLoginFocused();
        }
    }

    @Override
    public void onPasswordError(String error) {
        if (loginView != null) {
            loginView.setPasswordError(error);
            loginView.setPasswordFocused();
        }
    }

    @Override
    public void onLoginSuccess() {
        if (loginView != null) {
            loginView.switchOffWaitingMode();
            loginView.hideKeyboard();
            loginView.navigateToLoggedInFragment(login, password);
        }
    }

    @Override
    public void onLoginFail() {
        login = "";
        password = "";
        if (loginView != null) {
            loginView.switchOffWaitingMode();
            loginView.setFocusedView();
            loginView.showKeyboard();
        }
    }

}
