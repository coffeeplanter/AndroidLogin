package ru.coffeeplanter.androidlogin.domain.mainactivity

import ru.coffeeplanter.androidlogin.data.settings.SettingsRepository
import ru.coffeeplanter.androidlogin.data.settings.SettingsSource
import ru.coffeeplanter.androidlogin.presentation.activities.MainActivityPresenter

/**
 * Created by Ilya Solovyov on 21.09.2017.
 * is3k@ya.ru
 */

class MainActivityInteractorImpl(private val presenter: MainActivityPresenter) : MainActivityInteractor {

    private val settingsRepository: SettingsSource

    init {
        this.settingsRepository = SettingsRepository()
    }

    override fun chooseFragmentToLoadAtStart() {

        val login = settingsRepository.login
        val timeStamp = settingsRepository.timeStamp

        if (System.nanoTime() - timeStamp > SettingsSource.TIME_TO_KEEP_LOGIN * 1e9) {
            settingsRepository.clearAuthorizationData()
            presenter.addLoginFragment()
        } else {
            presenter.addLoggedInFragment(login)
        }
    }

    override fun clearAuthorizationData() {
        settingsRepository.clearAuthorizationData()
    }

}
