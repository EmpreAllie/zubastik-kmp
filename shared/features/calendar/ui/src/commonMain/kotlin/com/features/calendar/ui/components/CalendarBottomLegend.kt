package com.features.calendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.features.ui.Res
import com.features.ui.completedVisitToDoctor
import com.features.ui.plannedVisit
import com.features.ui.teethConditionMark
import com.features.ui.theme.MainTheme
import com.features.ui.toothBrushChange
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarBottomLegend() {
    Column {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(MainTheme.colors.secondary)
                    .padding(20.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                LegendItem(
                    text = stringResource(Res.string.completedVisitToDoctor),
                    color = MainTheme.colors.eventBlue,
                )

                LegendItem(
                    text = stringResource(Res.string.teethConditionMark),
                    color = MainTheme.colors.eventLightBlue,
                    isSplit = true,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                LegendItem(
                    text = stringResource(Res.string.toothBrushChange),
                    color = MainTheme.colors.eventTiffany,
                )

                LegendItem(
                    text = stringResource(Res.string.plannedVisit),
                    color = MainTheme.colors.eventRed,
                )
            }
        }
    }
}

@Composable
private fun LegendItem(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    isSplit: Boolean = false,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(5.dp)),
        ) {
            if (isSplit) {
                Column {
                    Box(
                        modifier =
                            Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(MainTheme.colors.eventLightBlue),
                    )
                    Box(
                        modifier =
                            Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(MainTheme.colors.eventOrange),
                    )
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize().background(color),
                )
            }
        }

        Box(
            modifier =
                Modifier
                    .padding(horizontal = 8.dp)
                    .width(12.dp)
                    .height(2.dp)
                    .background(MainTheme.colors.secondary),
        )

        Text(
            text = text,
            style = MainTheme.typography.calendar.legendItem,
            color = MainTheme.colors.secondary,
        )
    }
}
