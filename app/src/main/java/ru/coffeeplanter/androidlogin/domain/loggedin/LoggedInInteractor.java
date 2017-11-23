package ru.coffeeplanter.androidlogin.domain.loggedin;

import ru.coffeeplanter.androidlogin.domain.BaseInteractor;

/**
 * Created by Ilya Solovyov on 21.11.2017.
 * is3k@ya.ru
 */

@SuppressWarnings("SpellCheckingInspection")
public interface LoggedInInteractor extends BaseInteractor {

    interface OnUserForgottenListener {
        void onUserForget();
    }

    void forgetUser();

}
