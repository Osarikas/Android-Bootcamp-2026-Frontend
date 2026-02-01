package ru.sicampus.bootcamp2026.ui.main.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.sicampus.bootcamp2026.ui.theme.White

@Composable
fun Meetings() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White) //Временно поменял Black на White, чтобы было видно список
    ) {
        Text(text = "Meetings")
    }
}