package com.example.cariceramah.app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import com.example.cariceramah.DI.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class CariCeramahApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        val appContext: Context?
            get() = context
    }
}