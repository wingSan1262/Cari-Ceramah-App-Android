package com.example.basecomponent.base_compose_component

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basecomponent.theme.GRAY_CACACA
import com.example.basecomponent.theme.WhiteFFFFFF
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@Composable
fun PasswordField(hintText : String = "Username / Password",
                  onTextChange : (content : String )->Unit = {},
                  modifier: Modifier = Modifier.background(Color.Transparent)){
    LoginTextField(
        hintText, onTextChange, true, modifier
    )
}

@Composable
fun UsernameField(hintText : String = "Username / Password",
                  onTextChange : (content : String )->Unit = {},
                  modifier: Modifier = Modifier.background(Color.Transparent)){
    LoginTextField(
        hintText, onTextChange, false, modifier
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
fun LoginTextField(
    hintText : String = "Username / Password",
    onTextChange : (content : String )->Unit = {},
    isPassword : Boolean = false,
    modifier: Modifier = Modifier.background(Color.Transparent)
){

    var textValue by remember {
        mutableStateOf("")
    }

    val coroutineScope = rememberCoroutineScope()
    val debounceState = MutableStateFlow<String?>(null).apply {
        coroutineScope.launch{
            debounce(700)
                .distinctUntilChanged()
                .collect {
                    onTextChange(it ?: "")
                }
        }
    }

    OutlinedTextField(
        value = textValue,
        onValueChange = {
            textValue = it
            coroutineScope.launch {
                debounceState.emit(it)
            }
        },
        modifier = modifier,
        textStyle = LocalTextStyle.current.copy(
            fontSize = 14.sp
        ),
        placeholder = {
            Text(
                hintText,
                color = GRAY_CACACA
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = GRAY_CACACA,
            unfocusedIndicatorColor = GRAY_CACACA,
            disabledIndicatorColor = Color.Transparent,
            backgroundColor = WhiteFFFFFF,
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else
            VisualTransformation.None,
        maxLines = 1,
        singleLine = true
    )
}