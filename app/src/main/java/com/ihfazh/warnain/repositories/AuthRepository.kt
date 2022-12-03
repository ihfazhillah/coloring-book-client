package com.ihfazh.warnain.repositories

interface AuthRepository {
    suspend fun getToken(): String
    suspend fun removeToken()
    suspend fun getDeviceIdentifier(): String
    suspend fun getServerEndpoint(): String
    suspend fun setServerEndpoint(value: String)
}