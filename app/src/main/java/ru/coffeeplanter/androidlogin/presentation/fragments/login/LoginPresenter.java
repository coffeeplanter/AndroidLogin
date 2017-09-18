package ru.coffeeplanter.androidlogin.presentation.fragments.login;

import ru.coffeeplanter.androidlogin.presentation.BasePresenter;

interface LoginPresenter extends BasePresenter {

    void validateLoginAndPassword(String login, String password);
    void tryToLogin(String login, String password);
    void onFragmentPause();
    void onFragmentDestroy();

}
