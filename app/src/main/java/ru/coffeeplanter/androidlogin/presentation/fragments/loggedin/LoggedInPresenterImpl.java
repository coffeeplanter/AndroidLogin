package ru.coffeeplanter.androidlogin.presentation.fragments.loggedin;

class LoggedInPresenterImpl implements LoggedInPresenter {

    private LoggedInView loggedInView;

    LoggedInPresenterImpl(LoggedInView loggedInView) {
        this.loggedInView = loggedInView;
    }

    @Override
    public void onFragmentCreateView() {
        if (loggedInView != null) {
            loggedInView.getDataFromArguments();
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
        // TODO: logOff
        if (loggedInView != null) {
            loggedInView.navigateToLoginFragment();
        }
    }

    @Override
    public void onFragmentDestroy() {
        loggedInView = null;
    }

}
