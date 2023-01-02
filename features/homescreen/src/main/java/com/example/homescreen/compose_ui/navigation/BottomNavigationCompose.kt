package com.example.homescreen.compose_ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.basecomponent.R.*
import com.example.basecomponent.theme.GRAY_F2F2F2
import com.example.basecomponent.theme.WhiteFFFFFF
import com.example.homescreen.R
import com.example.homescreen.compose_ui.navigation.HomeNavigator.Companion.CREATE_EVENT
import com.example.homescreen.compose_ui.navigation.HomeNavigator.Companion.HOME_LECTURE


data class BottomNavItem(
    val title : String,
    val icon : Int,
    val screenRoute : String
)

@Composable
fun BottomNavigationCompose(
    homeNavigator: HomeNavigator
) {
    val listItem = listOf(
        BottomNavItem(
            stringResource(string.home_btm), drawable.ic_home_btm_nav, HOME_LECTURE
        ),
        BottomNavItem(
            stringResource(string.create_lecture_btm), drawable.ic_create_lecture_btm_nav, CREATE_EVENT
        )
    )

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        BottomNavigation(
            backgroundColor = WhiteFFFFFF
        ) {

            val navBackStackEntry by homeNavigator.nav.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            listItem.forEach {
                BottomNavigationItem(
                    icon = {
                        Card(
                            backgroundColor = if(currentRoute == it.screenRoute)
                                GRAY_F2F2F2 else WhiteFFFFFF,
                            elevation = if(currentRoute == it.screenRoute)
                                AppBarDefaults.TopAppBarElevation else 0.dp,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    vertical = 3.dp, horizontal = 14.dp
                                ),
                        ){
                            Column(
                                horizontalAlignment = CenterHorizontally
                            ){
                                Image(painterResource(id = it.icon),
                                    contentDescription = it.title,
                                    modifier = Modifier
                                        .width(28.dp)
                                        .height(28.dp).padding(top = 3.dp)
                                )
                                Text(
                                    it.title, fontSize = 13.sp, fontWeight = FontWeight.W300,
                                    color = Color.Black
                                )
                            }

                        }
                    },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == it.screenRoute,
                    onClick = {
                        homeNavigator.navigateByBottomNav(it.screenRoute)
                    }
                )
            }
        }
    }

}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}
