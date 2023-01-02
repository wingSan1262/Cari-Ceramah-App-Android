package com.example.homescreen.compose_ui.navigation

import androidx.navigation.NavHostController
import com.example.basecomponent.base_component.BaseNavigator

class HomeNavigator(
    val nav : NavHostController
) : BaseNavigator() {

    companion object{
        val HOME_LECTURE = "home_lecture"
        val CREATE_EVENT = "create_event"
    }

    fun navigateToSplashScreen () {
        nav.navigate(
            route = HOME_LECTURE
        )
    }

    fun navigateToCreateEvent () {
        nav.navigate(
            route = CREATE_EVENT
        )
    }

    fun navigateByBottomNav(route : String){
        nav.navigate(route = route){
            popUpTo(route){
                inclusive = true
            }
        }
    }
}