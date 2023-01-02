package com.example.homescreen.compose_ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basecomponent.theme.BLUE_001D6E
import com.example.basecomponent.theme.SAGE_C1DADA
import com.example.basecomponent.base_compose_component.NotificationWidget
import com.example.basecomponent.base_compose_component.SearchFieldText
import com.example.homescreen.R

@ExperimentalComposeUiApi
@Composable
@Preview
fun HomeTaskBar(
    notificationCounter : Int = 0,
    onSearch : (String) -> Unit ={},
    onCloseClick : ()->Unit = {},
    onBurgerClick : ()->Unit = {}
) {

    Card(
        elevation = AppBarDefaults.TopAppBarElevation,
        modifier = Modifier
            .height(60.dp)
            .background(color = SAGE_C1DADA)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(color = SAGE_C1DADA)
                .fillMaxSize()
                .padding(bottom = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.onboard_logo),
                contentDescription = "splash_background",
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .padding(start = 9.dp),
                contentScale = ContentScale.Fit
            )

            SearchFieldText(
                onTextChange = onSearch,
                onCloseClicked = onCloseClick,
                modifier = Modifier
                    .background(Color.Transparent)
                    .height(30.dp)
                    .fillMaxWidth()
                    .weight(6f)
                    .padding(start = 7.dp)
                    .border(1.dp, BLUE_001D6E, shape = RoundedCornerShape(15.dp))
            )

            Image(
                painter = painterResource(id = R.drawable.burger_icon),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start =8.dp, end = 14.dp)
                    .clickable { onBurgerClick() },
                contentScale = ContentScale.Fit, contentDescription = ""
            )
//            NotificationWidget(
//                Modifier
//                    .wrapContentSize()
//                    .padding(start = 6.dp, end = 8.dp, top = 2.dp)
//            )
        }
    }

}