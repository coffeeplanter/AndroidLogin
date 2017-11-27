package ru.coffeeplanter.androidlogin.presentation.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.coffeeplanter.androidlogin.R
import ru.coffeeplanter.androidlogin.platform.TimerService
import ru.coffeeplanter.androidlogin.presentation.fragments.loggedin.LoggedInFragment
import ru.coffeeplanter.androidlogin.presentation.fragments.login.LoginFragment

@Suppress("PrivatePropertyName")
/**
 * Приложение состоит из одной активити, двух фрагментов
 * (для экрана авторизации и для экрана закрытой области) и сервиса таймера.
 *
 *
 * Активити хостит фрагменты, содержит реализацию колбэков,
 * управляет сохранением, чтением и удалением логина и пароля (включая шифрование и расшифрование),
 * управляет запуском и остановкой сервиса, получает сообщение от сервиса и обрабатывает нажатие кнопки "назад".
 *
 *
 * Фрагменты содержат пользовательский интерфейс.
 *
 *
 * Сервис — этой простой таймер, который по истечении заданного времени посылает широковещательное сообщение.
 *
 *
 * Класс Crypter выполняет функции шифрования и расшифрования.
 */

class MainActivity : AppCompatActivity(), MainActivityView, LoginFragment.LoginFragmentCallback, LoggedInFragment.LoggedInFragmentCallback {

    // Fragments tags.
    private val LOGIN_FRAGMENT_TAG = "LoginFragment"
    private val LOGGED_IN_FRAGMENT_TAG = "LoggedInFragment"

    // Activity visibility flag.
    @Suppress("MemberVisibilityCanPrivate")
    var isActive = false
        private set

    // Receiver to catch timeout messages from the TimerService.
    private var timeOutReceiver: BroadcastReceiver? = null

    internal var presenter: MainActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainActivityPresenterImpl(this)
        presenter?.onActivityCreate()
    }

    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    override fun onDestroy() {
        presenter?.onActivityDestroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        presenter?.onBackPressed()
    }

    // LoginFragment callback.
    override fun onSignedIn(login: String, password: String) {
        presenter?.onSignedIn(login)
    }

    // LoggedInFragment callback.
    override fun onForgetUserPressed() {
        presenter?.onForgetUserPressed()
    }

    override fun addLoginFragment() {
        val fm = supportFragmentManager
        val fragment = fm.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_container, LoginFragment(), LOGIN_FRAGMENT_TAG)
                    .commit()
        }
    }

    override fun addLoggedInFragment(login: String) {
        val fm = supportFragmentManager
        val fragment = fm.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_container, LoggedInFragment.newInstance(login), LOGGED_IN_FRAGMENT_TAG)
                    .commit()
        }
    }

    override fun replaceFromLoginToLoggedInFragment(login: String) {
        val fm = supportFragmentManager
        val fragment = LoggedInFragment.newInstance(login)
        fm.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                .replace(R.id.fragment_container, fragment, LOGGED_IN_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit()
    }

    override fun replaceFromLoggedInToLoginFragment() {
        val fm = supportFragmentManager
        val fragment = LoginFragment()
        fm.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                .replace(R.id.fragment_container, fragment, LOGIN_FRAGMENT_TAG)
                .commit()
    }

    override fun chooseFragmentOnBroadcastReceived() {
        val fm = supportFragmentManager
        val currentFragment = fm.findFragmentByTag(LOGGED_IN_FRAGMENT_TAG)
        if (currentFragment != null && isActive) {
            if (fm.backStackEntryCount > 0) {
                fm.popBackStack()
            } else {
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                        .replace(R.id.fragment_container, LoginFragment(), LOGIN_FRAGMENT_TAG)
                        .commit()
            }
        }
    }

    override fun processBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentByTag(LOGGED_IN_FRAGMENT_TAG)
        if (currentFragment == null) {
            super.onBackPressed()
        } else {
            finish()
        }
    }

    override fun registerTimeOutReceiver() {
        timeOutReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                presenter?.onTimeOutBroadcastReceived()
            }
        }
        val filter = IntentFilter(TimerService.ACTION_TIME_IS_FINISHED)
        registerReceiver(timeOutReceiver, filter)
    }

    override fun unregisterTimeOutReceiver() {
        if (timeOutReceiver != null) {
            unregisterReceiver(timeOutReceiver)
        }
    }

    @Suppress("unused")
    companion object {
        private val TAG = MainActivity::class.java.name.substring(0, 23)
    }

}
