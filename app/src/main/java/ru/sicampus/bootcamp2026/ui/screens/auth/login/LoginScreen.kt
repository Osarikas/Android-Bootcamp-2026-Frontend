package ru.sicampus.bootcamp2026.ui.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import ru.sicampus.bootcamp2026.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.sicampus.bootcamp2026.components.InputField
import ru.sicampus.bootcamp2026.ui.theme.BgGradientBottom
import ru.sicampus.bootcamp2026.ui.theme.BgGradientTop
import ru.sicampus.bootcamp2026.ui.theme.Black
import ru.sicampus.bootcamp2026.ui.theme.PrimaryGray
import ru.sicampus.bootcamp2026.ui.theme.SecondaryGray
import ru.sicampus.bootcamp2026.ui.theme.White

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    navController: NavController,
    onNavigateToRegister: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.actionFlow.collect{action ->
            when(action) {
                is LoginAction.OpenScreen -> navController.navigate(action.route)
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Привет! Давай авторизуемся",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        when (val currentState = state) {
            is LoginState.Data -> Content(viewModel, currentState, onNavigateToRegister)
            is LoginState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}



@Preview
@Composable
private fun LoginScreen() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                //.fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_logo),
                contentDescription = "*Название приложения/компании",
                Modifier.size(100.dp),
                tint = White
            )
        }
        Spacer(modifier = Modifier.weight(1f))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(772.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(White)
                // .align(Alignment.BottomCenter)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = BgGradientTop)
                    .padding(16.dp)
                    .zIndex(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(SecondaryGray)
                            .clickable(
                                onClick = { }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                            contentDescription = "Назад",
                            modifier = Modifier.size(32.dp),
                            tint = Black
                        )
                    }
                }
                Text(
                    text = "Вход",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }


            var inputLogin by remember { mutableStateOf("") }
            var inputPassword by remember { mutableStateOf("") }
            val focusPasswordRequester = remember { FocusRequester() }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Spacer(modifier = Modifier.height(112.dp))

                InputField(
                    title = "Логин",
                    value = inputLogin,
                    onValueChange = {
                        inputLogin = it
                        // viewModel.onIntent(LoginIntent.TextInput(inputLogin, inputPassword))
                    },
                    placeholderText = "Введите логин",
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusPasswordRequester.requestFocus()
                        }
                    )
                )

                InputField(
                    title = "Пароль",
                    value = inputPassword,
                    onValueChange = {
                        inputPassword = it
                        // viewModel.onIntent(LoginIntent.TextInput(inputLogin, inputPassword))
                    },
                    placeholderText = "Введите пароль",
                    modifier = Modifier.focusRequester(focusPasswordRequester).fillMaxWidth(),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // viewModel.onIntent(LoginIntent.Send(inputLogin, inputPassword))
                        }
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(172.dp)
                    .background(brush = BgGradientBottom)
                    .padding(16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { }
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Black)
                        .clickable(
                            onClick = { }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Войти",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = White
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Нет аккаунта? ",
                        fontSize = 16.sp,
                        color = PrimaryGray
                    )
                    Text(text = "Создать",
                        fontSize = 16.sp,
                        color = Black,
                        modifier = Modifier.clickable(
                                onClick = /* onNavigateToRegister */ { }
                        )
                    )
                }
            }
        }
    }
}





@Composable
private fun Content(
    viewModel: LoginViewModel,
    state: LoginState.Data,
    onNavigateToRegister: () -> Unit
) {
    var inputLogin by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    val focusPasswordRequester = remember { FocusRequester() }
    Spacer(modifier = Modifier.size(16.dp))
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = inputLogin,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusPasswordRequester.requestFocus()
            }
        ),
        onValueChange = {
            inputLogin = it
            viewModel.onIntent(LoginIntent.TextInput(inputLogin, inputPassword))
        },
        label = { Text("Логин") }
    )
    Spacer(modifier = Modifier.size(16.dp))
    TextField(
        modifier = Modifier.focusRequester(focusPasswordRequester).fillMaxWidth(),
        value = inputPassword,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                viewModel.onIntent(LoginIntent.Send(inputLogin, inputPassword))
            }
        ),
        onValueChange = {
            inputPassword = it
            viewModel.onIntent(LoginIntent.TextInput(inputLogin, inputPassword))
        },
        label = { Text("Пароль") }
    )
    Spacer(modifier = Modifier.size(16.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick = onNavigateToRegister) {
            Text("Нет аккаунта? Зарегистрироваться")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.onIntent(LoginIntent.Send(inputLogin, inputPassword))
            },
            enabled = state.isEnabledSend
        ) {
            Text("Войти")
        }

        if (state.error != null) {
            Text(
                modifier = Modifier,
                text = state.error,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red,
            )
        }
    }

}