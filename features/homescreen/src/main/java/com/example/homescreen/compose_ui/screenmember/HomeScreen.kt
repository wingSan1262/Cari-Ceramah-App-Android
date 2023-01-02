package com.example.homescreen.compose_ui.screenmember

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.DrawerValue.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.basecomponent.R.*
import com.example.basecomponent.base_component.*
import com.example.basecomponent.base_component.ErrorType.*
import com.example.basecomponent.base_compose_component.ErrorWidget
import com.example.basecomponent.baseentities.ResourceState
import com.example.basecomponent.theme.*
import com.example.common_viewmodel.ProfileViewModel
import com.example.domain.model.LectureModel
import com.example.homescreen.compose_ui.DrawerFadeIn
import com.example.homescreen.compose_ui.DrawerFadeOut
import com.example.homescreen.compose_ui.navigation.HomeNavigator
import com.example.homescreen.compose_ui.widget.HomeTaskBar
import com.example.homescreen.compose_ui.widget.lectures.LectureItemCompose
import com.example.homescreen.compose_ui.widget.lectures.LectureItemShimmer
import com.example.homescreen.compose_ui.widget.lectures.dummyLectureList
import com.example.homescreen.model.LectureShimmerModel
import com.example.homescreen.viewmodel.LecturesViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalTextApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.HomeScreen(
    lectureVm: LecturesViewModel,
    profileVm: ProfileViewModel,
    isLocationEnvOk: Boolean,
    isDim: Boolean,
    onFinish: () -> Unit,
) {

    composable( route = HomeNavigator.HOME_LECTURE){
        HomeScreenContent(lectureVm, profileVm, isLocationEnvOk, isDim)
    }
}

@ExperimentalTextApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreenContent(
    lectureVm: LecturesViewModel,
    profileVm : ProfileViewModel,
    isLocationEnvOk: Boolean,
    isDim: Boolean = false,
    owner : Activity = LocalContext.current as Activity,
) {

    val scrollState = rememberLazyListState()

    lectureVm.gpsLocation.observeAsState()
    val registerNotif = profileVm.registerFcmStatus.observeAsState()
    val unregisterNotif = profileVm.unregisterFcmStatus.observeAsState()

    val isSearchingState = lectureVm.isSearch.observeAsState()
    val nearestDataState = lectureVm.nearestData.observeAsState()
    val searchDataState = lectureVm.searchData.observeAsState()
    val locationState = lectureVm.locationData.observeAsState()
    val tokenFcmState = profileVm.getFcmData.observeAsState()
    val endOfListReached by remember {
        derivedStateOf { scrollState.isScrolledToEnd() }
    }
    val isError = lectureVm.isError.observeAsState()

    var drawerState by remember { mutableStateOf(Closed) }
    val nearestList = remember { mutableStateListOf<BaseListModel>()
        .apply {
            addAll(getShimmerLoad()) }}
    val searchList = remember { mutableStateListOf<BaseListModel>()
        .apply {
            addAll(getShimmerLoad()) }}
    var tokenFcm by remember { mutableStateOf("") }

    val interactionSource = remember { MutableInteractionSource() }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd){
        Scaffold(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    drawerState = Closed
                },
            topBar = {
                HomeTaskBar(
                    onCloseClick = {
                        lectureVm.setSearch(false)
                        lectureVm.setError(NO_ERROR)
                    },
                    onSearch = {
                        if(it.isEmpty()) return@HomeTaskBar
                        lectureVm.setSearch(true)
                        lectureVm.currentQuery = it
                        lectureVm.searchLectures()
                    },
                    onBurgerClick = {
                        drawerState = if(drawerState == Open) Closed else Open
                    }
                )
            },
            content = {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = scrollState
                ) {
                    items(
                        if(isSearchingState.value == true)
                            searchList  else nearestList,
                        key= { it.id }
                    ){
                        when (it){
                            is LectureModel ->LectureItemCompose(
                                it,
                                onDirection = {
                                    owner.openMaps(it.latitude, it.longitude, it.placeName)
                                },
                                onCall = owner::doPhoneCall
                            )
                            is LectureShimmerModel -> LectureItemShimmer()
                        }
                    } }
                if(isError.value != NO_ERROR){
                    ErrorWidget(
                        info1 = if(isError.value == EMPTY_DATA) stringResource(id = string.lecture_not_found)
                        else stringResource(id = string.no_internet),
                        info2 = if(isError.value == EMPTY_DATA) stringResource(string.lecture_not_found_2) else "",
                        actionString = if(isError.value == EMPTY_DATA)
                            stringResource(id = string.lecture_not_found_3)
                        else stringResource(id = string.research)
                    ){
                        lectureVm.setSearch(false)
                        lectureVm.fetchNearestLecture()
                        lectureVm.setError(NO_ERROR)
                    }
                }

                if(isDim){
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .advancedShadow(
                            GRAY_707070_dim,
                            0.6f,
                            0.dp,
                            1.dp,
                        )
                        .clickable {  }
                    )
                }
            }
        )

        AnimatedVisibility(
            visible = drawerState == Open,
            enter = DrawerFadeIn(),
            exit = DrawerFadeOut()
        ) {
            CustomDrawer(
                tokenFcm,
            ){
                if(it)
                    locationState.value?.getContent()?.let{
                        profileVm.registerFcm(it)}
                else profileVm.unregisterFcm()
            }
        }
    }

    LaunchedEffect(registerNotif.value){
        registerNotif.value?.nonFilteredContent().run {
            if(this is ResourceState.Failure)
                owner.showToast(this.exception.message.toString())
        }
        profileVm.getFcmToken()
    }
    LaunchedEffect(unregisterNotif.value){
        registerNotif.value?.nonFilteredContent().run {
            if(this is ResourceState.Failure)
                owner.showToast(this.exception.message.toString())
        }
        profileVm.getFcmToken()
    }
    LaunchedEffect(nearestDataState.value, isLocationEnvOk){
        if(!isLocationEnvOk) return@LaunchedEffect
        nearestDataState.value.let { response ->
            when(response){
                is ResourceState.Success ->{
                    nearestList.clear()
                    nearestList.addAll(response.body)
                }
                is ResourceState.Failure -> {
                    if(!response.body.isNullOrEmpty()){
                        nearestList.clear()
                        nearestList.addAll(
                            response.body ?: listOf())
                    }
                    owner.showToast(response.exception.message.toString())
                }
            }
        }
    }

    LaunchedEffect(searchDataState.value, isLocationEnvOk){
        if(!isLocationEnvOk) return@LaunchedEffect
        searchDataState.value.let { response ->
            when(response){
                is ResourceState.Success ->{
                    searchList.clear()
                    searchList.addAll(response.body)
                }
                is ResourceState.Failure -> {
                    if(!response.body.isNullOrEmpty()){
                        searchList.clear()
                        searchList.addAll(
                            response.body ?: listOf())
                    }
                    owner.showToast(response.exception.message.toString())
                }
            }
        }
    }

    LaunchedEffect(tokenFcmState.value){
        tokenFcmState.value?.nonFilteredContent().let{
            tokenFcm = it?.getContent() ?: ""
        }
    }

    LaunchedEffect(locationState.value, isLocationEnvOk){
        if(!isLocationEnvOk) return@LaunchedEffect
        locationState.value.let{
            if(it is ResourceState.Success)
                if(isSearchingState.value == true)
                    lectureVm.searchLectures() else lectureVm.fetchNearestLecture()
            if( it is ResourceState.Failure)
                owner.showToast(it.exception.message.toString()) else
                    lectureVm.fetchLocation()
        }
    }

    LaunchedEffect(endOfListReached){
        if(endOfListReached){
            if(isSearchingState.value == true) {
                lectureVm.searchLectures()
            } else {
                lectureVm.fetchNearestLecture()
            }
        } }
}

