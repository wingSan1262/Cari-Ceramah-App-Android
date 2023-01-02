package com.example.homescreen

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.navigation.compose.rememberNavController
import com.example.basecomponent.base_component.BaseActivityCompose
import com.example.basecomponent.base_component.HandleLocationPermission
import com.example.basecomponent.theme.CariceramahTheme
import com.example.common_viewmodel.ProfileViewModel
import com.example.homescreen.compose_ui.navigation.BottomNavigationCompose
import com.example.homescreen.compose_ui.navigation.HomeComposeNavigation
import com.example.homescreen.compose_ui.navigation.HomeNavigator
import com.example.homescreen.viewmodel.LecturesViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject

@ExperimentalTextApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class HomeActivity : BaseActivityCompose() {

    @Inject
    lateinit var lecturesViewModel: LecturesViewModel
    @Inject
    lateinit var profileViewModel: ProfileViewModel

    private lateinit var navigator : HomeNavigator
    override var isStatusBarTransparant: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var isDim by remember {
                mutableStateOf(false)
            }

            navigator = HomeNavigator(
                rememberNavController()
            )
            var isLocationEnvOk by remember { mutableStateOf(false) }
            CariceramahTheme{
                Scaffold (
                    bottomBar = {
                        BottomNavigationCompose(navigator)
                    },
                    content = {
                        HomeComposeNavigation(
                            navigator,lecturesViewModel, profileViewModel, isLocationEnvOk, isDim
                        )
                    }
                )
                val activity = LocalContext.current as Activity
                HandleLocationPermission(
                    activity = activity,
                    onShow = { isDim = it }
                ){
                    isLocationEnvOk = it
                }
            } }
    }
}
