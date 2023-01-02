package com.example.cariceramah.DI

import android.app.Application
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.ExperimentalTextApi
import com.example.basecomponent.base_component.appUserPreference
import com.example.basecomponent.base_component.utils.LocationService
import com.example.cariceramah.BuildConfig
import com.example.cariceramah.app.CariCeramahApp
import com.example.data.repo.local.PrefIml
import com.example.data.repo.local.PrefInterface
import com.example.data.repo.remote.KhutbahApiIml
import com.example.data.repo.remote.KhutbahApiInterface
import com.example.data.repo.remote.KhutbahInterface
import com.example.homescreen.HomeActivity
import com.example.homescreen.di.HomeBinders
import com.example.onboarding.Di.OnboardingBinder
import com.example.onboarding.OnBoardingActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalTextApi
@Module
abstract class ActivityBinder{

    @ContributesAndroidInjector(modules = [HomeBinders::class])
    abstract fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [OnboardingBinder::class])
    abstract fun bindOnBoardingActivity(): OnBoardingActivity
}