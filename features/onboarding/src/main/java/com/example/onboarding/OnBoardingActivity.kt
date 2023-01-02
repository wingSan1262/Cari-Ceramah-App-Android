package com.example.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.basecomponent.base_component.ActivityName
import com.example.basecomponent.base_component.BaseActivityCompose
import com.example.basecomponent.theme.CariceramahTheme
import com.example.common_viewmodel.ProfileViewModel
import com.example.onboarding.ui.compose_component.navigation.OnboardingComposeNavigation
import javax.inject.Inject

@ExperimentalMaterialApi
class OnBoardingActivity : BaseActivityCompose() {

    @Inject
    lateinit var profileViewModel: ProfileViewModel

    private lateinit var navController : NavHostController

    override var isStatusBarTransparant: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberNavController()
            CariceramahTheme{
                OnboardingComposeNavigation(profileViewModel,navController){
                    Intent(this, Class.forName(ActivityName.HOME_ACTIVITY)).run {
                        startActivity(this)
                    }
                    finish()
                }
            }
        }
    }
}
