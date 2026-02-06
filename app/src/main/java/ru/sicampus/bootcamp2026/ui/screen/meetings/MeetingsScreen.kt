package ru.sicampus.bootcamp2026.ui.screen.meetings.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.FlowPreview
import ru.sicampus.bootcamp2026.ui.screen.meetings.EmployeeSearchState
import ru.sicampus.bootcamp2026.ui.screen.meetings.MeetingViewModel

@OptIn(FlowPreview::class)
@Composable

fun MeetingsScreen(
    viewmodel: MeetingViewModel = viewModel<MeetingViewModel>(),
) {
    val state by viewmodel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        SearchField(viewmodel.searchState)

        Box(modifier = Modifier.weight(1f)) {
            when (val currentState = state) {
                is EmployeeSearchState.Error -> ListErrorState(
                    state = currentState,
                    onRefresh = { viewmodel.getData() }
                )
                is EmployeeSearchState.Loading -> ListLoadingState()
                is EmployeeSearchState.Content -> ListContentState(currentState)
            }
        }
    }
}
@Composable
private fun ListLoadingState(
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp)
        )
    }

}
@Composable
private fun ListErrorState(
    state: EmployeeSearchState.Error,
    onRefresh: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(state.reason)
            Button(
                onClick = onRefresh
            ) {
                Text("Refresh")
            }
        }
    }
}
@OptIn(FlowPreview::class)
@Composable
private fun ListContentState(
    state: EmployeeSearchState.Content
){
    var selectedUserName by remember { mutableStateOf<String?>(null) }



    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.inputError ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = Red
            )
        }
        if (state.users.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Пользователей не обнаружено",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        } else {
            state.users.forEach { user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedUserName = if (selectedUserName == user.name) null else user.name
                        }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val photoUrl: String = if(user.photoUrl == null){
                        "https://i.ibb.co/23xhLxL9/photo-2026-01-04-21-57-33.jpg"
                    } else{
                        Log.d("KTOR", user.photoUrl)
                        user.photoUrl
                    }
                    AsyncImage(
                        modifier = Modifier.size(96.dp).clip(CircleShape),
                        model = photoUrl,
                        contentDescription = null,
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(user.name)
                        Text(user.position)

                        if (selectedUserName == user.name) {
                            Text(user.username)
                            Text(user.email)
                            Text(user.phoneNumber)
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun SearchField(
    searchState: TextFieldState
){
    OutlinedTextField(
        state = searchState,
        label = { Text("Search") },
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    )
}