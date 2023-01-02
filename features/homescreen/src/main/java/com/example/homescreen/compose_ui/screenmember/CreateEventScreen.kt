package com.example.homescreen.compose_ui.screenmember

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.basecomponent.base_compose_component.AuthenticationContent
import com.example.basecomponent.base_compose_component.LoginScreen
import com.example.homescreen.compose_ui.navigation.HomeNavigator


@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.CreateEventScreen(
    // your state data hear && dependancies
    isDim: Boolean,
    onFinish: () -> Unit
) {
    composable( route = HomeNavigator.CREATE_EVENT){
//        CreateEventContent()
        AuthenticationContent { username , pass ->

        }
    }
}

@Composable
@Preview
fun CreateEventContent(){
    Scaffold(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize(),
        content = {
            Text("Halooo Create Screen", color = Color.Black)
        }
    )
}