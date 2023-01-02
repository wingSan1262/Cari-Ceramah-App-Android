package com.example.cariceramah.DI

import android.app.Activity
import android.app.Application
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.ExperimentalTextApi
import com.example.cariceramah.app.CariCeramahApp
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@OptIn(ExperimentalComposeUiApi::class, ExperimentalPagerApi::class, ExperimentalTextApi::class)
@ExperimentalMaterialApi
@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        NetworkBinder::class, FactoryBinders::class, ActivityBinder::class,
        UseCaseBinder::class, ServiceBinder::class
    ]
)
interface AppComponent : AndroidInjector<CariCeramahApp>{
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}