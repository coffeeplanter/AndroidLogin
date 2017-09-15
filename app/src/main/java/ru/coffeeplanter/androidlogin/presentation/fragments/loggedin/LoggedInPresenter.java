package ru.coffeeplanter.androidlogin.presentation.fragments.loggedin;

interface LoggedInPresenter {

    void onFragmentCreateView();
    void exitButtonClicked();
    void forgetUserButtonClicked();
    void onFragmentDestroy();

}
