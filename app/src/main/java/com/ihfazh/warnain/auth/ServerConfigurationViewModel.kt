package com.ihfazh.warnain.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihfazh.warnain.WarnainKoinApplication
import com.ihfazh.warnain.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.context.GlobalContext
import org.koin.ksp.generated.module

@KoinViewModel
class ServerConfigurationViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _endpoint = MutableStateFlow("")
    val endpoint = _endpoint.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    fun updateEndpoint(value: String){
        _endpoint.value = value
//        viewModelScope.launch {
//            authRepository.setServerEndpoint(endpoint.value)
//        }
    }

    fun login(){
        viewModelScope.launch {
            authRepository.setServerEndpoint(endpoint.value)
            authRepository.getToken()
            _success.value = true
        }
    }
}