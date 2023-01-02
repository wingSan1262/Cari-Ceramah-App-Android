package com.example.onboarding.ui.compose_component.navigation

import androidx.navigation.NavHostController
import com.example.basecomponent.base_component.BaseNavigator
import com.example.basecomponent.base_component.ScreenRoute.Companion.LOGIN_LOGOUT_SCREEN
import com.example.basecomponent.base_component.ScreenRoute.Companion.ONBOARDING_SCREEN
import com.example.basecomponent.base_component.ScreenRoute.Companion.SPLASH_SCREEN

class OnBoardingNavigator(
    val nav : NavHostController
) : BaseNavigator() {

    fun navigateToSplashScreen (isPop : Boolean = false) {
        nav.navigate(
            route = SPLASH_SCREEN
        ){ if(isPop) popUpTo(SPLASH_SCREEN) }
    }

    fun navigateToOnboardingScreen (isPop : Boolean = false) {
        nav.navigate(
            route = ONBOARDING_SCREEN
        ){ if(isPop) popUpTo(SPLASH_SCREEN){
            inclusive = true
        } }
    }

    fun navigateToAutenthicationScreen (isPop : Boolean = false) {
        nav.navigate(
            route = LOGIN_LOGOUT_SCREEN
        ){ if(isPop) popUpTo(LOGIN_LOGOUT_SCREEN) }
    }
}