package com.features.teeth.ui.mappanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.teeth.domain.model.Tooth
import com.features.teeth.domain.model.ToothStatus
import com.features.teeth.presentation.model.TeethEvents
import com.features.teeth.presentation.model.TeethState
import com.features.teeth.ui.dialog.ToothInfoEditDialog
import com.features.teeth.ui.utils.findToothById
import com.features.teeth.ui.utils.getToothTypeStringResource
import com.features.ui.Res
import com.features.ui.button.MainButton
import com.features.ui.edit
import com.features.ui.healthy
import com.features.ui.missing
import com.features.ui.nothingIsSelected
import com.features.ui.problematic
import com.features.ui.theme.MainTheme
import com.features.ui.tooth
import org.jetbrains.compose.resources.stringResource

@Composable
fun InteractiveTeethMap(
    state: TeethState,
    onEvent: (TeethEvents) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MainTheme.colors.white)
                    .padding(16.dp),
        ) {
            TeethMapPanel(
                teeth = state.teeth,
                selectedToothId = state.selectedToothId,
                onToothClick = { id ->
                    if (id != null) {
                        onEvent(TeethEvents.OnToothClicked(id))
                    }
                },
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SelectedToothInfoPanel(
            tooth = state.teeth.findToothById(state.selectedToothId),
            onEditClick = {
                onEvent(TeethEvents.OnEditClicked)
            },
        )
    }

    if (state.isEditDialogVisible) {
        ToothInfoEditDialog(
            tooth = state.teeth.findToothById(state.selectedToothId),
            onDismiss = {
                onEvent(TeethEvents.OnDismissDialog)
            },
            onConfirm = { newStatus, newNote ->
                state.teeth.findToothById(state.selectedToothId)?.id?.let { id ->
                    onEvent(
                        TeethEvents.OnToothStatusChanged(
                            toothId = id,
                            note = newNote,
                            status = newStatus,
                        ),
                    )
                }
            },
        )
    }
}

@Composable
private fun SelectedToothInfoPanel(
    tooth: Tooth?,
    onEditClick: () -> Unit,
) {
    val toothId = tooth?.id
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (toothId != null) {
            Text(
                text =
                    stringResource(Res.string.tooth, toothId) + ", " +
                        stringResource(tooth.getToothTypeStringResource()),
                style = MainTheme.typography.teeth.toothNumber,
                color = MainTheme.colors.secondary,
            )

            Spacer(modifier = Modifier.height(24.dp))

            val descriptionText =
                if (!tooth.note.isNullOrBlank()) {
                    tooth.note.orEmpty()
                } else {
                    val statusRes =
                        when (tooth.status) {
                            ToothStatus.HEALTHY -> Res.string.healthy
                            ToothStatus.PROBLEMATIC -> Res.string.problematic
                            ToothStatus.MISSING -> Res.string.missing
                        }
                    stringResource(statusRes)
                }

            val statusColor =
                if (tooth.note.isNullOrBlank()) {
                    MainTheme.colors.lightGray
                } else {
                    MainTheme.colors.black
                }

            Text(
                text = descriptionText,
                style = MainTheme.typography.teeth.toothDescription,
                color = statusColor,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))

            MainButton(
                modifier =
                    Modifier
                        .height(32.dp)
                        .width(180.dp),
                text = stringResource(Res.string.edit),
                textStyle = MainTheme.typography.teeth.buttonText,
                contentColor = MainTheme.colors.secondary,
                backgroundColor = MainTheme.colors.button,
                onClick = onEditClick,
            )
        } else {
            Text(
                modifier =
                    Modifier
                        .padding(vertical = 20.dp),
                text = stringResource(Res.string.nothingIsSelected),
                style = MainTheme.typography.teeth.toothDescription,
                color = MainTheme.colors.gray,
                textAlign = TextAlign.Center,
            )
        }
    }
}
