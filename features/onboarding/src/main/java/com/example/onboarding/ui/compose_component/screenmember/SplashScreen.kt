package com.example.onboarding.ui.compose_component.screenmember

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.basecomponent.R.*
import com.example.basecomponent.base_component.ScreenRoute.Companion.SPLASH_SCREEN
import com.example.onboarding.R
import com.example.onboarding.ui.compose_component.navigation.OnBoardingNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun NavGraphBuilder.SplashScreen(
    // your state data hear && dependancies
    onFinish : ()->Unit
) {
    composable( route = SPLASH_SCREEN){
        SplashScreenContent()
        LaunchedEffect(true){
            launch {
                delay(2000)
                onFinish()
            }
        }
    }
}

@Composable
@Preview
fun SplashScreenContent(){

    Scaffold(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize(),
        content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                    .border(0.dp, Color.Transparent)
            ){
                Image(
                    painter = painterResource(id = drawable.splash_background),
                    contentDescription = "splash_background",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp),
                    contentScale = ContentScale.FillBounds
                )

                Image(
                    painter = painterResource(id = R.drawable.splash_logo),
                    contentDescription = "splash_background",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(
                            bottom = 100.dp
                        )
                    ,
                    contentScale = ContentScale.Fit
                )
            }
        }
    )
}