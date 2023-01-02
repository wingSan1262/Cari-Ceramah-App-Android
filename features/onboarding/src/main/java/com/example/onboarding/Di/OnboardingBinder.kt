package com.example.onboarding.Di

import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModelProvider
import com.example.common_viewmodel.ProfileViewModel
import com.example.onboarding.OnBoardingActivity
import dagger.Module
import dagger.Provides



@OptIn(ExperimentalMaterialApi::class)
@Module
class OnboardingBinder {
    @Provides
    fun getProfileVm(
        activity : OnBoardingActivity,
        factory: ViewModelProvider.Factory
    ): ProfileViewModel {
        return ViewModelProvider(
            activity , factory
        ).get(ProfileViewModel::class.java)
    }

}