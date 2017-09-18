package ru.coffeeplanter.androidlogin.presentation.fragments.loggedin;

import ru.coffeeplanter.androidlogin.presentation.BasePresenter;

interface LoggedInPresenter extends BasePresenter {

    void onFragmentCreateView();
    void exitButtonClicked();
    void forgetUserButtonClicked();
    void onFragmentDestroy();

}
