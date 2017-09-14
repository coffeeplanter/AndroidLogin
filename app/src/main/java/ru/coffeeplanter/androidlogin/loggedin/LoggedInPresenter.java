package ru.coffeeplanter.androidlogin.loggedin;

interface LoggedInPresenter {

    void onFragmentCreateView();
    void exitButtonClicked();
    void forgetUserButtonClicked();
    void onFragmentDestroy();

}
