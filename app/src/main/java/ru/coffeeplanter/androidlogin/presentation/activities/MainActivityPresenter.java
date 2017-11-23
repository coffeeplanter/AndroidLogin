package ru.coffeeplanter.androidlogin.presentation.activities;

import ru.coffeeplanter.androidlogin.presentation.BasePresenter;

/**
 * Created by Ilya Solovyov on 20.09.2017.
 * is3k@ya.ru
 */

@SuppressWarnings("SpellCheckingInspection")
public interface MainActivityPresenter extends BasePresenter {

    void onActivityCreate();
    void addLoginFragment();
    void addLoggedInFragment(String login);
    void onSignedIn(String login);
    void onForgetUserPressed();
    void onActivityDestroy();
    void onBackPressed();
    void onTimeOutBroadcastReceived();

}
