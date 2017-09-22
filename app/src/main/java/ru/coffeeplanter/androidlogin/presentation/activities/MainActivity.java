package ru.coffeeplanter.androidlogin.presentation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements
        MainActivityView,
        LoginFragment.LoginFragmentCallback,
        LoggedInFragment.LoggedInFragmentCallback {

    public static final String INTENT_TIME_SERVICE = "time"; // Key string for service intent.

//    private static final String TAG = MainActivity.class.getName().substring(0, 23);

    // Fragments tags.
    private final String LOGIN_FRAGMENT_TAG = "LoginFragment";
    private final String LOGGED_IN_FRAGMENT_TAG = "LoggedInFragment";

    // Time of login and password keeping in seconds.
    private final long TIME_TO_KEEP_LOGIN = 300;

    // Constants for settings file.
    private final String APP_PREFS = "login_settings";
    private final String APP_PREFS_LOGIN = "login";
    private final String APP_PREFS_PASSWORD = "password";
    private final String APP_TIMESTAMP = "timestamp";

    // Activity visibility flag.
    private boolean isInFront = false;

    // AES cryption key.
//    private byte[] cryptKey;

//    Crypter mCrypter;

//    SharedPreferences mSettings; // App settings.

    private BroadcastReceiver timeOutReceiver; // Receiver to catch timeout messages from the TimerService.

    MainActivityPresenter presenter;

    // Loading of certain fragment depending on presence of saved login-password pair in settings.
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenterImpl(this);
        presenter.onActivityCreate();

        // Move to presenter.

        // Create and register Broadcast Receiver.
//        createReceiver();
//        IntentFilter filter = new IntentFilter(TimerService.ACTION_TIME_IS_FINISHED);
//        registerReceiver(timeOutReceiver, filter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set activity visibility flag
        isInFront = true;
        // Check for LoggedInFragment is loaded.
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentByTag(LOGGED_IN_FRAGMENT_TAG);
//        if ((currentFragment != null) && (!mSettings.contains(APP_PREFS_LOGIN))) {
//            if (fm.getBackStackEntryCount() > 0) {
//                fm.popBackStack();
//            }
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Set activity visibility flag
        isInFront = false;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(timeOutReceiver); // Unregister BroadcastReceiver on activity destroy.
        presenter.onActivityDestroy();
        super.onDestroy();
    }

    // Switch off Back press while LoggedInFragment is loaded.
    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    // LoginFragment callback.
    @Override
    public void onSignedIn(String login, String password) {
        // Save login in settings.
//        saveDataToSharedPreferences(login, password);
        // Load logged in fragment.
        presenter.onSignedIn(login);


        // Move to presenter

//        changeToLoggedInFragment(login);
//        // Start TimerService.
//        Intent intent = new Intent(this, TimerService.class);
//        intent.putExtra(INTENT_TIME_SERVICE, TIME_TO_KEEP_LOGIN);
//        startService(intent);
    }

    // LoggedInFragment callback.
    @Override
    public void onForgetUserPressed(String login) {
        // Remove login from saved settings.
//        clearSharedPreferences();
        // Load login fragment.
//        FragmentManager fm = getSupportFragmentManager();
//        checkBackStackAndChangeToLoginFragment(fm);
        // Stop TimerService.
        Intent intent = new Intent(this, TimerService.class);
        stopService(intent);
    }

    // Load Appropriate fragment on app start.
//    private void loadAppropriateFragment(String login, String password, long timestamp) {
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//        if (fragment == null) {
//            if ((login != null) && (password != null)) {
//                if (System.nanoTime() - timestamp > TIME_TO_KEEP_LOGIN * 1e9) {
//                    // Remove login from saved settings.
////                    clearSharedPreferences();
//                    // Load LoginFragment.
//                    loadLoginFragment(fm);
//                } else {
//                    // Load LoggedInFragment.
//                    loadLoggedInFragment(fm, login);
//                }
//            } else {
//                // Load LoginFragment.
//                loadLoginFragment(fm);
//            }
//        }
//    }

    // Load LoginFragment on app start.
