package com.example.cariceramah.DI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basecomponent.annotation.ViewModelKey
import com.example.cariceramah.DI.factories.ViewModelProviderFactory
import com.example.common_viewmodel.ProfileViewModel
import com.example.homescreen.viewmodel.LecturesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class FactoryBinders{

    @Binds
    @Singleton
    abstract fun providesViewModelFactory(
        factory: ViewModelProviderFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun providesLoginViewModelFactory(
        loginViewModel: ProfileViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LecturesViewModel::class)
    abstract fun providesLectureViewModelFactory(
        lecturesViewModel: LecturesViewModel
    ): ViewModel

}