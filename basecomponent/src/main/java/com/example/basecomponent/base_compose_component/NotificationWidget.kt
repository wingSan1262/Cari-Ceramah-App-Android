package com.example.basecomponent.base_compose_component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basecomponent.R
import com.example.basecomponent.theme.BLUE_001D6E
import com.example.basecomponent.theme.RED_FF0000

@Composable
@Preview(showBackground = true)
fun NotificationWidget(
    modifier: Modifier = Modifier.wrapContentSize(),
    counter : Int = 0
) {
    val counterState by remember {
        mutableStateOf(counter)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd
    ){
        Image(

            painter = painterResource(id = R.drawable.notification_logo),
            contentDescription = "splash_background",
            modifier = Modifier
                .padding(4.dp)
                .size(26.dp)
        )
        Card(
            modifier = Modifier
                .height(15.dp)
                .width(15.dp),
            shape = CircleShape,
            backgroundColor = RED_FF0000,
        ) {
            Box(
                modifier =  Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    counterState.toString(), color = Color.White, fontSize = 7.sp
                )
            }
        }
    }

}