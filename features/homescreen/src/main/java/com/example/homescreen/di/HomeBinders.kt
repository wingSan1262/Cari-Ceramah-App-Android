package com.example.homescreen.di

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.lifecycle.ViewModelProvider
import com.example.basecomponent.annotation.ViewModelKey
import com.example.common_viewmodel.ProfileViewModel
import com.example.homescreen.HomeActivity
import com.example.homescreen.viewmodel.LecturesViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


@OptIn(ExperimentalTextApi::class, ExperimentalPagerApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Module
class HomeBinders {

    @Provides
    fun getProfileVm(
        activity : HomeActivity,
        factory: ViewModelProvider.Factory
    ): ProfileViewModel {
        return ViewModelProvider(
            activity , factory
        )[ProfileViewModel::class.java]
    }

    @Provides
    fun getLectureViewModel(
        activity : HomeActivity,
        factory: ViewModelProvider.Factory
    ): LecturesViewModel {
        return ViewModelProvider(
            activity as AppCompatActivity, factory
        )[LecturesViewModel::class.java]
    }

}