package com.example.homescreen.compose_ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.example.common_viewmodel.ProfileViewModel
import com.example.homescreen.compose_ui.screenmember.CreateEventScreen
import com.example.homescreen.compose_ui.screenmember.HomeScreen
import com.example.homescreen.viewmodel.LecturesViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalTextApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun HomeComposeNavigation(
    homeNavigator: HomeNavigator,
    lectureVm: LecturesViewModel,
    profileViewModel: ProfileViewModel,
    isLocationEnvOk : Boolean,
    isDim : Boolean
){
    val navigator by remember {
        mutableStateOf(homeNavigator)
    }

    NavHost(
        navigator.nav,
        startDestination = HomeNavigator.HOME_LECTURE,
        modifier = Modifier.padding(bottom = 40.dp)){
        HomeScreen(lectureVm, profileViewModel,
            isLocationEnvOk, isDim){  }
        CreateEventScreen(isDim) {  }
    }
}