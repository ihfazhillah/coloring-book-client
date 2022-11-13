package com.ihfazh.warnain.auth


sealed interface LoginState {
    object MANUAL: LoginState
    object QRCODE: LoginState
}