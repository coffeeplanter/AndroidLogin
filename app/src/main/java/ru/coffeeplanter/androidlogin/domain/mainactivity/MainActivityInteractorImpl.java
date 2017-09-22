package ru.coffeeplanter.androidlogin.domain.mainactivity;

import ru.coffeeplanter.androidlogin.data.settings.SettingsRepository;
import ru.coffeeplanter.androidlogin.data.settings.SettingsSource;
import ru.coffeeplanter.androidlogin.presentation.activities.MainActivityPresenter;

/**
 * Created by Ilya Solovyov on 21.09.2017.
 * is3k@ya.ru
 */

public class MainActivityInteractorImpl implements MainActivityInteractor {

    private final long TIME_TO_KEEP_LOGIN = 10;

    private SettingsSource settingsRepository;
    private MainActivityPresenter presenter;

    public MainActivityInteractorImpl(MainActivityPresenter presenter) {
        this.presenter = presenter;
        this.settingsRepository = new SettingsRepository();
    }

    @Override
    public void chooseFragmentToLoadAtStart() {

        String login = settingsRepository.getLogin();
        String password = settingsRepository.getPassword();
        long timeStamp = settingsRepository.getTimeStamp();

        if ((login != null) && (password != null)) {
            if (System.nanoTime() - timeStamp > TIME_TO_KEEP_LOGIN * 1e9) {
                settingsRepository.clearAuthorizationData();
                presenter.addLoginFragment();
            } else {
                presenter.addLoggedInFragment(login);
            }
        } else {
            presenter.addLoginFragment();
        }
    }

    @Override
    public void clearAuthorizationData() {
        settingsRepository.clearAuthorizationData();
    }

}
