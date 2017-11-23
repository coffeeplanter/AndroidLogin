package ru.coffeeplanter.androidlogin.platform;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import ru.coffeeplanter.androidlogin.R;
import ru.coffeeplanter.androidlogin.data.settings.SettingsSource;

@SuppressWarnings("SpellCheckingInspection")
public class TimerService extends Service {

    public static final String INTENT_TIME_ELAPSED = "time_elapsed";
    public static final String INTENT_TIME_SERVICE = "time";
    public static final String ACTION_TIME_IS_FINISHED = ResourceSupplier.getString(R.string.action_time_is_finished);

    private final String TAG = getTAG();

    private Timer timer;

    private long timeToKeepLogin; // Time to keep login in seconds.

    public static void start() {
        Context context = App.getContext();
        Intent intent = new Intent(context, TimerService.class);
        intent.putExtra(INTENT_TIME_SERVICE, SettingsSource.TIME_TO_KEEP_LOGIN);
        context.startService(intent);
    }

    public static void stop() {
        Context context = App.getContext();
        Intent intent = new Intent(context, TimerService.class);
        context.stopService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Get time to keep login.
        timeToKeepLogin = intent.getLongExtra(INTENT_TIME_SERVICE, 0);

        // Start counting time.
        timer = new Timer(timeToKeepLogin);
        timer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    void makeFinishActions() {
        Intent intent = new Intent(ACTION_TIME_IS_FINISHED);
        intent.putExtra(INTENT_TIME_ELAPSED, timeToKeepLogin);
        sendBroadcast(intent);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        timer.terminate(); // Stop timer thread on service destroy.
        super.onDestroy();
    }

    private String getTAG() {
        String TAG;
        try {
            TAG = getClass().getSimpleName().substring(0, 23);
        } catch (StringIndexOutOfBoundsException sioobe) {
            TAG = getClass().getSimpleName();
        }
        return TAG;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Internal timer class.
    private class Timer extends Thread {

        private final int TIMER_FIDELITY = 100; // Time between time limit checks in milliseconds.

        private long timeUntilStop = 0L; // Time limit.

        private boolean mustStop = false; // Flag to stop thread.

        Timer(long time) {
            timeUntilStop = time;
        }

        // Change flag to stop thread.
        void terminate() {
            mustStop = true;
        }

        @Override
        public void run() {
            long startTime = System.nanoTime();
            long stopTime = System.nanoTime();
            try {
                while (((stopTime - startTime) < timeUntilStop * 1e9) && (!mustStop)) {
                    Thread.sleep(TIMER_FIDELITY);
                    stopTime = System.nanoTime();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!mustStop) {
                makeFinishActions();
            }
        }

    }

}
