package com.example.homescreen.compose_ui.widget.lectures

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basecomponent.base_compose_component.ShimmerAnimation
import com.example.basecomponent.base_compose_component.StandardDivider
import com.google.accompanist.pager.HorizontalPager

@Composable
@Preview
fun LectureItemShimmer (
    modifier:  Modifier = Modifier.fillMaxWidth()
) {
    Column(
        modifier = modifier
    ) {

        ShimmerAnimation(
            outerPadding = 8.dp,
            modifier = Modifier.fillMaxWidth().height(200.dp)
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Row(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 5.dp)
        ) {
            Column(modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(end = 12.dp)) {
                ShimmerAnimation(
                    3.dp,
                    Modifier
                        .fillMaxWidth().height(40.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                ShimmerAnimation(
                    3.dp,
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(top = 5.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(end = 12.dp)) {
                ShimmerAnimation(3.dp, Modifier.fillMaxWidth().height(40.dp)
                    .padding(top = 0.dp, bottom = 0.dp)
                    .clip(RoundedCornerShape(16.dp)))
                ShimmerAnimation(3.dp, Modifier.fillMaxWidth().height(40.dp)
                    .padding(top = 5.dp)
                    .clip(RoundedCornerShape(16.dp)))
            }
        }

        StandardDivider(
            Modifier
                .padding(
                    top = 16.dp, start = 14.dp, end = 14.dp, bottom = 9.dp
                )
        )
    }
}