package ru.coffeeplanter.androidlogin.presentation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ru.coffeeplanter.androidlogin.R;
import ru.coffeeplanter.androidlogin.platform.TimerService;
import ru.coffeeplanter.androidlogin.presentation.fragments.loggedin.LoggedInFragment;
import ru.coffeeplanter.androidlogin.presentation.fragments.login.LoginFragment;

/**
 * Приложение состоит из одной активити, двух фрагментов
 * (для экрана авторизации и для экрана закрытой области) и сервиса таймера.
 * <p>
 * Активити хостит фрагменты, содержит реализацию колбэков,
 * управляет сохранением, чтением и удалением логина и пароля (включая шифрование и расшифрование),
 * управляет запуском и остановкой сервиса, получает сообщение от сервиса и обрабатывает нажатие кнопки "назад".
 * <p>
 * Фрагменты содержат пользовательский интерфейс.
 * <p>
 * Сервис — этой простой таймер, который по истечении заданного времени посылает широковещательное сообщение.
 * <p>
 * Класс Crypter выполняет функции шифрования и расшифрования.
 */

@SuppressWarnings("SpellCheckingInspection")
public class MainActivity extends AppCompatActivity implements
        MainActivityView,
        LoginFragment.LoginFragmentCallback,
        LoggedInFragment.LoggedInFragmentCallback {

    private static final String TAG = MainActivity.class.getName().substring(0, 23);

    // Fragments tags.
    private final String LOGIN_FRAGMENT_TAG = "LoginFragment";
    private final String LOGGED_IN_FRAGMENT_TAG = "LoggedInFragment";

    // Activity visibility flag.
    private boolean isInFront = false;

    // Receiver to catch timeout messages from the TimerService.
    private BroadcastReceiver timeOutReceiver;

    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenterImpl(this);
        presenter.onActivityCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInFront = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInFront = false;
    }

    @Override
    protected void onDestroy() {
        presenter.onActivityDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    // LoginFragment callback.
    @Override
    public void onSignedIn(String login, String password) {
        presenter.onSignedIn(login);
    }

    // LoggedInFragment callback.
    @Override
    public void onForgetUserPressed() {
        presenter.onForgetUserPressed();
    }

    public boolean isActive() {
        return isInFront;
    }

    @Override
    public void addLoginFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment(), LOGIN_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void addLoggedInFragment(String login) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_container, LoggedInFragment.newInstance(login), LOGGED_IN_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void replaceFromLoginToLoggedInFragment(String login) {
        FragmentManager fm = getSupportFragmentManager();
        LoggedInFragment fragment = LoggedInFragment.newInstance(login);
        fm.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                .replace(R.id.fragment_container, fragment, LOGGED_IN_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void replaceFromLoggedInToLoginFragment() {
        FragmentManager fm = getSupportFragmentManager();
        LoginFragment fragment = new LoginFragment();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                .replace(R.id.fragment_container, fragment, LOGIN_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void chooseFragmentOnBroadcastReceived() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentByTag(LOGGED_IN_FRAGMENT_TAG);
        if ((currentFragment != null) && isActive()) {
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                        .replace(R.id.fragment_container, new LoginFragment(), LOGIN_FRAGMENT_TAG)
                        .commit();
            }
        }
    }

    @Override
    public void processBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(LOGGED_IN_FRAGMENT_TAG);
        if (currentFragment == null) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    @Override
    public void registerTimeOutReceiver() {
        timeOutReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                presenter.onTimeOutBroadcastReceived();
            }
        };
        IntentFilter filter = new IntentFilter(TimerService.ACTION_TIME_IS_FINISHED);
        registerReceiver(timeOutReceiver, filter);
    }

    @Override
    public void unregisterTimeOutReceiver() {
        if (timeOutReceiver != null) {
            unregisterReceiver(timeOutReceiver);
        }
    }

}
