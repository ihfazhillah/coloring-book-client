package com.ihfazh.warnain.auth


sealed interface LoginState {
    object SUCCESS: LoginState
    object ERROR: LoginState
}