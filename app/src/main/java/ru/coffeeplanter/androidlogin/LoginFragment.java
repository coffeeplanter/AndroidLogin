package ru.coffeeplanter.androidlogin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Fragment class for login screen.
 */

public class LoginFragment extends Fragment implements TextWatcher {

    /**
     * Imperative interface for the host activity.
     */
    public interface Callback {
        void onSignIn(String login, String password);
    }

    private final String TAG = "LoginFragment";

    Callback mCallback; // Callback for signing in.

    private Button mSignInButton;
    private EditText mLoginEditText, mPasswordEditText;
    private TextInputLayout mLoginContainer, mPasswordContainer;
    private View viewToGetFocus; // View getting focus on login / password errors.

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mSignInButton = (Button) view.findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToLogin();
            }
        });

        mLoginContainer = (TextInputLayout) view.findViewById(R.id.login_container);
        mPasswordContainer = (TextInputLayout) view.findViewById(R.id.password_container);

        mLoginEditText = (EditText) view.findViewById(R.id.login);

        mPasswordEditText = (EditText) view.findViewById(R.id.password);
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.login_action_id || actionId == EditorInfo.IME_ACTION_DONE) {
                    tryToLogin();
                    return true;
                }
                return false;
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        // Assigning TextWatcher listener here to prevent early calls.
        mLoginEditText.addTextChangedListener(this);
        mPasswordEditText.addTextChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Preventing TextWatcher calls.
        mLoginEditText.removeTextChangedListener(this);
        mPasswordEditText.removeTextChangedListener(this);
        // Removing entered data due to safety reasons.
        mPasswordEditText.setText("");
        mLoginEditText.setText("");
    }

    // Three TextWatcher methods.
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        validateLoginAndPassword();
    }

    // Attempt to login.
    private void tryToLogin() {
        if (validateLoginAndPassword()) {
            String login = mLoginEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();
            switchKeyboard(false);
            mCallback.onSignIn(login, password);
        } else {
            viewToGetFocus.requestFocus();
        }
    }

    // Login and password validation
    private boolean validateLoginAndPassword() {

        boolean noLoginErrors = true;
        boolean noPasswordErrors = true;

        String login = mLoginEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(password)) {
            mPasswordContainer.setError(getString(R.string.empty_password_error_message_label));
            viewToGetFocus = mPasswordEditText;
            noPasswordErrors = false;
        } else if (!password.matches("^(?!.*(.)\\1+).*$")) {
            mPasswordContainer.setError(getString(R.string.password_contains_two_consecutive_symbols_error_message_label));
            viewToGetFocus = mPasswordEditText;
            noPasswordErrors = false;
        } else if (!password.matches("^.*(?:[A-Za-zА-Яа-яЁё].*){3,}$")) {
            mPasswordContainer.setError(getString(R.string.password_contains_too_little_letters_error_message_label));
            viewToGetFocus = mPasswordEditText;
            noPasswordErrors = false;
        } else if (!password.matches("^\\D*(?:\\d\\D*){3,}$")) {
            mPasswordContainer.setError(getString(R.string.password_contains_too_little_digits_error_message_label));
            viewToGetFocus = mPasswordEditText;
            noPasswordErrors = false;
        }
        // Virtually this condition is always true if previous two conditions are true,
        // but I left it here to meet app specification.
        else if (!password.matches("^.{6,}$")) {
            mPasswordContainer.setError(getString(R.string.short_password_error_message_label));
            viewToGetFocus = mPasswordEditText;
            noPasswordErrors = false;
        }

        if (TextUtils.isEmpty(login)) {
            mLoginContainer.setError(getString(R.string.empty_login_error_message_label));
            viewToGetFocus = mLoginEditText;
            noLoginErrors = false;
        } else if (!login.matches("^[^.|\\s]+$")) {
            mLoginContainer.setError(getString(R.string.login_contains_dots_or_spaces_error_message_label));
            viewToGetFocus = mLoginEditText;
            noLoginErrors = false;
        } else if (!login.matches("^.{4,}$")) {
            mLoginContainer.setError(getString(R.string.short_login_error_message_label));
            viewToGetFocus = mLoginEditText;
            noLoginErrors = false;
        }

        if (noLoginErrors) {
            mLoginContainer.setError(null);
        }
        if (noPasswordErrors) {
            mPasswordContainer.setError(null);
        }

        return noLoginErrors && noPasswordErrors;

    }

    // Show / hide keyboard.
    private void switchKeyboard(boolean show) {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (show) {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            } else {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

}