//    private void loadLoginFragment(FragmentManager fm) {
//        fm.beginTransaction()
//                .add(R.id.fragment_container, new LoginFragment(), LOGIN_FRAGMENT_TAG)
//                .commit();
//    }

    // Load LoggedInFragment on app start.
//    private void loadLoggedInFragment(FragmentManager fm, String login) {
//        Fragment fragment = LoggedInFragment.newInstance(login);
//        fm.beginTransaction()
//                .add(R.id.fragment_container, fragment, LOGGED_IN_FRAGMENT_TAG)
//                .commit();
//    }

    // Saving to SharedPreferences.
//    private void saveDataToSharedPreferences(String login, String password) {
//        SharedPreferences.Editor editor = mSettings.edit();
//        editor.putString(APP_PREFS_LOGIN, mCrypter.encrypt(login));
//        editor.putString(APP_PREFS_PASSWORD, mCrypter.encrypt(password));
//        editor.putLong(APP_TIMESTAMP, System.nanoTime());
//        editor.apply();
//    }

    // Clearing SharedPreferences.
//    private void clearSharedPreferences() {
//        SharedPreferences.Editor editor = mSettings.edit();
//        editor.remove(APP_PREFS_LOGIN);
//        editor.remove(APP_PREFS_PASSWORD);
//        editor.remove(APP_TIMESTAMP);
//        editor.apply();
//    }

    // Broadcast receiver creation.
//    private void createReceiver() {
//        timeOutReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.d(TAG, "System.nanoTime() from MainActivity: " + System.nanoTime());
//                long timeElapsed = intent.getLongExtra(TimerService.INTENT_TIME_ELAPSED, TIME_TO_KEEP_LOGIN);
//                // Remove login from saved settings.
////                clearSharedPreferences();
//                // Load login fragment if it's not already loaded.
//                FragmentManager fm = getSupportFragmentManager();
//                Fragment currentFragment = fm.findFragmentByTag(LOGGED_IN_FRAGMENT_TAG);
//                if ((currentFragment != null) && isInFront) {
//                    checkBackStackAndChangeToLoginFragment(fm);
//                    Toast.makeText(MainActivity.this, R.string.time_is_finished_toast, Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
//    }

    // Animated change from LoggedInFragment to LoginFragment.
//    private void changeToLoginFragment() {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
//        ft.replace(R.id.fragment_container, new LoginFragment(), LOGIN_FRAGMENT_TAG);
//        ft.commit();
//    }

    // Animated change from LoginFragment to LoggedInFragment.
//    private void changeToLoggedInFragment(String login) {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        // Set animation.
//        ft.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,
//                R.anim.slide_in_from_left, R.anim.slide_out_to_right);
//        // Create instance of being loaded fragment.
//        LoggedInFragment fragment = LoggedInFragment.newInstance(login);
//        // Load fragment.
//        ft.replace(R.id.fragment_container, fragment, LOGGED_IN_FRAGMENT_TAG);
//        ft.addToBackStack(null);
//        ft.commit();
//    }

    // Check BackStack and choose right way to load LoginFragment.
//    private void checkBackStackAndChangeToLoginFragment(FragmentManager fm) {
//        if (fm.getBackStackEntryCount() > 0) {
//            fm.popBackStack();
//        } else {
//            changeToLoginFragment();
//        }
//    }

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
    public void returnToLoginFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }

    @Override
    public void createTimeOutReceiver() {
        timeOutReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                presenter.onBroadcastReceived();
            }
        };
    }

    @Override
    public void chooseFragmentOnBroadcastReceived() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentByTag(LOGGED_IN_FRAGMENT_TAG);
        if ((currentFragment != null) && isActive()) {
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            }
            Toast.makeText(MainActivity.this, R.string.time_is_finished_toast, Toast.LENGTH_SHORT).show();
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

}
