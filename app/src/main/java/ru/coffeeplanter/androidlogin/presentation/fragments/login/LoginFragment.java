package ru.coffeeplanter.androidlogin.presentation.fragments.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import ru.coffeeplanter.androidlogin.R;

public class LoginFragment extends Fragment implements
        LoginView,
        TextWatcher,
        View.OnClickListener,
        View.OnTouchListener,
        TextView.OnEditorActionListener {

    /**
     * Obligatory interface for the host activity.
     */
    public interface LoginFragmentCallback {
        void onSignedIn(String login, String password);
    }

    private LoginPresenter presenter;

    private LoginFragmentCallback loginFragmentCallback; // LoggedInFragmentCallback for signing in.

    @SuppressWarnings("FieldCanBeLocal")
    private AppCompatButton signInButton;
    private TextInputEditText loginEditText, passwordEditText;
    private TextInputLayout loginContainer, passwordContainer;
    private ContentLoadingProgressBar progressBar;
    private View fadingForeground;
    private View viewToGetFocus; // View getting focus on login / password errors.

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginFragmentCallback = (LoginFragmentCallback) context;
        presenter = new LoginPresenterImpl(this, getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        loginContainer = view.findViewById(R.id.login_container);
        passwordContainer = view.findViewById(R.id.password_container);

        loginEditText = view.findViewById(R.id.login);

        passwordEditText = view.findViewById(R.id.password);
        passwordEditText.setOnEditorActionListener(this);

        progressBar = view.findViewById(R.id.progress_bar);
        fadingForeground = view.findViewById(R.id.fading_foreground);
        fadingForeground.setOnTouchListener(this);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        // Assigning TextWatcher listener here to prevent early calls.
        loginEditText.addTextChangedListener(this);
        passwordEditText.addTextChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Preventing TextWatcher calls.
        loginEditText.removeTextChangedListener(this);
        passwordEditText.removeTextChangedListener(this);
        presenter.onFragmentPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onFragmentDestroy();
    }

    @Override
    public void switchToWaitingMode() {
        fadingForeground.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        loginEditText.setFocusableInTouchMode(false);
        passwordEditText.setFocusableInTouchMode(false);
        loginEditText.clearFocus();
        passwordEditText.clearFocus();
    }

    @Override
    public void switchOffWaitingMode() {
        progressBar.setVisibility(View.GONE);
        fadingForeground.setVisibility(View.GONE);
        loginEditText.setFocusableInTouchMode(true);
        passwordEditText.setFocusableInTouchMode(true);
        passwordEditText.requestFocus();
    }

    @Override
    public void setLoginError(String error) {
        loginContainer.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        passwordContainer.setError(error);
    }

    @Override
    public void clearFieldsData() {
        passwordEditText.setText("");
        loginEditText.setText("");
    }

    @Override
    public void setLoginFocused() {
        viewToGetFocus = loginEditText;
    }

    @Override
    public void setPasswordFocused() {
        viewToGetFocus = passwordEditText;
    }

    @Override
    public void setFocusedView() {
        if (viewToGetFocus != null) {
            viewToGetFocus.requestFocus();
        }
    }

    @Override
    public void navigateToLoggedInFragment(String login, String password) {
        if (loginFragmentCallback != null) {
            loginFragmentCallback.onSignedIn(login, password);
        }
    }

    @Override
    public void showKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {
        // Try to login
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        presenter.tryToLogin(login, password);
    }

    // Needed to prevent touch events under fading foreground
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        // Try to login
        if (actionId == R.id.login_action_id || actionId == EditorInfo.IME_ACTION_DONE) {
            String login = loginEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            return presenter.tryToLogin(login, password);
        } else {
            return false;
        }
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
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        presenter.validateLoginAndPassword(login, password);
    }

}
