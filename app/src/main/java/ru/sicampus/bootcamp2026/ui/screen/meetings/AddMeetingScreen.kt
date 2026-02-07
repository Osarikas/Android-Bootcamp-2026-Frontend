package ru.sicampus.bootcamp2026.ui.screen.meetings

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.sicampus.bootcamp2026.R
import ru.sicampus.bootcamp2026.components.AppButton
import ru.sicampus.bootcamp2026.components.AppTopBar
import ru.sicampus.bootcamp2026.components.InputField
import ru.sicampus.bootcamp2026.components.UserItem1
import ru.sicampus.bootcamp2026.ui.theme.BgGradientBottom
import ru.sicampus.bootcamp2026.ui.theme.BgGradientTop
import ru.sicampus.bootcamp2026.ui.theme.White

@Preview
@Composable
fun AddMeetingScreen(
) {
    val scrollState = rememberScrollState()
    val lazyListState = rememberLazyListState()

    val nestedScrollConnection = rememberNestedScrollConnection(
        scrollState = scrollState,
        lazyListState = lazyListState
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(White)
            .nestedScroll(nestedScrollConnection)
    ) {
        AppTopBar(
            title = "Новая встреча",
            startIconId =  R.drawable.ic_back,
            startIconButtonOnClick = {},
            endIconId = R.drawable.ic_reload,
            endIconButtonOnClick = {}
        )

        MainScrollableContent(
            scrollState = scrollState,
            lazyListState = lazyListState
        )

        AddMeetingFooter(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
@Composable
private fun AddMeetingFooter(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(116.dp)
            .background(brush = BgGradientBottom)
            .padding(top = 16.dp)
            .clickable(
                interactionSource = remember() { MutableInteractionSource() },
                indication = null
            ) { }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        AppButton(
            text = "Создать",
            onClick = {},
            enabled = true
        )
    }
}

@Composable
private fun rememberNestedScrollConnection(
    scrollState: ScrollState,
    lazyListState: LazyListState
): NestedScrollConnection {
    return remember(scrollState, lazyListState) {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y

                if (delta < 0) {
                    val columnCanScroll = scrollState.value < scrollState.maxValue

                    if (columnCanScroll) {
                        val consumed = scrollState.dispatchRawDelta(-delta)
                        return Offset(0f, -consumed)
                    } else {
                        val consumed = lazyListState.dispatchRawDelta(-delta)
                        return Offset(0f, -consumed)
                    }
                }

                if (delta > 0) {
                    val lazyCanScrollUp =
                        lazyListState.firstVisibleItemIndex > 0 ||
                                lazyListState.firstVisibleItemScrollOffset > 0

                    if (lazyCanScrollUp) {
                        val consumed = lazyListState.dispatchRawDelta(-delta)
                        return Offset(0f, -consumed)
                    } else {
                        val consumed = scrollState.dispatchRawDelta(-delta)
                        return Offset(0f, -consumed)
                    }
                }

                return Offset.Zero
            }
        }
    }
}

@Composable
private fun MainScrollableContent(
    scrollState: ScrollState,
    lazyListState: LazyListState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(112.dp))

        ContentAboveLastItem()

        LastItemContainer(lazyListState = lazyListState)
    }
}

@Composable
private fun ContentAboveLastItem() {
    InputField(
        title = "Название",
        value = "",
        onValueChange = { },
        placeholderText = "Как бы Вы назвали встречу?",
    )
    InputField(
        title = "Место",
        value = "",
        onValueChange = { },
        placeholderText = "Где будет проходить встреча?",
    )
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            InputField(
                title = "Дата",
                value = "",
                onValueChange = { },
                iconId = R.drawable.ic_date
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier.weight(1f)
        ) {
            InputField(
                title = "Время",
                value = "",
                onValueChange = { },
                iconId = R.drawable.ic_time
            )
        }
    }
}

@Composable
private fun LastItemContainer(
    lazyListState: LazyListState
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val appTopBarHeight = 132.dp
    val lazyHeight = screenHeight - appTopBarHeight

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LastItemContainerTopBar()

        LazyColumn (
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = lazyHeight)
        ) {
            item {
                Spacer(modifier = Modifier.height(92.dp))
            }

            items(100) {
                UserItem1() // временные элементы
            }

            item {
                Spacer(modifier = Modifier.height(116.dp))
            }
        }
    }
}

@Composable
private fun LastItemContainerTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = BgGradientTop)
            .padding(bottom = 8.dp)
            .zIndex(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { }
    ) {
        // временный поиск
        InputField(
            title = "Участники",
            value = "",
            onValueChange = { },
            placeholderText = "Кого бы Вы позвали на встречу?"
        )
        // временный поиск
    }
}