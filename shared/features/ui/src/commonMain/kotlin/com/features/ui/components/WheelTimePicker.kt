package com.features.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.features.ui.theme.MainTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlin.math.abs

@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    initialIndex: Int,
    onItemSelected: (Int) -> Unit,
) {
    val itemHeight = 40.dp

    val visibleItemsCount = 1

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .filter { !it }
            .map {
                val layoutInfo = listState.layoutInfo
                val center = layoutInfo.viewportEndOffset / 2
                val closestItem =
                    listState.layoutInfo.visibleItemsInfo.minByOrNull {
                        abs((it.offset + it.size / 2) - center)
                    }
                closestItem?.index ?: initialIndex
            }.distinctUntilChanged()
            .collectLatest { index ->
                listState.scrollToItem(index)
                onItemSelected(index)
            }
    }

    Box(
        modifier =
            modifier
                .height(itemHeight)
                .clipToBounds(),
        contentAlignment = Alignment.Center,
    ) {
        // Серый фон
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MainTheme.colors.timeSelectorBoxGray),
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(items) { _, item ->
                Box(
                    modifier = Modifier.height(itemHeight),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = item,
                        style = MainTheme.typography.calendarDialog.timeBoxContent,
                        color = MainTheme.colors.black,
                    )
                }
            }
        }
    }
}
