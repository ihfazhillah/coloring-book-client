package com.ihfazh.warnain

import com.ihfazh.warnain.remote.WarnainService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@ComponentScan("com.ihfazh.warnain")
class WarnainKoinApplication {
   @Single
   fun setOkHttpClient(): OkHttpClient {
       val interceptor =  HttpLoggingInterceptor()
       interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

       val authInterceptor = Interceptor {
           val request = it.request().newBuilder()
               .addHeader("Authorization", "Token 575ef5db603bc0ad2557757e8cce59ea1c3404d8")
               .build()
           it.proceed(request)
       }

       return OkHttpClient
           .Builder()
           .addInterceptor(interceptor)
           .addInterceptor(authInterceptor)
           .build()
   }

    @Single
    fun setRetrofit(client: OkHttpClient) : WarnainService{
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.65.124:9000/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build();

        return retrofit.create(WarnainService::class.java);
    }


}