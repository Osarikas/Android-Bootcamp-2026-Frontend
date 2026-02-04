package ru.sicampus.bootcamp2026.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage



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