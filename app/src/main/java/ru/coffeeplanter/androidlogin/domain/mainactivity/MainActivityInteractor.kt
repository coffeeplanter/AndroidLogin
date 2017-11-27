package ru.coffeeplanter.androidlogin.domain.mainactivity

import ru.coffeeplanter.androidlogin.domain.BaseInteractor

/**
 * Created by Ilya Solovyov on 21.09.2017.
 * is3k@ya.ru
 */

interface MainActivityInteractor : BaseInteractor {

    fun chooseFragmentToLoadAtStart()

    fun clearAuthorizationData()

}
