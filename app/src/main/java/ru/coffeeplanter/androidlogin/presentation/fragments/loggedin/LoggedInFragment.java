package ru.coffeeplanter.androidlogin.presentation.fragments.loggedin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.coffeeplanter.androidlogin.R;

public class LoggedInFragment extends Fragment implements
        LoggedInView,
        View.OnClickListener {

    /**
     * Obligatory interface for the host activity.
     */
    public interface LoggedInFragmentCallback {
        void onForgetUserPressed(String login);
    }

    private static final String ARG_LOGIN = "login";

    private LoggedInPresenter presenter;

    private LoggedInFragmentCallback loggedInFragmentCallback; // LoginFragmentCallback for forgetting user.

    @SuppressWarnings("FieldCanBeLocal")
    private AppCompatTextView greetingTextView;
    @SuppressWarnings("FieldCanBeLocal")
    private AppCompatButton exitButton, forgetUserButton;

    private String login;

    public static LoggedInFragment newInstance(String login) {
        Bundle args = new Bundle();
        args.putString(ARG_LOGIN, login);
        LoggedInFragment fragment = new LoggedInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loggedInFragmentCallback = (LoggedInFragmentCallback) context;
        presenter = new LoggedInPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_logged_in, container, false);

        greetingTextView = view.findViewById(R.id.private_greeting_container);

        exitButton = view.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);

        forgetUserButton = view.findViewById(R.id.forget_user_button);
        forgetUserButton.setOnClickListener(this);

        presenter.onFragmentCreateView();

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onFragmentDestroy();
    }

    @Override
    public void getDataFromFragmentArguments() {
        login = getArguments() != null ? getArguments().getString(ARG_LOGIN, "") : "";
    }

    @Override
    public void setGreetingMessage() {
        if (login != null) {
            greetingTextView.setText(getString(R.string.custom_private_greeting_message, login));
        }
    }

    @Override
    public void closeApp() {
        getActivity().finish();
    }

    @Override
    public void navigateToLoginFragment() {
        if (loggedInFragmentCallback != null && login != null) {
            loggedInFragmentCallback.onForgetUserPressed(login);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_button:
                presenter.exitButtonClicked();
                break;
            case R.id.forget_user_button:
                presenter.forgetUserButtonClicked();
                break;
        }
    }

}
