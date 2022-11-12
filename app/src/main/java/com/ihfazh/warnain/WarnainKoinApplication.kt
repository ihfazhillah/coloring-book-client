package com.ihfazh.warnain

import android.util.Log
import com.ihfazh.warnain.common.PreferenceManager
import com.ihfazh.warnain.remote.WarnainService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URI

@Module
@ComponentScan("com.ihfazh.warnain")
class WarnainKoinApplication {
   @Single
   fun setOkHttpClient(preferenceManager: PreferenceManager): OkHttpClient {
       val interceptor =  HttpLoggingInterceptor()
       interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

       val authInterceptor = Interceptor {
           val request = it.request()
           val token = preferenceManager.getToken()
           if (token != null){
               val nextRequest = request.newBuilder()
                   .addHeader("Authorization", "Token ${preferenceManager.getToken()}")
                   .build()
               it.proceed(nextRequest)
           } else {

               it.proceed(request)
           }
       }

       val dynamicBaseUrl = Interceptor {
           val request = it.request()
           val nextRequest = preferenceManager.getServerEndpoint()?.let { d->
               val url = if (d.startsWith("http")) d else "http://$d"
               URI(url)
           }?.let { uri ->
               val newUrl = request.url.newBuilder().host(uri.host).port(uri.port).build()
               Log.d("DYNAMIC BASE URL", "setOkHttpClient: $newUrl")
               request.newBuilder().url(newUrl).build()
           }

           if (nextRequest != null){
               it.proceed(nextRequest)
           } else {
               it.proceed(request)
           }

       }

       return OkHttpClient
           .Builder()
           .addInterceptor(dynamicBaseUrl)
           .addInterceptor(interceptor)
           .addInterceptor(authInterceptor)
           .build()
   }

    @Factory
    fun setRetrofit(client: OkHttpClient, preferenceManager: PreferenceManager) : WarnainService{
        val retrofit = Retrofit.Builder()
            .baseUrl("http://${preferenceManager.getServerEndpoint()}/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build();

        return retrofit.create(WarnainService::class.java);
    }


}