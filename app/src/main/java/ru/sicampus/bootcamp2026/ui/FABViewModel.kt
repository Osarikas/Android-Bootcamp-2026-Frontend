package ru.sicampus.bootcamp2026.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class FABViewModel : ViewModel() {
    var config by mutableStateOf<FABConfig?>(null)
        private set

    fun setConfig(iconId: Int?, onClick: () -> Unit = {}) {
        config = if (iconId == null) null else FABConfig(iconId, onClick)
    }
}
data class FABConfig(
    val iconId: Int,
    val onClick: () -> Unit
)