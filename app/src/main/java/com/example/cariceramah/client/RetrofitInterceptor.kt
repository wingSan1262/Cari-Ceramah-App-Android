package com.example.cariceramah.client

import com.example.cariceramah.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RetrofitInterceptor() : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val request: Request = original.newBuilder()
            .header("client-api-key", BuildConfig.API_KEY)
            .header("Accept", "application/json")
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }

}