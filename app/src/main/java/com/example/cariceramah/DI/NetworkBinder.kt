package com.example.cariceramah.DI

import android.app.Application
import com.example.basecomponent.base_component.appUserPreference
import com.example.basecomponent.base_component.utils.LocationService
import com.example.cariceramah.BuildConfig
import com.example.cariceramah.app.CariCeramahApp
import com.example.cariceramah.client.RetrofitInterceptor
import com.example.data.repo.local.PrefIml
import com.example.data.repo.local.PrefInterface
import com.example.data.repo.remote.KhutbahApiIml
import com.example.data.repo.remote.KhutbahApiInterface
import com.example.data.repo.remote.KhutbahInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkBinder(

) {

    @Provides
    @Singleton
    fun getClientLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun getOkHttpClientBuilder(
        logger : HttpLoggingInterceptor,
        headerInterceptor: RetrofitInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            addNetworkInterceptor(headerInterceptor)
//            addInterceptor(logger)
        }
    }

    @Provides
    @Singleton
    fun getHeaderInterceptor(): RetrofitInterceptor {
        return RetrofitInterceptor()
    }

    @Provides
    @Singleton
    fun getRetrofitClient(
        okHttpBuilder: OkHttpClient.Builder
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    @Singleton
    fun getKhutbahRetrofitApi(
        retrofit: Retrofit
    ): KhutbahInterface {
        return retrofit
            .newBuilder()
            .baseUrl(BuildConfig.SERVER_URL)
            .build()
            .create(KhutbahInterface::class.java)
    }

    @Provides
    @Singleton
    fun getKhutbahApi(
        retrofitInterface : KhutbahInterface
    ): KhutbahApiInterface {
        return KhutbahApiIml(retrofitInterface)
    }

    @Provides
    @Singleton
    fun getPrefApi(application: Application): PrefInterface {
        return PrefIml(application.applicationContext.appUserPreference)
    }

    @Provides
    @Singleton
    fun getLocationService(application: Application): LocationService {
        return LocationService(application)
    }
}