package ru.coffeeplanter.androidlogin.presentation.activities;

import ru.coffeeplanter.androidlogin.presentation.BasePresenter;

/**
 * Created by Ilya Solovyov on 20.09.2017.
 * is3k@ya.ru
 */

public interface MainActivityPresenter extends BasePresenter {

    void onActivityCreate();
    void addLoginFragment();
    void addLoggedInFragment(String login);
    void replaceFromLoginToLoggedInFragment(String login);
    void replaceFromLoggedInToLoginFragment();
    void returnToLoginFragment();
    void onSignedIn(String login);
    void onActivityDestroy();
    void onBackPressed();

}
