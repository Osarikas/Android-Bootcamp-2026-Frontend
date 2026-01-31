//package bottom_nav
//
//import android.graphics.fonts.FontStyle
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.Stable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.res.vectorResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import ru.sicampus.bootcamp2026.R
//import ru.sicampus.bootcamp2026.ui.theme.BgGradient
//import ru.sicampus.bootcamp2026.ui.theme.Black
//import ru.sicampus.bootcamp2026.ui.theme.White
//
//sealed class BottomNavItem(
//    val title: String,
//    val icon: Int,
//    val iconSelected: Int,
//    val route: String
//) {
//    object Inbox : BottomNavItem(
//        "Входящие",
//        R.drawable.frame1,
//        R.drawable.frame1on,
//        "inbox"
//    )
//
//    object Meetings : BottomNavItem(
//        "Встречи",
//        R.drawable.frame2,
//        R.drawable.frame2on,
//        "meetings"
//    )
//
//    object Profile : BottomNavItem(
//        "Профиль",
//        R.drawable.frame3,
//        R.drawable.frame3on,
//        "profile"
//    )
//}
//
//
//@Stable
//class BottomNavState(
//    val currentRoute: String = BottomNavItem.Meetings.route,
//    val onNavigate: (String) -> Unit = {}
//) {
//    val currentItem: BottomNavItem
//        get() = when (currentRoute) {
//            BottomNavItem.Inbox.route -> BottomNavItem.Inbox
//            BottomNavItem.Meetings.route -> BottomNavItem.Meetings
//            BottomNavItem.Profile.route -> BottomNavItem.Profile
//            else -> BottomNavItem.Meetings
//        }
//}
//
//@Composable
//fun BottomNavBar(
//    state: BottomNavState,
//    modifier: Modifier = Modifier
//) {
//    val BottomNavItems = listOf(
//        BottomNavItem.Inbox,
//        BottomNavItem.Meetings,
//        BottomNavItem.Profile
//    )
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .background(brush = BgGradient)
//            .padding(top = 16.dp, bottom = 24.dp, start = 38.dp, end = 38.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter)
//                .clip(RoundedCornerShape(24.dp))
//                .background(Black)
//                .padding(8.dp),
//        ) {
//            Row (
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly,
//            ) {
//                BottomNavItems.forEach { item ->
//                    BottomNavButton(
//                        item = item,
//                        isSelected = state.currentRoute == item.route,
//                        onSelected = { state.onNavigate(item.route) }
//                    )
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//private fun BottomNavButton(
//    item: BottomNavItem,
//    isSelected: Boolean,
//    onSelected: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val textColor = if (isSelected) Black else White
//    val iconColor = if (isSelected) Black else White
//
//    Box(
//        modifier = modifier
//            .height(40.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        if (isSelected) {
//            Box(
//                modifier = Modifier
//                    .size(72.dp)
//                    .align(Alignment.Center)
//                    .clip(RoundedCornerShape(20.dp))
//                    .background(White)
//            )
//        }
//        Column (
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .clickable(
//                    onClick = onSelected,
//                    indication = null,
//                    interactionSource = remember { MutableInteractionSource() }
//                )
//        ) {
//            Icon(
//                imageVector = ImageVector.vectorResource(
//                    id = if (isSelected) item.iconSelected else item.icon
//                ),
//                contentDescription = item.title,
//                tint = iconColor,
//                modifier = Modifier.size(40.dp)
//            )
//            Text(
//                text = item.title,
//                fontSize = 10.sp,
//                color = textColor,
//                textAlign = TextAlign.Center,
//                maxLines = 1
//            )
//        }
//    }
//}
//
//