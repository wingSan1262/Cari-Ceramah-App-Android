package com.example.basecomponent.base_compose_component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basecomponent.R
import com.example.basecomponent.theme.BLUE_001D6E
import com.example.basecomponent.theme.WhiteFFFFFF

@Composable
@Preview(showBackground = true)
fun ErrorWidget(
    info1 : String = "Kajian yang kamu cari belum ada nih",
    info2 : String = "Yuk, cari kajian yang lain",
    actionString : String = "Cari Ulang",
    action : ()->Unit = {}
) {
    Column(
        horizontalAlignment =
        Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(WhiteFFFFFF),

    ) {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .weight(1f))
        Image(
            painter = painterResource(id = R.drawable.no_content_ic),
            contentDescription = "no content"
        )
        
        Text(
            textAlign = TextAlign.Center,
            text = info1,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            textAlign = TextAlign.Center,
            text = info2, fontWeight = FontWeight.W600,
            modifier = Modifier.padding(top = 8.dp)
        )
        if(!actionString.isNullOrEmpty()){
            CommonBlueButton(
                modifier = Modifier
                    .wrapContentSize()
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(top = 32.dp),
                buttonText = actionString
            ){ action() }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .weight(2f))
    }
}

@Composable
@Preview
fun CommonBlueButton(
    modifier: Modifier = Modifier,
    buttonText : String = "",
    textSize: TextUnit = 11.sp,
    corner : Dp = 8.dp,
    onClick : ()->Unit = {}
){
    Button(
        onClick = {
            onClick()
        },

        elevation = ButtonDefaults.elevation(
            defaultElevation = 4.dp,
            pressedElevation = 1.dp,
            disabledElevation = 0.dp
        ),
        content = {
            Text(
                modifier = Modifier.padding(horizontal = 32.dp),
                text = buttonText,
                fontSize = 11.sp,
                color = WhiteFFFFFF,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W900
            )
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = BLUE_001D6E)
    )
}