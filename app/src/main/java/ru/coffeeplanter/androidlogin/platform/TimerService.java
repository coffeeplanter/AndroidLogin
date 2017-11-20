package ru.coffeeplanter.androidlogin.platform;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import ru.coffeeplanter.androidlogin.presentation.activities.MainActivity;

public class TimerService extends Service {

    public static final String ACTION_TIME_IS_FINISHED = "ru.coffeeplanter.androidlogin.action.TIME_IS_FINISHED";
    public static final String INTENT_TIME_ELAPSED = "time_elapsed";

    private final String TAG = "TimerService";

    private Chronometer mTimer;

    private long mTimeToKeepLogin; // Time to keep login in seconds.

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Service started");

        // Get time to keep login.
        mTimeToKeepLogin = intent.getLongExtra(MainActivity.INTENT_TIME_SERVICE, 0);

        // Start counting time.
        mTimer = new Chronometer(mTimeToKeepLogin);
        mTimer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    void makeFinishActions() {
        Intent intent = new Intent(ACTION_TIME_IS_FINISHED);
        intent.putExtra(INTENT_TIME_ELAPSED, mTimeToKeepLogin);
        sendBroadcast(intent);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        mTimer.terminate(); // Stop timer thread on service destroy.
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Internal timer class.
    private class Chronometer extends Thread {

        private final int TIMER_FIDELITY = 1000; // Time between time limit checks in milliseconds.

        private long timeUntilStop = 0L; // Time limit.

        private boolean mustStop = false; // Flag to stop thread.

        Chronometer(long time) {
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
