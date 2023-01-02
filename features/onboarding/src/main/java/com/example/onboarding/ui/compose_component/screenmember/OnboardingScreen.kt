package com.example.onboarding.ui.compose_component.screenmember

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.basecomponent.base_component.BaseActivityCompose
import com.example.basecomponent.base_component.HandleLocationPermission
import com.example.basecomponent.base_component.ScreenRoute.Companion.ONBOARDING_SCREEN
import com.example.basecomponent.base_component.advancedShadow
import com.example.basecomponent.theme.*
import com.example.common_viewmodel.ProfileViewModel
import com.example.onboarding.R
import com.example.onboarding.ui.compose_component.navigation.OnBoardingNavigator

fun NavGraphBuilder.OnboardingScreen(
    // your state data hear && dependancies
    profileViewModel: ProfileViewModel,
    onContinue: () -> Unit,
    onLogin: () -> Unit,
) {
    composable( route = ONBOARDING_SCREEN){

        val activity = (LocalContext.current as BaseActivityCompose)
        var showPermission by remember {
            mutableStateOf(false)
        }
        var isDim by remember {
            mutableStateOf(false)
        }

        OnboardScreenContent(
            isDim,
            onLogin = onLogin,
            onFinish = {
                if(!showPermission){ showPermission=true; return@OnboardScreenContent; }
                onContinue()
            }
        )
        BackHandler { activity.finish() }
        if(showPermission)
            HandleLocationPermission(
                activity,
                onShow = {isDim = it}
            ){ if(it) onContinue() }
    }
}

@Composable
@Preview
fun OnboardScreenContent(
    isDim : Boolean = false,
    onFinish : ()->Unit = {},
    onLogin : ()->Unit = {},
){
    Scaffold(
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.8f, true),
                )
                Image(
                    painter = painterResource(id = R.drawable.onboard_logo),
                    contentDescription = "splash_background",
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(start = 60.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f, true),
                )
                Image(
                    painter = painterResource(id = R.drawable.onboarding_illustration),
                    contentDescription = "splash_background",
                    modifier = Modifier
                        .weight(4f, true)
                        .fillMaxWidth()
                    ,
                    contentScale = ContentScale.Fit
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f, true),
                )
                Button(
                    onClick = {
                        onFinish()
                              },
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 4.dp,
                        disabledElevation = 0.dp
                    ),
                    content = {
                        Text(
                            text = stringResource(id = com.example.basecomponent.R.string.find_lecture).uppercase(),
                            fontSize = 16.sp,
                            color = WhiteFFFFFF,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp, start = 18.dp, end = 18.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = BLUE_001D6E)
                )
                Divider(
                    color = GRAY_EFEFEF, thickness = 1.dp,
                    modifier = Modifier
                        .padding(
                            bottom = 26.dp
                        )
                )

                Row(
                    modifier = Modifier
                        .padding(bottom = 42.dp)
                ){
                    Text(
                        text = stringResource(id = com.example.basecomponent.R.string.sign_up_1),
                        color = Color.Black)
                    Text(
                        text = stringResource(id = com.example.basecomponent.R.string.sign_up_2),
                        fontWeight = FontWeight.Bold, color = Color.Black,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable {
                                onLogin()
                            }
                    )
                }
            }
            if(isDim){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .advancedShadow(
                        GRAY_707070_dim,
                        0.6f,
                        0.dp,
                        1.dp,
                    )
                    .clickable {  }
                )
            }
        }
    )
}
