package ru.coffeeplanter.androidlogin.presentation.fragments.loggedin

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.coffeeplanter.androidlogin.R

class LoggedInFragment : Fragment(), LoggedInView, View.OnClickListener {

    private var presenter: LoggedInPresenter? = null

    // LoginFragmentCallback for forgetting user.
    private var loggedInFragmentCallback: LoggedInFragmentCallback? = null

    private var greetingTextView: AppCompatTextView? = null
    private var exitButton: AppCompatButton? = null
    private var forgetUserButton: AppCompatButton? = null

    private var login: String? = null

    /**
     * Obligatory interface for the host activity.
     */
    interface LoggedInFragmentCallback {
        fun onForgetUserPressed()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loggedInFragmentCallback = context as LoggedInFragmentCallback?
        presenter = LoggedInPresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_logged_in, container, false)

        greetingTextView = view.findViewById(R.id.private_greeting_container)

        exitButton = view.findViewById(R.id.exit_button)
        exitButton!!.setOnClickListener(this)

        forgetUserButton = view.findViewById(R.id.forget_user_button)
        forgetUserButton!!.setOnClickListener(this)

        presenter!!.onFragmentCreateView()

        return view

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.onFragmentDestroy()
    }

    override fun getDataFromFragmentArguments() {
        login = if (arguments != null) arguments!!.getString(ARG_LOGIN, "") else ""
    }

    override fun setGreetingMessage() {
        if (login != null) {
            greetingTextView!!.text = getString(R.string.custom_private_greeting_message, login)
        }
    }

    override fun closeApp() {
        val activity = activity
        if (activity != null) {
            getActivity()!!.finish()
        }
    }

    override fun navigateToLoginFragment() {
        if (loggedInFragmentCallback != null && login != null) {
            loggedInFragmentCallback!!.onForgetUserPressed()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.exit_button -> presenter!!.exitButtonClicked()
            R.id.forget_user_button -> presenter!!.forgetUserButtonClicked()
        }
    }

    companion object {
        private val ARG_LOGIN = "login"
        fun newInstance(login: String): LoggedInFragment {
            val args = Bundle()
            args.putString(ARG_LOGIN, login)
            val fragment = LoggedInFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
