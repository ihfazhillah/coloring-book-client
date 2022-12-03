package com.ihfazh.warnain.no_connection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihfazh.warnain.repositories.AuthRepository
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NoConnectionViewModel(
    private val authRepository: AuthRepository
):
ViewModel(){
    fun removeToken(){
        viewModelScope.launch {
            authRepository.removeToken()
        }
    }
}