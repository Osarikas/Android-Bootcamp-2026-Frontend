package ru.sicampus.bootcamp2026.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.sicampus.bootcamp2026.ui.theme.Black
import ru.sicampus.bootcamp2026.ui.theme.White



@Composable
fun FloatingActionButton() {
    val activity = LocalActivity.current as? ComponentActivity
    val fabVm = if (activity != null) { viewModel<FABViewModel>(viewModelStoreOwner = activity) } else { viewModel<FABViewModel>() }
    val config = fabVm.config ?: return
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(Black)
            .clickable(
                onClick = {
                    config.onClick()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(config.iconId),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = White
        )
    }
}