package ru.coffeeplanter.androidlogin.presentation.fragments.loggedin

import ru.coffeeplanter.androidlogin.presentation.BasePresenter

internal interface LoggedInPresenter : BasePresenter {

    fun onFragmentCreateView()
    fun exitButtonClicked()
    fun forgetUserButtonClicked()
    fun onFragmentDestroy()

}
