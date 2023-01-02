package com.example.basecomponent.base_compose_component

import androidx.compose.animation.AnimatedVisibility
import com.example.basecomponent.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basecomponent.theme.*
import com.example.homescreen.compose_ui.DrawerFadeInBottomVertical
import com.example.homescreen.compose_ui.DrawerFadeOut


@Composable
@Preview(showBackground = true, backgroundColor = 0x999999)
fun LocationPermissionBottomSheet(
    onDenied : ()->Unit = {},
    onAccept : ()->Unit = {},
    isVisible : Boolean = false
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = DrawerFadeInBottomVertical(),
        exit = DrawerFadeOut()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                elevation = 32.dp
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        stringResource(id = R.string.permission_and_gps),
                        fontSize = 14.sp, color = BLUE_001D6E, textAlign = TextAlign.Center,
                        modifier = Modifier.padding(
                            bottom = 16.dp,
                            top = 24.dp, start = 40.dp, end = 40.dp
                        )
                    )
                    Text(
                        stringResource(id = R.string.permission_and_gps2),
                        fontSize = 16.sp, color = BLUE_001D6E, fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(
                            bottom = 8.dp,
                            top = 18.dp, start = 11.dp, end = 11.dp
                        )
                    )

                    Row(modifier = Modifier.padding(
                        bottom = 14.dp,
                        top = 18.dp, start = 11.dp, end = 11.dp
                    )){
                        UniversalButton<Any?>(buttonText = stringResource(id = R.string.yes)
                            , modifier = Modifier.padding(8.dp)){
                            onAccept()
                        }
                        UniversalButton<Any?>(buttonText = stringResource(id = R.string.no)
                            , modifier = Modifier.padding(8.dp),
                            backgroundColor = GRAY_DFDFDF, textColor = BLUE_001D6E){
                            onDenied()
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.illustration_location_request),
                        contentDescription = "splash_background",
                        modifier = Modifier.padding(top = 16.dp)
                        ,
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun <T>UniversalButton(
    item : T? = null,
    buttonText : String = "",
    backgroundColor: Color = BLUE_001D6E,
    textColor : Color = WhiteFFFFFF,
    modifier: Modifier = Modifier,
    onClick : (T?)->Unit = {},
){
    Button(
        onClick = {onClick(item)},
        elevation = ButtonDefaults.elevation(
            defaultElevation = 4.dp,
            pressedElevation = 1.dp,
            disabledElevation = 0.dp
        ),
        content = {
            Text(
                text = buttonText,
                fontSize = 11.sp,
                color = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W900
            )
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .wrapContentSize()
            .width(88.dp)
            .wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    )
}