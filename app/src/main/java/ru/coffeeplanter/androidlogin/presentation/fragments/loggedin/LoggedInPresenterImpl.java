package ru.coffeeplanter.androidlogin.presentation.fragments.loggedin;

import ru.coffeeplanter.androidlogin.domain.loggedin.LoggedInInteractor;
import ru.coffeeplanter.androidlogin.domain.loggedin.LoggedInInteractorImpl;

@SuppressWarnings("SpellCheckingInspection")
class LoggedInPresenterImpl implements LoggedInPresenter, LoggedInInteractor.OnUserForgottenListener {

    private LoggedInView loggedInView;
    private LoggedInInteractor interactor;

    LoggedInPresenterImpl(LoggedInView loggedInView) {
        this.loggedInView = loggedInView;
        interactor = new LoggedInInteractorImpl(this);
    }

    @Override
    public void onFragmentCreateView() {
        if (loggedInView != null) {
            loggedInView.getDataFromFragmentArguments();
            loggedInView.setGreetingMessage();
        }
    }

    @Override
    public void exitButtonClicked() {
        if (loggedInView != null) {
            loggedInView.closeApp();
        }
    }

    @Override
    public void forgetUserButtonClicked() {
        interactor.forgetUser();
    }

    @Override
    public void onFragmentDestroy() {
        loggedInView = null;
    }

    @Override
    public void onUserForget() {
        if (loggedInView != null) {
            loggedInView.navigateToLoginFragment();
        }
    }

}
