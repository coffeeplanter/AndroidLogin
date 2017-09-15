package ru.coffeeplanter.androidlogin.presentation.fragments.loggedin;

interface LoggedInView {

    void getDataFromArguments();
    void setGreetingMessage();
    void closeApp();
    void navigateToLoginFragment();

}
