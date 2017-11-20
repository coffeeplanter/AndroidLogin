package ru.coffeeplanter.androidlogin.domain.login;

import ru.coffeeplanter.androidlogin.domain.BaseInteractor;

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

@SuppressWarnings("SpellCheckingInspection")
public interface LoginInteractor extends BaseInteractor {

    interface OnLoginFinishedListener {
        void onLoginError(String error);
        void onPasswordError(String error);
        void onLoginSuccess();
        void onLoginFail();
    }

    boolean validateLoginAndPassword(String login, String password);

    void tryToLogin(String login, String password);

}
