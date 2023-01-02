package com.example.cariceramah.DI

import com.example.basecomponent.base_component.appUserPreference
import com.example.basecomponent.base_component.utils.LocationService
import com.example.cariceramah.BuildConfig
import com.example.cariceramah.app.CariCeramahApp
import com.example.common_usecase.GetFcmTokenUseCase
import com.example.common_usecase.LocationUseCase
import com.example.common_usecase.RetrieveSaveLocationUseCase
import com.example.data.repo.local.PrefIml
import com.example.data.repo.local.PrefInterface
import com.example.data.repo.remote.KhutbahApiIml
import com.example.data.repo.remote.KhutbahApiInterface
import com.example.data.repo.remote.KhutbahInterface
import com.example.domain.FetchLectureUseCase
import com.example.domain.RegisterFcmUseCase
import com.example.domain.SearchLectureUseCase
import com.example.domain.UnregisterFcmUseCase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class UseCaseBinder(

) {

    @Provides
    @Singleton
    fun getLocationUseCase(
        locationService: LocationService,
        prefInterface: PrefInterface
    ): LocationUseCase {
        return LocationUseCase(locationService, prefInterface)
    }

    @Provides
    @Singleton
    fun retrieveLocationUseCase(
        prefInterface: PrefInterface
    ): RetrieveSaveLocationUseCase {
        return RetrieveSaveLocationUseCase(prefInterface) }

    @Provides
    @Singleton
    fun fetchLectureUseCase(
        khutbahApiInterface: KhutbahApiInterface
    ): FetchLectureUseCase {
        return FetchLectureUseCase(khutbahApiInterface) }

    @Provides
    @Singleton
    fun searchLectureUseCase(
        khutbahApiInterface: KhutbahApiInterface
    ): SearchLectureUseCase {
        return SearchLectureUseCase(khutbahApiInterface) }

    @Provides
    @Singleton
    fun unregisterFcmUseCase(
        khutbahApiInterface: KhutbahApiInterface,
        prefInterface: PrefInterface
    ): UnregisterFcmUseCase {
        return UnregisterFcmUseCase(khutbahApiInterface, prefInterface) }

    @Provides
    @Singleton
    fun setFcmUseCase(
        khutbahApiInterface: KhutbahApiInterface,
        prefInterface: PrefInterface
    ): RegisterFcmUseCase {
        return RegisterFcmUseCase(khutbahApiInterface, prefInterface) }

    @Provides
    @Singleton
    fun getNotificationTokenUseCase(
        prefInterface: PrefInterface
    ): GetFcmTokenUseCase {
        return GetFcmTokenUseCase(prefInterface) }


}