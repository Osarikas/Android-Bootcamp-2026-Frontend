package ru.sicampus.bootcamp2026.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sicampus.bootcamp2026.R
import ru.sicampus.bootcamp2026.ui.theme.Black
import ru.sicampus.bootcamp2026.ui.theme.PrimaryGray
import ru.sicampus.bootcamp2026.ui.theme.Red
import ru.sicampus.bootcamp2026.ui.theme.SecondaryGray
import ru.sicampus.bootcamp2026.ui.theme.White

@Preview
@Composable
fun InboxItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SecondaryGray)
            .border(
                width = 1.dp,
                color = PrimaryGray,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp, 24.dp, 20.dp, 20.dp)
    ) {
        Status()

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Название",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Black,
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Место",
            fontSize = 14.sp,
            color = Black,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(16.dp))

        DateAndTime()

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppButton(
                text = "Принять",
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                onClick = { }
            )
            AppButton(
                text = "Принять",
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                activeColors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = Black
                ),
            )
        }
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

@Composable
private fun DateAndTime() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_date),
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = Black
            )
            Text(
                text = "Дата",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Black,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_time),
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = Black
            )
            Text(
                text = "Время",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Black,
            )
        }
    }
}
