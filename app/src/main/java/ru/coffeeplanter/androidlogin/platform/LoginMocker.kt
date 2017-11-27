package ru.coffeeplanter.androidlogin.platform

import android.os.Handler

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

object LoginMocker {

    private val DEFAULT_LOGIN_MOCK_TIME = 2000 // Delay in milliseconds.

    fun execute(runnable: Runnable) {
        execute(runnable, DEFAULT_LOGIN_MOCK_TIME)
    }

    @Suppress("MemberVisibilityCanPrivate")
    fun execute(runnable: Runnable, delay: Int) {
        Handler().postDelayed(runnable, delay.toLong())
    }

}
