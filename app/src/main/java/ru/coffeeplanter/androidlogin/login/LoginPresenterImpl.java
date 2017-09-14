package ru.coffeeplanter.androidlogin.login;

import android.content.Context; // Needed to access to app resources.
import android.os.Handler; // Needed to mock login process.

import ru.coffeeplanter.androidlogin.R;

class LoginPresenterImpl implements LoginPresenter, Runnable {

    private static final String REGEX_EMPTY_STRING = "^$";
    private static final String REGEX_TWO_CONSECUTIVE_SYMBOLS = "^(?!.*(.)\\1+).*$";
    private static final String REGEX_NOT_ENOUGH_LETTERS = "^.*(?:[A-Za-zА-Яа-яЁё].*){3,}$";
    private static final String REGEX_NOT_ENOUGH_DIGITS = "^\\D*(?:\\d\\D*){3,}$";
    private static final String REGEX_TOO_SHORT_PASSWORD = "^.{6,}$";

    private static final String REGEX_CONTAINS_DOTS_OR_SPACES = "^[^.|\\s]+$";
    private static final String REGEX_TOO_SHORT_LOGIN = "^.{4,}$";

    private LoginView loginView;
    private Context context; // Needed to access to app resources.

    // Needed to mock login process.
    private String login;
    private String password;

    LoginPresenterImpl(LoginView loginView, Context context) {
        this.loginView = loginView;
        this.context = context;
    }

    @Override
    public boolean validateLoginAndPassword(String login, String password) {

        boolean noLoginErrors = true;
        boolean noPasswordErrors = true;

        if (password.matches(REGEX_EMPTY_STRING)) {
            if (loginView != null) {
                loginView.setPasswordError(context.getString(R.string.empty_password_error_message_label));
                loginView.setPasswordFocused();
            }
            noPasswordErrors = false;
        } else if (!password.matches(REGEX_TWO_CONSECUTIVE_SYMBOLS)) {
            if (loginView != null) {
                loginView.setPasswordError(context.getString(R.string.password_contains_two_consecutive_symbols_error_message_label));
                loginView.setPasswordFocused();
            }
            noPasswordErrors = false;
        } else if (!password.matches(REGEX_NOT_ENOUGH_LETTERS)) {
            if (loginView != null) {
                loginView.setPasswordError(context.getString(R.string.password_contains_too_little_letters_error_message_label));
                loginView.setPasswordFocused();
            }
            noPasswordErrors = false;
        } else if (!password.matches(REGEX_NOT_ENOUGH_DIGITS)) {
            if (loginView != null) {
                loginView.setPasswordError(context.getString(R.string.password_contains_too_little_digits_error_message_label));
                loginView.setPasswordFocused();
            }
            noPasswordErrors = false;
        }
        // Virtually this condition is always true if previous two conditions are true,
        // but I left it here to meet app specification.
        else if (!password.matches(REGEX_TOO_SHORT_PASSWORD)) {
            if (loginView != null) {
                loginView.setPasswordError(context.getString(R.string.short_password_error_message_label));
                loginView.setPasswordFocused();
            }
            noPasswordErrors = false;
        }

        if (login.matches(REGEX_EMPTY_STRING)) {
            if (loginView != null) {
                loginView.setLoginError(context.getString(R.string.empty_login_error_message_label));
                loginView.setLoginFocused();
            }
            noLoginErrors = false;
        } else if (!login.matches(REGEX_CONTAINS_DOTS_OR_SPACES)) {
            if (loginView != null) {
                loginView.setLoginError(context.getString(R.string.login_contains_dots_or_spaces_error_message_label));
                loginView.setLoginFocused();
            }
            noLoginErrors = false;
        } else if (!login.matches(REGEX_TOO_SHORT_LOGIN)) {
            if (loginView != null) {
                loginView.setLoginError(context.getString(R.string.short_login_error_message_label));
                loginView.setLoginFocused();
            }
            noLoginErrors = false;
        }

        if (noLoginErrors && loginView != null) {
            loginView.setLoginError(null);
        }
        if (noPasswordErrors && loginView != null) {
            loginView.setPasswordError(null);
        }

        return noLoginErrors && noPasswordErrors;
    }

    @Override
    public boolean tryToLogin(final String login, final String password) {
        if (loginView != null) {
            this.login = login;
            this.password = password;
            if (validateLoginAndPassword(login, password)) {
                loginView.switchToWaitingMode();
                // Mock login process.
                new Handler().postDelayed(this, 2000);
            } else {
                loginView.setFocusedView();
                loginView.showKeyboard();
            }
        }
        return false;
    }

    @Override
    public void onFragmentPause() {
        // Removing entered data due to safety reasons.
        if (loginView != null) {
            loginView.clearFieldsData();
        }
    }

    @Override
    public void onFragmentDestroy() {
        loginView = null;
        context = null;
    }

    // Needed to mock login process.
    @Override
    public void run() {
        loginView.switchOffWaitingMode();
        loginView.hideKeyboard();
        loginView.navigateToLoggedInFragment(login, password);
    }

}
