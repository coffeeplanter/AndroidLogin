package ru.coffeeplanter.androidlogin.domain.loggedin

import ru.coffeeplanter.androidlogin.data.settings.SettingsRepository
import ru.coffeeplanter.androidlogin.data.settings.SettingsSource
import ru.coffeeplanter.androidlogin.platform.TimerService

/**
 * Created by Ilya Solovyov on 21.11.2017.
 * is3k@ya.ru
 */

class LoggedInInteractorImpl(private val listener: LoggedInInteractor.OnUserForgottenListener) : LoggedInInteractor {

    private val settingsSource: SettingsSource

    init {
        settingsSource = SettingsRepository()
    }

    override fun forgetUser() {
        settingsSource.clearAuthorizationData()
        TimerService.stop()
        listener.onUserForget()
    }

}
