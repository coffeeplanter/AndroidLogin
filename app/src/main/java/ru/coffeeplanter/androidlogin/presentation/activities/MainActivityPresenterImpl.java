package ru.coffeeplanter.androidlogin.presentation.activities;

import ru.coffeeplanter.androidlogin.domain.mainactivity.MainActivityInteractor;
import ru.coffeeplanter.androidlogin.domain.mainactivity.MainActivityInteractorImpl;

/**
 * Created by Ilya Solovyov on 20.09.2017.
 * is3k@ya.ru
 */

@SuppressWarnings("SpellCheckingInspection")
class MainActivityPresenterImpl implements MainActivityPresenter {

    private MainActivityView mainActivityView;
    private MainActivityInteractor interactor;

    MainActivityPresenterImpl(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
        interactor = new MainActivityInteractorImpl(this);
    }

    @Override
    public void onActivityCreate() {
        interactor.chooseFragmentToLoadAtStart();
        mainActivityView.registerTimeOutReceiver();
    }

    @Override
    public void onActivityDestroy() {
        mainActivityView.unregisterTimeOutReceiver();
        mainActivityView = null;
    }

    @Override
    public void onBackPressed() {
        mainActivityView.processBackPressed();
    }

    @Override
    public void onTimeOutBroadcastReceived() {
        interactor.clearAuthorizationData();
        mainActivityView.chooseFragmentOnBroadcastReceived();
    }

    @Override
    public void addLoginFragment() {
        mainActivityView.addLoginFragment();
    }

    @Override
    public void addLoggedInFragment(String login) {
        mainActivityView.addLoggedInFragment(login);
    }

    @Override
    public void onSignedIn(String login) {
        mainActivityView.replaceFromLoginToLoggedInFragment(login);
    }

    @Override
    public void onForgetUserPressed() {
        mainActivityView.replaceFromLoggedInToLoginFragment();
    }

}
