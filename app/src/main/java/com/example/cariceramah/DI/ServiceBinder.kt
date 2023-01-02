package com.example.cariceramah.DI

import com.example.cariceramah.FcmService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBinder {

    @ContributesAndroidInjector
    abstract fun provideFcm() : FcmService
}