fun getShimmerLoad():List<BaseListModel> {
    return listOf(
        LectureShimmerModel(), LectureShimmerModel(), LectureShimmerModel(), LectureShimmerModel())
}

@ExperimentalTextApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@Composable
@Preview
fun HomeScreenContentPreview(
    listItem: List<LectureModel> = dummyLectureList,
) {
    var drawerState by remember { mutableStateOf(Closed) }
    InvertComposeLayoutRtl{
        ModalDrawer(
            drawerState = DrawerState(drawerState),
            drawerShape = RoundedCornerShape(6.dp),
            drawerBackgroundColor = Cloud_ebf3fe,
            drawerContent = {
                Text("Tesssss")
            }) {
            InvertComposeLayoutLtr {
                Scaffold(
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxSize(),
                    topBar = {
                        HomeTaskBar(
                            onCloseClick = {

                            }, onSearch = {

                            },
                            onBurgerClick = {
                                drawerState = if(drawerState == Open)
                                    Closed else Open
                            }
                        )
                    },
                    content = {
                        LazyColumn() {
                            items(
                                listItem
                            ){
                                LectureItemCompose(
                                    it
                                )
                            } }
                    })
            }
        }
    }
}

@Composable
@Preview
fun CustomDrawer(
    tokenFcm : String = "",
    onNotificationChange : (Boolean)->Unit = {}
){
    Card(
        modifier = Modifier
            .height(170.dp)
            .width(135.dp),
        elevation = 4.dp,
        backgroundColor = Cloud_ebf3fe,
        shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End
        ){
            Text(
                stringResource(id = string.notification),
                fontSize = 13.sp,
                fontWeight = FontWeight.W600,
                color = BLUE_001D6E,
                modifier = Modifier
                    .padding(start = 12.dp, top = 15.dp)
                    .weight(2.5f)
            )
            Switch(
                checked = tokenFcm.isNotEmpty(),
                onCheckedChange = onNotificationChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = GRAY_707070,
                    uncheckedTrackColor = GRAY_DFDFDF,
                    checkedThumbColor = BLUE_001D6E,
                    checkedTrackColor = GRAY_DFDFDF,

                )
            )
        }
    }
}