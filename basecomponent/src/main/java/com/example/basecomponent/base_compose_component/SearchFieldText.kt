package com.example.basecomponent.base_compose_component

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basecomponent.R
import com.example.basecomponent.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@ExperimentalComposeUiApi
@Composable
@Preview( showBackground = true, backgroundColor = 0xFFFFFF)
fun SearchFieldText(
    onTextChange : (String)->Unit = {},
    onCloseClicked : ()->Unit = {},
    placeHolder : String = stringResource(id = R.string.find_lecture).lowercase(),
    modifier: Modifier = Modifier
        .height(30.dp)
        .width(216.dp)
        .border(1.dp, BLUE_001D6E, shape = RoundedCornerShape(15.dp))
) {
    val coroutineScope = rememberCoroutineScope()
    val debounceState = MutableStateFlow<String?>(null).apply {
        coroutineScope.launch{
            debounce(700)
                .distinctUntilChanged()
                .collect {
                    if(it == null) return@collect
                    onTextChange(it)
                }
        }
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember {
        mutableStateOf(placeHolder)
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        backgroundColor = WhiteFFFFFF
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
             Image(
                 painter = painterResource(id = R.drawable.search_logo),
                 contentDescription = "",
                 modifier = Modifier
                     .fillMaxHeight()
                     .weight(1f)
                     .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
             )

            BasicTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    coroutineScope.launch {
                        debounceState.emit(it)
                    }
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = BLUE_001D6E_50,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(7f)
                    .padding(start = 2.dp, bottom = 4.dp)
                    .onFocusChanged {
                        if (it.hasFocus && !isSearching) {
                            searchText = ""
                            isSearching = true
                        }
                    },
            )

            if(isSearching){
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_close_24),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                        .clickable {
                            isSearching = false
                            searchText = placeHolder
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            onCloseClicked()
                        }
                )
            }
        }
    }

}