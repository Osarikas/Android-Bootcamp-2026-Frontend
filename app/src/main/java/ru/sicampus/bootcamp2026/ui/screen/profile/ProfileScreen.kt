package ru.sicampus.bootcamp2026.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.sicampus.bootcamp2026.ui.screen.meetings.MeetingViewModel

@Composable
fun Profile(
    viewmodel: ProfileViewModel = viewModel<ProfileViewModel>(),
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) { }
    TextButton(
        onClick = { viewmodel.logout() }
    ) {
        Text("Logout")
    }
}