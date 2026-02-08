package ru.sicampus.bootcamp2026.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sicampus.bootcamp2026.ui.theme.Black
import ru.sicampus.bootcamp2026.ui.theme.Red
import ru.sicampus.bootcamp2026.ui.theme.SecondaryGray

@Preview
@Composable
fun InboxItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SecondaryGray)
            .padding(20.dp, 24.dp, 20.dp, 20.dp)
    ) {
        Status()

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Black,
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Black,
            maxLines = 2
        )
    }
}

@Composable
private fun Status() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(8.dp)
                .background(Red)
        )
        Text(
            text = "Вас пригласили на встречу",
            fontSize = 14.sp,
            color = Red
        )
    }
}
