package ru.coffeeplanter.androidlogin.presentation.fragments.login

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.AppCompatButton
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import ru.coffeeplanter.androidlogin.R

class LoginFragment : Fragment(), LoginView, TextWatcher, View.OnClickListener, View.OnTouchListener, TextView.OnEditorActionListener {

    private var presenter: LoginPresenter? = null

    // LoggedInFragmentCallback for signing in.
    private var loginFragmentCallback: LoginFragmentCallback? = null

    private var signInButton: AppCompatButton? = null
    private var loginEditText: TextInputEditText? = null
    private var passwordEditText: TextInputEditText? = null
    private var loginContainer: TextInputLayout? = null
    private var passwordContainer: TextInputLayout? = null
    private var progressBar: ContentLoadingProgressBar? = null
    private var fadingForeground: View? = null
    private var viewToGetFocus: View? = null // View getting focus on login / password errors.

    /**
     * Obligatory interface for the host activity.
     */
    interface LoginFragmentCallback {
        fun onSignedIn(login: String, password: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loginFragmentCallback = context as LoginFragmentCallback?
        presenter = LoginPresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        signInButton = view.findViewById(R.id.sign_in_button)
        signInButton!!.setOnClickListener(this)

        loginContainer = view.findViewById(R.id.login_container)
        passwordContainer = view.findViewById(R.id.password_container)

        loginEditText = view.findViewById(R.id.login)

        passwordEditText = view.findViewById(R.id.password)
        passwordEditText!!.setOnEditorActionListener(this)

        progressBar = view.findViewById(R.id.progress_bar)
        fadingForeground = view.findViewById(R.id.fading_foreground)
        fadingForeground!!.setOnTouchListener(this)

        return view

    }

    override fun onResume() {
        super.onResume()
        // Assigning TextWatcher listener here to prevent early calls.
        loginEditText!!.addTextChangedListener(this)
        passwordEditText!!.addTextChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        // Preventing TextWatcher calls.
        loginEditText!!.removeTextChangedListener(this)
        passwordEditText!!.removeTextChangedListener(this)
        presenter!!.onFragmentPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.onFragmentDestroy()
    }

    override fun switchToWaitingMode() {
        hideKeyboard()
        fadeStatusBar()
        fadingForeground!!.visibility = View.VISIBLE
        progressBar!!.visibility = View.VISIBLE
        loginEditText!!.isFocusableInTouchMode = false
        loginEditText!!.isEnabled = false
        passwordEditText!!.isFocusableInTouchMode = false
        passwordEditText!!.isEnabled = false
        signInButton!!.isClickable = false
        loginEditText!!.clearFocus()
        passwordEditText!!.clearFocus()
    }

    override fun switchOffWaitingMode() {
        unfadeStatusBar()
        progressBar!!.visibility = View.GONE
        fadingForeground!!.visibility = View.GONE
        loginEditText!!.isFocusableInTouchMode = true
        loginEditText!!.isEnabled = true
        passwordEditText!!.isFocusableInTouchMode = true
        passwordEditText!!.isEnabled = true
        signInButton!!.isClickable = true
    }

    override fun setLoginError(error: String?) {
        loginContainer!!.error = error
    }

    override fun setPasswordError(error: String?) {
        passwordContainer!!.error = error
    }

    override fun clearFieldsData() {
        passwordEditText!!.setText("")
        loginEditText!!.setText("")
    }

    override fun setLoginFocused() {
        viewToGetFocus = loginEditText
    }

    override fun setPasswordFocused() {
        viewToGetFocus = passwordEditText
    }

    override fun setFocusedView() {
        if (viewToGetFocus != null) {
            viewToGetFocus!!.requestFocus()
        }
    }

    override fun navigateToLoggedInFragment(login: String, password: String) {
        if (loginFragmentCallback != null) {
            loginFragmentCallback!!.onSignedIn(login, password)
        }
    }

    override fun showKeyboard() {
        val fragmentActivity = activity
        if (fragmentActivity != null) {
            val view = activity!!.currentFocus
            if (view != null) {
                val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    override fun hideKeyboard() {
        val fragmentActivity = activity
        if (fragmentActivity != null) {
            val view = activity!!.currentFocus
            if (view != null) {
                val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    override fun fadeStatusBar() {
        setStatusBarFade(R.color.colorFadeStatusBar)
    }

    override fun unfadeStatusBar() {
        setStatusBarFade(R.color.colorMainBackgroundTop)
    }

    private fun setStatusBarFade(@ColorRes color: Int) {
        val activity = activity
        if (activity != null) {
            val window = activity.window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(getActivity()!!, color)
            }
        }
    }

    override fun onClick(view: View) {
        // Try to login
        val login = loginEditText!!.text.toString()
        val password = passwordEditText!!.text.toString()
        presenter!!.tryToLogin(login, password)
    }

    // Needed to prevent touch events under fading foreground
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        view.performClick()
        return true
    }

    override fun onEditorAction(textView: TextView, actionId: Int, keyEvent: KeyEvent): Boolean {
        // Try to login
        return if (actionId == resources.getInteger(R.integer.login_action_id) || actionId == EditorInfo.IME_ACTION_DONE) {
            val login = loginEditText!!.text.toString()
            val password = passwordEditText!!.text.toString()
            presenter!!.tryToLogin(login, password)
            true
        } else {
            false
        }
    }

    // Three TextWatcher methods.
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        val login = loginEditText!!.text.toString()
        val password = passwordEditText!!.text.toString()
        presenter!!.validateLoginAndPassword(login, password)
    }

}
