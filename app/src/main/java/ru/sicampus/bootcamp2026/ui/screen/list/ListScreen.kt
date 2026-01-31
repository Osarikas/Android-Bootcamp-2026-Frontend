package ru.sicampus.bootcamp2026.ui.screen.list

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@OptIn(FlowPreview::class)
@Composable
fun ListScreen(
    viewmodel: ListViewModel = viewModel<ListViewModel>()
){
    val state by viewmodel.uiState.collectAsState()
    val searchState = rememberTextFieldState()
    LaunchedEffect(searchState) {
        snapshotFlow { searchState.text }
            .map { it.toString() }
            .distinctUntilChanged()
            .debounce(400)
            .collect { query ->
                viewmodel.getData(query)
            }
    }

    when(val currentState = state){
        is ListState.Error -> ListErrorState(currentState, onRefresh = {viewmodel.getData()})
        is ListState.Loading -> ListLoadingState()
        is ListState.Content -> ListContentState(currentState, viewmodel.searchState)
    }

}
@Composable
private fun ListLoadingState(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp)
        )
    }

}
@Composable
private fun ListErrorState(
    state: ListState.Error,
    onRefresh: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
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
    state: ListState.Content,
    searchState: TextFieldState
){
    var selectedUserName by remember { mutableStateOf<String?>(null) }



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            state = searchState,
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

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
                    AsyncImage(
                        modifier = Modifier.size(96.dp).clip(CircleShape),
                        model = user.photoUrl,
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