package ru.coffeeplanter.androidlogin.domain.login;

import ru.coffeeplanter.androidlogin.R;
import ru.coffeeplanter.androidlogin.data.settings.SettingsRepository;
import ru.coffeeplanter.androidlogin.data.settings.SettingsSource;
import ru.coffeeplanter.androidlogin.platform.LoginMocker;
import ru.coffeeplanter.androidlogin.platform.ResourceSupplier;
import ru.coffeeplanter.androidlogin.presentation.BasePresenter;

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

@SuppressWarnings("SpellCheckingInspection")
public class LoginInteractorImpl implements LoginInteractor, Runnable {

    private static final String REGEX_EMPTY_STRING = "^$";
    private static final String REGEX_TWO_CONSECUTIVE_SYMBOLS = "^(?!.*(.)\\1+).*$";
    private static final String REGEX_NOT_ENOUGH_LETTERS = "^.*(?:[A-Za-zА-Яа-яЁё].*){3,}$";
    private static final String REGEX_NOT_ENOUGH_DIGITS = "^\\D*(?:\\d\\D*){3,}$";
    private static final String REGEX_TOO_SHORT_PASSWORD = "^.{6,}$";

    private static final String REGEX_CONTAINS_DOTS_OR_SPACES = "^[^.|\\s]+$";
    private static final String REGEX_TOO_SHORT_LOGIN = "^.{4,}$";

    private final long TIME_TO_KEEP_LOGIN = 300;

    private SettingsSource settingsRepository;
    private BasePresenter presenter;
    private OnLoginFinishedListener listener;

    public LoginInteractorImpl(BasePresenter presenter, OnLoginFinishedListener listener) {
        this.presenter = presenter;
        this.listener = listener;
        this.settingsRepository = new SettingsRepository();
    }

    public LoginInteractorImpl(BasePresenter presenter) {
        this.presenter = presenter;
        this.settingsRepository = new SettingsRepository();
    }

    @Override
    public boolean validateLoginAndPassword(String login, String password) {

        boolean noLoginErrors = true;
        boolean noPasswordErrors = true;

        if (password.matches(REGEX_EMPTY_STRING)) {
            listener.onPasswordError(ResourceSupplier.getString(R.string.empty_password_error_message_label));
            noPasswordErrors = false;
        } else if (!password.matches(REGEX_TWO_CONSECUTIVE_SYMBOLS)) {
            listener.onPasswordError(ResourceSupplier.getString(R.string.password_contains_two_consecutive_symbols_error_message_label));
            noPasswordErrors = false;
        } else if (!password.matches(REGEX_NOT_ENOUGH_LETTERS)) {
            listener.onPasswordError(ResourceSupplier.getString(R.string.password_contains_too_little_letters_error_message_label));
            noPasswordErrors = false;
        } else if (!password.matches(REGEX_NOT_ENOUGH_DIGITS)) {
            listener.onPasswordError(ResourceSupplier.getString(R.string.password_contains_too_little_digits_error_message_label));
            noPasswordErrors = false;
        }
        // Virtually this condition is always true if previous two conditions are true,
        // but I left it here to meet app specification.
        else if (!password.matches(REGEX_TOO_SHORT_PASSWORD)) {
            listener.onPasswordError(ResourceSupplier.getString(R.string.short_password_error_message_label));
            noPasswordErrors = false;
        } else {
            listener.onPasswordError(null);
        }

        if (login.matches(REGEX_EMPTY_STRING)) {
            listener.onLoginError(ResourceSupplier.getString(R.string.empty_login_error_message_label));
            noLoginErrors = false;
        } else if (!login.matches(REGEX_CONTAINS_DOTS_OR_SPACES)) {
            listener.onLoginError(ResourceSupplier.getString(R.string.login_contains_dots_or_spaces_error_message_label));
            noLoginErrors = false;
        } else if (!login.matches(REGEX_TOO_SHORT_LOGIN)) {
            listener.onLoginError(ResourceSupplier.getString(R.string.short_login_error_message_label));
            noLoginErrors = false;
        } else {
            listener.onLoginError(null);
        }

        return noLoginErrors && noPasswordErrors;

    }

    @Override
    public void tryToLogin(String login, String password) {
        if (validateLoginAndPassword(login, password)) {
            LoginMocker.execute(this);
            settingsRepository.saveAuthorizationData(System.nanoTime(), login, password);
        } else {
            listener.onLoginFail();
        }
    }

    // Needed to mock login process.
    @Override
    public void run() {
        listener.onLoginSuccess();
    }

}
