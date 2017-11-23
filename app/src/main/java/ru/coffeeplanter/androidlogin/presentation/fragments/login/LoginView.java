package ru.coffeeplanter.androidlogin.presentation.fragments.login;

import ru.coffeeplanter.androidlogin.presentation.BaseView;

@SuppressWarnings("SpellCheckingInspection")
interface LoginView extends BaseView {

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
    void fadeStatusBar();
    void unfadeStatusBar();

}
