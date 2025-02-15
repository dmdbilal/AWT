package com.dmdbilal.agroweathertip.domain.sign_in

data class SignInState(
    val isSignInSuccessfull: Boolean = false,
    val signInError: String? = null
)
