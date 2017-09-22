package ru.coffeeplanter.androidlogin.presentation.activities;

import ru.coffeeplanter.androidlogin.domain.mainactivity.MainActivityInteractor;
import ru.coffeeplanter.androidlogin.domain.mainactivity.MainActivityInteractorImpl;

/**
 * Created by Ilya Solovyov on 20.09.2017.
 * is3k@ya.ru
 */

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

//        if (interactor.isUserAuthorized()) {
//            mainActivityView.addLoggedInFragment();
//        } else {
//            mainActivityView.addLoginFragment();
//        }

        mainActivityView.createTimeOutReceiver();

        // Check settings
        // Load fragment.
        // Create receiver.
    }

    @Override
    public void onActivityDestroy() {
        mainActivityView = null;
    }

    @Override
    public void onBackPressed() {
        // Process it.
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
    public void replaceFromLoginToLoggedInFragment(String login) {
        mainActivityView.replaceFromLoginToLoggedInFragment(login);
    }

    @Override
    public void replaceFromLoggedInToLoginFragment() {
        mainActivityView.replaceFromLoggedInToLoginFragment();
    }

    @Override
    public void returnToLoginFragment() {
        mainActivityView.returnToLoginFragment();
    }

    @Override
    public void onSignedIn(String login) {
        mainActivityView.replaceFromLoginToLoggedInFragment(login);
        // Also start Timer.
    }

}
