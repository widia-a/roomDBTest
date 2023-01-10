package com.bcaf.roomdbtest.service

import com.bcaf.roomdbtest.PostDataDummy
import com.bcaf.roomdbtest.model.PostDummyData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class NetworkConfig {
    fun getInterceptor() : OkHttpClient {
        var logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient  = OkHttpClient.Builder().addInterceptor(logging)
            .build()
        return  okHttpClient

    }

    fun getInterceptorWithHeader() : OkHttpClient {
        var logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient  = OkHttpClient.Builder().addInterceptor(logging)
            .addInterceptor{chain ->
                val request = chain.request().newBuilder()
                    .addHeader("app-id","63bcc99843b161608eeac665")
                chain.proceed(request.build())
            }
            .build()
        return  okHttpClient

    }

    fun getRetrofitPostDummy() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dummyapi.io/data/v1/")
            .client(getInterceptorWithHeader())
            .addConverterFactory(GsonConverterFactory.create())
        .build()
        }

    fun getServiceDummy() = getRetrofitPostDummy().create(DummyApiInterface::class.java)

}