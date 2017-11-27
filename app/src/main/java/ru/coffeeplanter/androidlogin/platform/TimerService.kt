package ru.coffeeplanter.androidlogin.platform

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.coffeeplanter.androidlogin.R
import ru.coffeeplanter.androidlogin.data.settings.SettingsSource

class TimerService : Service() {

    @Suppress("PrivatePropertyName", "unused")
    private val TAG = tag

    private var timer: Timer? = null

    private var timeToKeepLogin: Long = 0 // Time to keep login in seconds.

    private val tag: String
        get() {
            return try {
                javaClass.simpleName.substring(0, 23)
            } catch (sioobe: StringIndexOutOfBoundsException) {
                javaClass.simpleName
            }
        }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        // Get time to keep login.
        timeToKeepLogin = intent.getLongExtra(INTENT_TIME_SERVICE, 0)

        // Start counting time.
        timer = Timer(timeToKeepLogin)
        timer!!.start()

        return super.onStartCommand(intent, flags, startId)
    }

    internal fun makeFinishActions() {
        val intent = Intent(ACTION_TIME_IS_FINISHED)
        intent.putExtra(INTENT_TIME_ELAPSED, timeToKeepLogin)
        sendBroadcast(intent)
        stopSelf()
    }

    override fun onDestroy() {
        timer!!.terminate() // Stop timer thread on service destroy.
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    // Internal timer class.
    private inner class Timer internal constructor(time: Long) : Thread() {

        @Suppress("PrivatePropertyName")
        private val TIMER_FIDELITY = 100 // Time between time limit checks in milliseconds.

        private var timeUntilStop = 0L // Time limit.

        private var mustStop = false // Flag to stop thread.

        init {
            timeUntilStop = time
        }

        // Change flag to stop thread.
        internal fun terminate() {
            mustStop = true
        }

        override fun run() {
            val startTime = System.nanoTime()
            var stopTime = System.nanoTime()
            try {
                while (stopTime - startTime < timeUntilStop * 1e9 && !mustStop) {
                    Thread.sleep(TIMER_FIDELITY.toLong())
                    stopTime = System.nanoTime()
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            if (!mustStop) {
                makeFinishActions()
            }
        }

    }

    companion object {

        val INTENT_TIME_ELAPSED = "time_elapsed"
        val INTENT_TIME_SERVICE = "time"
        val ACTION_TIME_IS_FINISHED = ResourceSupplier.getString(R.string.action_time_is_finished)

        fun start() {
            val context = App.context
            val intent = Intent(context, TimerService::class.java)
            intent.putExtra(INTENT_TIME_SERVICE, SettingsSource.TIME_TO_KEEP_LOGIN)
            context?.startService(intent)
        }

        fun stop() {
            val context = App.context
            val intent = Intent(context, TimerService::class.java)
            context?.stopService(intent)
        }
    }

}
