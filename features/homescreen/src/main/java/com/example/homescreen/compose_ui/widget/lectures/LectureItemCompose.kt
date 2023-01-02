package com.example.homescreen.compose_ui.widget.lectures

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.font.FontWeight.Companion.W900
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basecomponent.R.*
import com.example.basecomponent.base_component.getDateDisplay
import com.example.basecomponent.base_component.getHour
import com.example.basecomponent.base_component.getOneDecimalFormat
import com.example.basecomponent.base_component.getRupiahValueFormat
import com.example.basecomponent.base_compose_component.DotIndicatorTabLayout
import com.example.basecomponent.base_compose_component.StandardDivider
import com.example.basecomponent.theme.BLUE_001D6E
import com.example.basecomponent.theme.GRAY_707070
import com.example.basecomponent.theme.WhiteFFFFFF
import com.example.domain.model.LectureModel
import com.example.homescreen.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.util.Date

@ExperimentalTextApi
@ExperimentalPagerApi
@Composable
@Preview(showBackground = true)
fun LectureItemCompose(
    item : LectureModel = LectureModel(
        "123","123","Inilah Rahasia Sedekahmu","Ust Khalid Basalamah","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.","123","123","123",
        123.0,123.0, Date(), Date(),"123",10000,3.6,123,"Asdw","adwaw", arrayListOf(
            "https://miro.medium.com/max/1400/1*M3xO1dvWTpSC353E3y1uiw.webp",
            "https://miro.medium.com/max/1400/1*M3xO1dvWTpSC353E3y1uiw.webp"
        )
    ),
    onShare : (LectureModel)->Unit = {},
    onDirection : (LectureModel)->Unit = {},
    onCall : (String)->Unit = {},
){

    val pagerState = rememberPagerState()

    item.run{

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            HorizontalPager(
                count = item.listImages.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 9.dp)
            ) {
                PosterHolder(listImages[currentPage]){
                    onShare(item)
                }
            }

            if(item.listImages.size > 1)
                DotIndicatorTabLayout(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    totalDots = item.listImages.size,
                    selectedIndex =  pagerState.currentPage
                )

            Row(
                modifier = Modifier
            ) {
                LectureContentLeft(
                    item = item,
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp)
                )
                LectureContentRight(
                    item = item,
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .padding(end = 24.dp),
                    onClickDirection = onDirection,
                    onCall = onCall
                )
            }

            StandardDivider(
                Modifier
                    .padding(
                        top = 16.dp, start = 24.dp, end = 24.dp, bottom = 9.dp
                    )
            )
        }
    }
}

@Composable
fun LectureContentLeft(
    item : LectureModel,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(
            "${item.name} | ${item.lecturer}",
            fontWeight = FontWeight.W900,
            fontSize = 14.sp,
            color = BLUE_001D6E,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        item.startDate.run{
            item.finishDate.let{finishTime ->
                Text(
                    "${getDateDisplay()} " +
                            "| ${getHour()}-${finishTime.getHour()} " +
                            "\n${item.placeName}, ${item.city}",
                    fontSize = 14.sp,
                    color = GRAY_707070,
                    fontWeight = FontWeight.W500,
                    lineHeight = 18.sp
                ) } }

        Spacer(modifier = Modifier.height(15.dp))

        item.book.run{ if(isNullOrEmpty()) return@run
            Text(
                "Buku Ref : $this",
                fontSize = 14.sp,
                color = GRAY_707070,
                fontWeight = FontWeight.W700
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
        Text(
            item.description,
            textAlign = TextAlign.Justify,
            fontSize = 13.sp,
            color = GRAY_707070,
            fontWeight = FontWeight.W400,
            lineHeight = 16.sp
        )
    }
}

@ExperimentalTextApi
@Composable
fun LectureContentRight(
    item : LectureModel,
    modifier: Modifier = Modifier,
    onClickDirection : (LectureModel)->Unit = {},
    onCall : (String)->Unit = {},
){
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        val minsRide : Int = (item.distance * 60 / 20).toInt()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 2.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "${minsRide} ${stringResource(id = string.label_min)} • ",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom =0.dp, top = 1.dp)
            )
            Text(
                (if(item.distance > 10)
                    item.distance.toInt().toString() else item.distance.getOneDecimalFormat().toString()) +
                        " km",
                fontSize = 11.sp,
                modifier = Modifier.padding(bottom =0.dp, top = 1.dp)
            )
        }
        ButtonLectureClick(
            item, stringResource(id = R.string.open_map)
        ){
            it?.let { it1 -> onClickDirection(it1) }
        }

        ButtonLectureClick(
            item.phone, stringResource(id = R.string.do_phone)
        ){
            it?.let { it1 -> onCall(it1) }
        }
        
        PriceCompose(
            price = item.price,
            Modifier
                .align(Alignment.End)
                .padding(top = 6.dp)
        )
    }
}

@Composable
@Preview
fun <T>ButtonLectureClick(
    item : T? = null,
    buttonText : String = "",
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
                fontSize = 12.sp,
                color = WhiteFFFFFF,
                textAlign = TextAlign.Center,
                fontWeight = W900
            )
        },
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(
            vertical = 0.dp,
            horizontal = 16.dp
        ),
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(backgroundColor = BLUE_001D6E)
    )
}

@ExperimentalTextApi
@Composable
fun PriceCompose(
    price : Int,
    modifier: Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            "Rp",
            fontSize = 12.sp,
            fontWeight = FontWeight.W900,
            modifier = Modifier
                .wrapContentHeight()
                .padding(0.dp)
        )
        Text(
            "  ${price.getRupiahValueFormat()}", fontSize = 22.sp,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            ),
            fontWeight = FontWeight.W700,
            modifier = Modifier
                .wrapContentHeight()
                .padding(0.dp)
        )
    }
}

val dummyLectureList = listOf(
    LectureModel(
        "123","123","123","123","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco .","123","123","123",
        123.0,123.0, Date(), Date(),"123",10000,123.00,123,"Asdw","adwaw", arrayListOf(
            "https://miro.medium.com/max/1400/1*M3xO1dvWTpSC353E3y1uiw.webp",
            "https://miro.medium.com/max/1400/1*M3xO1dvWTpSC353E3y1uiw.webp"
        )
    ),
    LectureModel(
        "1231232","123","123","123","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.","123","123","123",
        123.0,123.0, Date(), Date(),"123",10000,123.00,123,"Asdw","adwaw", arrayListOf(
            "https://miro.medium.com/max/1400/1*M3xO1dvWTpSC353E3y1uiw.webp",
            "https://miro.medium.com/max/1400/1*M3xO1dvWTpSC353E3y1uiw.webp"
        )
    )
)

