package ru.coffeeplanter.androidlogin.domain.loggedin;

import ru.coffeeplanter.androidlogin.data.settings.SettingsRepository;
import ru.coffeeplanter.androidlogin.data.settings.SettingsSource;
import ru.coffeeplanter.androidlogin.platform.TimerService;

/**
 * Created by Ilya Solovyov on 21.11.2017.
 * is3k@ya.ru
 */

@SuppressWarnings("SpellCheckingInspection")
public class LoggedInInteractorImpl implements LoggedInInteractor {

    private SettingsSource settingsSource;
    private OnUserForgottenListener listener;

    public LoggedInInteractorImpl(OnUserForgottenListener listener) {
        this.listener = listener;
        settingsSource = new SettingsRepository();
    }

    @Override
    public void forgetUser() {
        settingsSource.clearAuthorizationData();
        TimerService.stop();
        listener.onUserForget();
    }

}
