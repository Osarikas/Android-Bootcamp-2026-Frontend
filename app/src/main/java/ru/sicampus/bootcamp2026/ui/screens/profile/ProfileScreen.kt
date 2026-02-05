package ru.sicampus.bootcamp2026.ui.screens.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import java.io.InputStream

@Composable
fun Profile(
    viewmodel: ProfileViewModel = viewModel()
) {
    val state by viewmodel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = { viewmodel.logout() }) {
                Text("Logout", color = Color.Red)
            }
        }
        when (val s = state) {
            is ProfileState.Loading -> CircularProgressIndicator()
            is ProfileState.Error -> Text(s.reason, color = Color.Red)
            is ProfileState.Content -> {
                val user = s.user

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



@Preview
@Composable
fun ProfileScreen() {
    val isProfileEditMode by remember { mutableStateOf(true) }

    if (isProfileEditMode) {
        ProfileEditMode()
    } else {
        ProfileViewMode()
    }
}


@Composable
fun ProfileViewMode() {
}

@Composable
fun ProfileEditMode() {

    var inputName by remember { mutableStateOf("") }
    var inputPosition by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputPhone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        ImageInput()

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

                },
                enabled = true
            ) {
                Text("Сохранить")
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                }
            ) {
                Text("Отмена")
            }
        }
    }
}



// Временное поле выбора изображения ->

@Composable
fun ImageInput() {
    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImage = loadBitmapFromUri(context, it)
        }
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .clickable {
                imagePicker.launch("image/*")
            },
        contentAlignment = Alignment.Center
    ) {
        if (selectedImage != null) {
            Image(
                bitmap = selectedImage!!.asImageBitmap(),
                contentDescription = "Выбранное фото"
            )
        } else {
            Text("Нажмите для выбора фото")
        }
    }
}

fun loadBitmapFromUri(context: android.content.Context, uri: Uri): Bitmap? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        null
    }
}