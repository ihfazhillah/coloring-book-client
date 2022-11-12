package com.ihfazh.warnain.repositories

import com.ihfazh.warnain.common.PreferenceManager
import com.ihfazh.warnain.remote.WarnainService
import com.ihfazh.warnain.remote.data.GetTokenBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class DefaultAuthRepository(
    private val preferenceManager: PreferenceManager,
    private val remote: WarnainService
) : AuthRepository {
    override suspend fun getToken(): String {
        return withContext(Dispatchers.IO){
            val guid = getDeviceIdentifier()
            val resp = remote.getToken(GetTokenBody(guid))
            preferenceManager.setToken(resp.token)
            resp.token
        }
    }

    override suspend fun getDeviceIdentifier(): String {
        return preferenceManager.getGUID()
    }

    override suspend fun getServerEndpoint(): String {
        return preferenceManager.getServerEndpoint()!!
    }

    override suspend fun setServerEndpoint(value: String) {
        preferenceManager.setServerEndpoint(value)
    }

}