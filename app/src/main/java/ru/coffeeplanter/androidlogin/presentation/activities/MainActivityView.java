package ru.coffeeplanter.androidlogin.presentation.activities;

import ru.coffeeplanter.androidlogin.presentation.BaseView;

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

interface MainActivityView extends BaseView {

    void addLoginFragment();
    void addLoggedInFragment(String login);
    void replaceFromLoginToLoggedInFragment(String login);
    void replaceFromLoggedInToLoginFragment();
    void returnToLoginFragment();
    void createTimeOutReceiver();

}