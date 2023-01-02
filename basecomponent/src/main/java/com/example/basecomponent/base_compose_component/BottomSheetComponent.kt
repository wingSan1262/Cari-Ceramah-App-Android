package com.example.basecomponent.base_compose_component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basecomponent.R
import com.example.basecomponent.theme.WhiteFFFFFF

@ExperimentalMaterialApi
@Composable
@Preview
fun StandardBottomSheetLayout(
    bottomSheetScaffoldState : BottomSheetScaffoldState? = null,
    onClose : ()->Unit = {},
    bottomSheetContent : @Composable ()->Unit = {},
    mainScreenContent : @Composable ()->Unit = {},
){
    if (bottomSheetScaffoldState != null) {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetBackgroundColor = Color.Transparent,
            sheetContentColor = Color.Transparent,
            sheetElevation = 0.dp,
            sheetContent = {
                Card(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    backgroundColor = WhiteFFFFFF,
                    contentColor = WhiteFFFFFF,
                    elevation = 32.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 0.dp,
                            end = 0.dp,
                            top = 8.dp,
                            bottom = 0.dp
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            ,
                        contentAlignment = Alignment.TopEnd
                    ){
                        bottomSheetContent()
                        Image(
                            painter = painterResource(id = R.drawable.ic_close)
                            , contentDescription = "close_icon",
                            modifier = Modifier.padding(
                                top = 30.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                                .clickable { onClose() }
                        )
                    }
                }},
            sheetPeekHeight = 0.dp,
            drawerBackgroundColor = Color.Transparent,
        ){mainScreenContent() }
    }
}