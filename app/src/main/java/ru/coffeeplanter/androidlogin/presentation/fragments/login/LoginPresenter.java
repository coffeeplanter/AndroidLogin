package ru.coffeeplanter.androidlogin.presentation.fragments.login;

interface LoginPresenter {

    boolean validateLoginAndPassword(String login, String password);
    boolean tryToLogin(String login, String password);
    void onFragmentPause();
    void onFragmentDestroy();

}
