package com.features.teeth.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.features.teeth.domain.model.Tooth
import com.features.teeth.domain.model.ToothStatus
import com.features.teeth.ui.utils.getToothTypeStringResource
import com.features.ui.Res
import com.features.ui.button.MainButton
import com.features.ui.healthy
import com.features.ui.ic_cross
import com.features.ui.ic_radio_off
import com.features.ui.ic_radio_on
import com.features.ui.missing
import com.features.ui.notes
import com.features.ui.notesHint
import com.features.ui.problematic
import com.features.ui.save
import com.features.ui.status
import com.features.ui.textInput.NotesTextField
import com.features.ui.theme.MainTheme
import com.features.ui.tooth
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ToothInfoEditDialog(
    tooth: Tooth?,
    onDismiss: () -> Unit,
    onConfirm: (ToothStatus, String) -> Unit,
) {
    if (tooth == null) return
    var noteText by remember { mutableStateOf(tooth.note ?: "") }
    var curStatus by remember { mutableStateOf(tooth.status) }

    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Column(
            modifier =
                Modifier
                    .background(
                        color = MainTheme.colors.white,
                        shape = RoundedCornerShape(size = 40.dp),
                    ).padding(24.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(Res.string.tooth, tooth.id),
                        style = MainTheme.typography.teeth.dialogHeader,
                        color = MainTheme.colors.secondary,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = stringResource(tooth.getToothTypeStringResource()).replaceFirstChar { it.uppercase() },
                        style = MainTheme.typography.teeth.dialogSubheader,
                        color = MainTheme.colors.secondary,
                        textAlign = TextAlign.Center,
                    )
                }

                Icon(
                    modifier =
                        Modifier
                            .align(Alignment.CenterEnd)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { onDismiss() },
                            ).padding(end = 10.dp),
                    painter = painterResource(Res.drawable.ic_cross),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                text = stringResource(Res.string.status),
                style = MainTheme.typography.teeth.dialogSmallHeader,
                color = MainTheme.colors.gray,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                ToothStatus.entries.forEach { status ->
                    val isSelected = status == curStatus

                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        curStatus = status
                                    },
                                ).padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter =
                                painterResource(
                                    if (isSelected) Res.drawable.ic_radio_on else Res.drawable.ic_radio_off,
                                ),
                            contentDescription = null,
                            modifier =
                                Modifier
                                    .size(20.dp),
                            tint = Color.Unspecified,
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = stringResource(getStatusStringResource(status)),
                            style = MainTheme.typography.teeth.radioListItem,
                            color = MainTheme.colors.darkGray,
                        )
                    }
                }
            }

            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 6.dp),
                text = stringResource(Res.string.notes),
                style = MainTheme.typography.teeth.dialogSmallHeader,
                color = MainTheme.colors.gray,
                textAlign = TextAlign.Center,
            )

            NotesTextField(
                text = noteText,
                onTextChange = { noteText = it },
                hintText = stringResource(Res.string.notesHint),
                textAndFocusedBorderColor = MainTheme.colors.secondary,
            )

            Spacer(modifier = Modifier.height(10.dp))

            val isChanged = curStatus != tooth.status || noteText != (tooth.note ?: "")

            MainButton(
                modifier =
                    Modifier
                        .width(86.dp)
                        .height(40.dp)
                        .align(Alignment.CenterHorizontally),
                text = stringResource(Res.string.save),
                textStyle = MainTheme.typography.teeth.buttonText,
                contentColor = MainTheme.colors.secondary,
                backgroundColor = MainTheme.colors.button,
                disabledColor = MainTheme.colors.disabled,
                disabledContentColor = MainTheme.colors.disabledContent,
                isEnabled = isChanged,
                onClick = {
                    onConfirm(curStatus, noteText)
                },
            )
        }
    }
}

private fun getStatusStringResource(status: ToothStatus): StringResource =
    when (status) {
        ToothStatus.HEALTHY -> Res.string.healthy
        ToothStatus.PROBLEMATIC -> Res.string.problematic
        ToothStatus.MISSING -> Res.string.missing
    }
