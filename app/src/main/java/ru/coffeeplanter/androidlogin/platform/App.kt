package ru.coffeeplanter.androidlogin.platform

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            private set
    }

}
