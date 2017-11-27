package ru.coffeeplanter.androidlogin.platform

/**
 * Created by Ilya Solovyov on 18.09.2017.
 * is3k@ya.ru
 */

object ResourceSupplier {

    fun getString(stringId: Int): String {
        return App.context?.getString(stringId) ?: ""
    }

}
