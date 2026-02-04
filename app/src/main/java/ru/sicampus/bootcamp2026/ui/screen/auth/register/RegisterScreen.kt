package ru.sicampus.bootcamp2026.ui.screen.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.TextButton
import androidx.compose.ui.focus.onFocusChanged

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    navController: NavController,
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val currentState = state) {
            is RegisterState.Data -> Content(viewModel, currentState, navController)
            is RegisterState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            }

            RegisterState.Success -> Unit
        }
    }
}

@Composable
private fun Content(
    viewModel: RegisterViewModel,
    state: RegisterState.Data,
    navController: NavController,
) {
    val fields by viewModel.fields.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Привет! Давай зарегистрируемся",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        RegistrationField(
            value = fields.name,
            label = "ФИО",
            onValueChange = { viewModel.onIntent(RegisterIntent.FieldChanged(fields.copy(name = it))) },
            error = state.fieldErrors["name"],
            onFocusChanged = { viewModel.onFieldFocusChanged("name", it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        RegistrationField(
            value = fields.username,
            label = "Логин",
            onValueChange = { viewModel.onIntent(RegisterIntent.FieldChanged(fields.copy(username = it))) },
            error = state.fieldErrors["username"],
            onFocusChanged = { viewModel.onFieldFocusChanged("username", it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        RegistrationField(
            value = fields.password,
            label = "Пароль",
            isPassword = true,
            onValueChange = { viewModel.onIntent(RegisterIntent.FieldChanged(fields.copy(password = it))) },
            error = state.fieldErrors["password"],
            onFocusChanged = { viewModel.onFieldFocusChanged("password", it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        RegistrationField(
            value = fields.position,
            label = "Должность",
            onValueChange = { viewModel.onIntent(RegisterIntent.FieldChanged(fields.copy(position = it))) },
            error = state.fieldErrors["position"],
            onFocusChanged = { viewModel.onFieldFocusChanged("position", it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        RegistrationField(
            value = fields.email,
            label = "Почта",
            onValueChange = { viewModel.onIntent(RegisterIntent.FieldChanged(fields.copy(email = it))) },
            error = state.fieldErrors["email"],
            onFocusChanged = { viewModel.onFieldFocusChanged("email", it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )

        RegistrationField(
            value = fields.phoneNumber,
            label = "Номер телефона",
            onValueChange = { viewModel.onIntent(RegisterIntent.FieldChanged(fields.copy(phoneNumber = it))) },
            error = state.fieldErrors["phoneNumber"],
            onFocusChanged = { viewModel.onFieldFocusChanged("phoneNumber", it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { viewModel.onIntent(RegisterIntent.Submit) })
        )
        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Уже есть аккаунт? Войти")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.onIntent(RegisterIntent.Submit) },
            enabled = state.isEnabledSend
        ) {
            Text("Зарегистрироваться")
        }

        if (state.error != null) {
            Text(text = state.error, color = Color.Red, textAlign = TextAlign.Center)
        }
    }
}



@Composable
private fun RegistrationField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    error: String?,
    isPassword: Boolean = false
){
    val passwordVisible = remember { mutableStateOf(false) }
    TextField(
        value = value,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().onFocusChanged{focusState ->
            onFocusChanged(focusState.isFocused)
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = onValueChange,
        isError = error != null,
        visualTransformation =  if (isPassword && !passwordVisible.value) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        }
    )
    if (error != null) {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}