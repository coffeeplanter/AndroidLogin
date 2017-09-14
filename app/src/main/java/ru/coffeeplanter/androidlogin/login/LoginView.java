package ru.coffeeplanter.androidlogin.login;

interface LoginView {

    void switchToWaitingMode();
    void switchOffWaitingMode();
    void setLoginError(String error);
    void setPasswordError(String error);
    void clearFieldsData();
    void setLoginFocused();
    void setPasswordFocused();
    void setFocusedView();
    void navigateToLoggedInFragment(String login, String password);
    void showKeyboard();
    void hideKeyboard();

}
