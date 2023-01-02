package com.example.basecomponent.base_compose_component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.basecomponent.theme.GRAY_8f8f8f
import com.example.basecomponent.theme.GRAY_EFEFEF

@Composable
fun StandardDivider(
    modifier: Modifier = Modifier
        .padding(
            bottom = 18.dp, start = 14.dp, end = 14.dp
        )
){
    Divider(
        color = GRAY_8f8f8f, thickness = 0.5.dp,
        modifier = modifier
    )
}
