package com.features.teeth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.features.teeth.presentation.model.TeethEvents
import com.features.teeth.presentation.model.TeethState
import com.features.teeth.ui.mappanel.InteractiveTeethMap
import com.features.ui.Res
import com.features.ui.teethCondition
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun TeethScreenContent(
    state: TeethState,
    onEvent: (TeethEvents) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MainTheme.colors.containerBackground)
                .padding(20.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp))
                    .background(MainTheme.colors.white)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(Res.string.teethCondition),
                style = MainTheme.typography.teeth.mainScreenHeader,
                color = MainTheme.colors.secondary,
                modifier = Modifier.padding(bottom = 24.dp),
            )

            InteractiveTeethMap(
                state = state,
                onEvent = onEvent,
            )
        }
    }
}
