package ru.coffeeplanter.androidlogin.domain.login

import ru.coffeeplanter.androidlogin.R
import ru.coffeeplanter.androidlogin.data.settings.SettingsRepository
import ru.coffeeplanter.androidlogin.data.settings.SettingsSource
import ru.coffeeplanter.androidlogin.platform.LoginMocker
import ru.coffeeplanter.androidlogin.platform.ResourceSupplier

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

class LoginInteractorImpl(private val listener: LoginInteractor.OnLoginFinishedListener) : LoginInteractor, Runnable {

    private val settingsRepository: SettingsSource

    init {
        this.settingsRepository = SettingsRepository()
    }

    override fun validateLoginAndPassword(login: String, password: String): Boolean {

        var noLoginErrors = true
        var noPasswordErrors = true

        if (password.matches(REGEX_EMPTY_STRING.toRegex())) {
            listener.onPasswordError(ResourceSupplier.getString(R.string.empty_password_error_message_label))
            noPasswordErrors = false
        } else if (!password.matches(REGEX_TWO_CONSECUTIVE_SYMBOLS.toRegex())) {
            listener.onPasswordError(ResourceSupplier.getString(R.string.password_contains_two_consecutive_symbols_error_message_label))
            noPasswordErrors = false
        } else if (!password.matches(REGEX_NOT_ENOUGH_LETTERS.toRegex())) {
            listener.onPasswordError(ResourceSupplier.getString(R.string.password_contains_too_little_letters_error_message_label))
            noPasswordErrors = false
        } else if (!password.matches(REGEX_NOT_ENOUGH_DIGITS.toRegex())) {
            listener.onPasswordError(ResourceSupplier.getString(R.string.password_contains_too_little_digits_error_message_label))
            noPasswordErrors = false
        } else if (!password.matches(REGEX_TOO_SHORT_PASSWORD.toRegex())) {
            listener.onPasswordError(ResourceSupplier.getString(R.string.short_password_error_message_label))
            noPasswordErrors = false
        } else {
            listener.onPasswordError(null)
        }// Virtually this condition is always true if previous two conditions are true,
        // but I left it here to meet app specification.

        if (login.matches(REGEX_EMPTY_STRING.toRegex())) {
            listener.onLoginError(ResourceSupplier.getString(R.string.empty_login_error_message_label))
            noLoginErrors = false
        } else if (!login.matches(REGEX_CONTAINS_DOTS_OR_SPACES.toRegex())) {
            listener.onLoginError(ResourceSupplier.getString(R.string.login_contains_dots_or_spaces_error_message_label))
            noLoginErrors = false
        } else if (!login.matches(REGEX_TOO_SHORT_LOGIN.toRegex())) {
            listener.onLoginError(ResourceSupplier.getString(R.string.short_login_error_message_label))
            noLoginErrors = false
        } else {
            listener.onLoginError(null)
        }

        return noLoginErrors && noPasswordErrors

    }

    override fun tryToLogin(login: String, password: String) {
        if (validateLoginAndPassword(login, password)) {
            LoginMocker.execute(this)
            settingsRepository.saveAuthorizationData(System.nanoTime(), login, password)
        } else {
            listener.onLoginFail()
        }
    }

    // Needed to mock login process.
    override fun run() {
        listener.onLoginSuccess()
    }

    companion object {

        private val REGEX_EMPTY_STRING = "^$"
        private val REGEX_TWO_CONSECUTIVE_SYMBOLS = "^(?!.*(.)\\1+).*$"
        private val REGEX_NOT_ENOUGH_LETTERS = "^.*(?:[A-Za-zА-Яа-яЁё].*){3,}$"
        private val REGEX_NOT_ENOUGH_DIGITS = "^\\D*(?:\\d\\D*){3,}$"
        private val REGEX_TOO_SHORT_PASSWORD = "^.{6,}$"

        private val REGEX_CONTAINS_DOTS_OR_SPACES = "^[^.|\\s]+$"
        private val REGEX_TOO_SHORT_LOGIN = "^.{4,}$"
    }

}
