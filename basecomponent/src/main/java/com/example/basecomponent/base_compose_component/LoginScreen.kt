package com.example.basecomponent.base_compose_component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.font.FontWeight.Companion.W800
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.basecomponent.R
import com.example.basecomponent.R.*
import com.example.basecomponent.base_component.ScreenRoute.Companion.LOGIN_LOGOUT_SCREEN
import com.example.basecomponent.theme.*
import com.example.basecomponent.base_compose_component.PasswordField
import com.example.basecomponent.base_compose_component.StandardBottomSheetLayout
import com.example.basecomponent.base_compose_component.StandardDivider
import com.example.basecomponent.base_compose_component.UsernameField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
fun NavGraphBuilder.LoginScreen(
    // your state data hear && dependancies
    onFinish : ()->Unit
) {
    composable( route = LOGIN_LOGOUT_SCREEN){
        AuthenticationContent{ userName , pass ->
            // do something the finish
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun AuthenticationContent(
    onLogin : (String, String)->Unit = {userName, pass ->}
){
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState =
    BottomSheetState(BottomSheetValue.Collapsed))

    StandardBottomSheetLayout(
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        bottomSheetContent = { SignUpBottomSheetContent() },
        onClose = {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.collapse() }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.onboard_logo),
                contentDescription = "splash_background",
                modifier = Modifier
                    .padding(top = 40.dp)
                    .padding(bottom = 28.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = stringResource(id = string.salam) + ",",
                fontSize = 15.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier
                    .padding(top = 45.dp, start = 16.dp)
                    .fillMaxWidth(),
                color = Black
            )
            Text(
                text = stringResource(id = string.welcome),
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(),
                color = Black
            )

            UsernameField(
                hintText = stringResource(id = string.username),
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 45.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 50.dp),
                onTextChange = {
                    userName = it
                }
            )

            PasswordField(
                hintText = stringResource(id = string.password),
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 45.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 14.dp),
                onTextChange = {
                    password = it
                }
            )
            Text(
                stringResource(id = string.forgot_password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 18.dp, top = 8.dp),
                textAlign = TextAlign.End,
                fontSize = 10.sp,
                color = GRAY_707070_dim
            )

            Button(
                onClick = {
                    onLogin(userName, password)
                },
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp),
                content = {
                    Text(
                        text = stringResource(id = string.login),
                        fontSize = 16.sp,
                        fontWeight = W800,
                        color = WhiteFFFFFF,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                },
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp,
                    disabledElevation = 0.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 32.dp, start = 16.dp, end = 16.dp, top = 45.dp
                    ),
                colors = ButtonDefaults.buttonColors(backgroundColor = BLUE_001D6E)
            )

            Spacer(
                Modifier.weight(1f)
            )
            StandardDivider(
                Modifier
                    .padding(
                        bottom = 16.dp, start = 48.dp, end = 48.dp
                    )
            )
            Text(text = stringResource(id = string.signup), fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .clickable {
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    },
                color = Black
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SignUpBottomSheetContent(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(262.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(id = string.signup),
            fontSize = 16.sp, color = GRAY_797979, fontWeight = Bold,
            modifier = Modifier.padding(bottom = 14.dp, top = 32.dp)
        )
        StandardDivider()

        Text(
            modifier = Modifier.padding(bottom = 14.dp,
                top = 10.dp, start = 11.dp, end = 11.dp),
            text = stringResource(
                id = string.register_msg),
            fontSize = 14.sp, color = GRAY_707070, textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        Text( " ${stringResource(id = string.email_header)}" +
                "\n${stringResource(id = string.subject_header)}",
            fontSize = 13.sp, color = GRAY_707070, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(bottom = 0.dp,
                top = 24.dp, start = 11.dp, end = 11.dp)
        )
    }
}
