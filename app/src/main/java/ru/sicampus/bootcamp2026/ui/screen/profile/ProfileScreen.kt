package ru.sicampus.bootcamp2026.ui.screen.profile

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import ru.sicampus.bootcamp2026.R
import ru.sicampus.bootcamp2026.domain.entities.ProfileUpdateEntity
import ru.sicampus.bootcamp2026.ui.FABViewModel

@Composable
fun Profile(
    viewmodel: ProfileViewModel = viewModel()
) {
    val state by viewmodel.uiState.collectAsState()
    val isEditMode by viewmodel.isEditMode.collectAsState()
    val activity = LocalActivity.current as? ComponentActivity
    val fabVm = if (activity != null) viewModel<FABViewModel>(activity) else viewModel()

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        TextButton(onClick = { viewmodel.onIntent(ProfileIntent.Logout) }) {
            Text("Logout", color = Color.Red)
        }
    }
    LaunchedEffect(isEditMode, state) {
        if (isEditMode || state !is ProfileState.Content) {
            fabVm.setConfig(null)
        } else {
            fabVm.setConfig(R.drawable.ic_edit) {
                viewmodel.onIntent(ProfileIntent.SetEditMode)
            }
        }
    }

    if (isEditMode) {
        ProfileEditMode(
            state, onCancel = { viewmodel.onIntent(ProfileIntent.Cancel) },
            onSave = {data ->
                viewmodel.onIntent(ProfileIntent.Save(data))
            }
        )
    } else {
        ProfileViewMode(state)
    }

}


@Composable
fun ProfileViewMode(state : ProfileState) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (state) {
            is ProfileState.Loading -> CircularProgressIndicator()
            is ProfileState.Error -> Text(state.reason, color = Color.Red)
            is ProfileState.Content -> {
                val user = state.user

                AsyncImage(
                    model = user.photoUrl,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp).clip(CircleShape),
                    placeholder = ColorPainter(Color.LightGray)
                )
                Text(user.name, style = MaterialTheme.typography.headlineMedium)
                Text(user.position, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Username: ${user.username}")
                    Text("Email: ${user.email}")
                    Text("Phone: ${user.phoneNumber}")
                }
            }
        }
    }
}

@Composable
fun ProfileEditMode(
    state: ProfileState,
    onCancel: () -> Unit,
    onSave: (ProfileUpdateEntity) -> Unit
) {
    when(state){
        is ProfileState.Content -> {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var inputName by remember { mutableStateOf(state.user.name) }
                var inputPosition by remember { mutableStateOf(state.user.position) }
                var inputEmail by remember { mutableStateOf(state.user.email) }
                var inputPhone by remember { mutableStateOf(state.user.phoneNumber) }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = inputName,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = {
                        inputName = it
                    },
                    label = { Text("ФИО") }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = inputPosition,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = {
                        inputPosition = it
                    },
                    label = { Text("Должность") }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = inputEmail,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    onValueChange = {
                        inputEmail = it
                    },
                    label = { Text("Почта") }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = inputPhone,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = {
                        inputPhone = it
                    },
                    label = { Text("Телефон") }
                )


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val updateData = ProfileUpdateEntity(
                                name = inputName,
                                position = inputPosition,
                                email = inputEmail,
                                phoneNumber = inputPhone
                            )
                            onSave(updateData)
                        },
                        enabled = true
                    ) {
                        Text("Сохранить")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onCancel()
                        }
                    ) {
                        Text("Отмена")
                    }
                }
            }
        }
        is ProfileState.Error -> Text(state.reason, color = Color.Red)
        ProfileState.Loading -> CircularProgressIndicator()
    }




}



// Временное поле выбора изображения ->

