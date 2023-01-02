package com.example.onboarding.ui.compose_component.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.basecomponent.base_component.ScreenRoute.Companion.SPLASH_SCREEN
import com.example.basecomponent.base_compose_component.LoginScreen
import com.example.common_viewmodel.ProfileViewModel
import com.example.onboarding.ui.compose_component.screenmember.OnboardingScreen
import com.example.onboarding.ui.compose_component.screenmember.SplashScreen

@ExperimentalMaterialApi
@Composable
fun OnboardingComposeNavigation(
    profileViewModel: ProfileViewModel,
    nav: NavHostController,
    onContinueToHome : ()->Unit = {}
){
    val screens = remember(nav){
        OnBoardingNavigator(nav)
    }

    val isOnboard = profileViewModel.isOnBoard.observeAsState()

    LaunchedEffect(isOnboard.value){
        if(isOnboard.value == null) { return@LaunchedEffect }
        if(isOnboard.value == true)
            onContinueToHome() else {
                screens.navigateToOnboardingScreen(true)
        }
    }

    NavHost(nav, startDestination = SPLASH_SCREEN){
        SplashScreen { profileViewModel.getOnBoardStatus() }
        OnboardingScreen (
            profileViewModel,
            onLogin = { screens.navigateToAutenthicationScreen(true) },
            onContinue = {
                profileViewModel.setOnBoardStatue()
                onContinueToHome()
            }
        )
        LoginScreen {}
    }
}