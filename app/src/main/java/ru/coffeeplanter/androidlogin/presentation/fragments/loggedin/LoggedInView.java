package ru.coffeeplanter.androidlogin.presentation.fragments.loggedin;

import ru.coffeeplanter.androidlogin.presentation.BaseView;

interface LoggedInView extends BaseView {

    void getDataFromFragmentArguments();
    void setGreetingMessage();
    void closeApp();
    void navigateToLoginFragment();

}
