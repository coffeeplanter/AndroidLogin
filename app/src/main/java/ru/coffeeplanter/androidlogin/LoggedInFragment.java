package ru.coffeeplanter.androidlogin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Fragment class for logged in screen.
 */

public class LoggedInFragment extends Fragment {

    /**
     * Imperative interface for the host activity.
     */
    public interface Callback {
        void onForgetUserPressed(String login);
    }

    private static final String ARG_LOGIN = "login";

    private final String TAG = "LoggedInFragment";

    Callback mCallback; // Callback for forgetting user.

    private TextView mGreetingTextView;
    private Button mExitButton, mForgetUserButton;

    // Creating of new fragment instances with arguments.
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
        mCallback = (Callback) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_logged_in, container, false);

        final String login = getArguments().getString(ARG_LOGIN);

        mGreetingTextView = (TextView) view.findViewById(R.id.private_greeting_container);
        if (login != null) {
            mGreetingTextView.setText(getString(R.string.custom_private_greeting_message, login));
        }

        mExitButton = (Button) view.findViewById(R.id.exit_button);
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mForgetUserButton = (Button) view.findViewById(R.id.forget_user_button);
        mForgetUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onForgetUserPressed(login);
            }
        });

        return view;

    }

}
