package com.features.onboard.ui.components.content.inner.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.core.domain.constants.Constants
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.onboard.presentation.model.OnboardingState
import com.features.ui.Res
import com.features.ui.brushingFrequency
import com.features.ui.brushingFrequencyHint
import com.features.ui.button.MainButton
import com.features.ui.confirm
import com.features.ui.ic_close_o
import com.features.ui.ic_dropdown_list
import com.features.ui.theme.MainTheme
import com.features.ui.timesADay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingBrushingDialog(
    brushingTimes: List<String>,
    onEvent: (OnboardingEvents) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(1) }

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column (
            modifier = Modifier
                .background(
                    color = MainTheme.colors.containerBackground,
                    shape = RoundedCornerShape(size = 20.dp)
                )
                .padding(16.dp)
        ) {

            // "Период чистки зубов   (X)"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.brushingFrequency),
                    style = MainTheme.typography.onboardingBrushingDialog.brushingFrequency,
                    color = MainTheme.colors.secondary
                )

                Icon(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { onDismiss() }
                        ),
                    painter = painterResource(Res.drawable.ic_close_o),
                    contentDescription = "Cross on Brushing Dialog",
                    tint = MainTheme.colors.secondary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Column {
                // Подпись "Частота ухода"
                Text(
                    text = stringResource(Res.string.brushingFrequencyHint),
                    style = MainTheme.typography.onboardingBrushingDialog.brushingFrequencyHint,
                    color = MainTheme.colors.secondary
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Выпадающий список с надписью "Период чистки"
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, MainTheme.colors.black.copy(alpha = 0.35f), RoundedCornerShape(80.dp))
                            .clickable { expanded = true }
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedValue.toString() + stringResource(Res.string.timesADay),
                            style = MainTheme.typography.onboardingBrushingDialog.brushingFrequencyListItem
                        )
                        Icon(
                            painter = painterResource(Res.drawable.ic_dropdown_list),
                            contentDescription = "Open dropdown list",
                            tint = MainTheme.colors.secondary
                        )
                    }

                    // Выбор частоты из выпадающего списка
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        repeat(Constants.Business.MAX_BRUSHING_FREQUENCY) { index ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = index.toString() + stringResource(Res.string.timesADay),
                                        style = MainTheme.typography.onboardingBrushingDialog.brushingFrequencyListItem
                                    )
                                },
                                onClick = {
                                    selectedValue = index
                                    expanded = false
                                    onEvent(OnboardingEvents.OnBrushingDialogCountChanged(index))
                                }
                            )
                        }
                    }
                }

                // Поля с выбором времени
                brushingTimes.forEachIndexed { index, time ->
                    Spacer(modifier = Modifier.height(12.dp))
                    OnboardingTimeSelectField(
                        time = time,
                        onTimeChange = {
                            //onEvent(OnboardingEvents.OnTimeChanged(index, newTime))
                        }
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка "Подтвердить"
                MainButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.confirm),
                    onClick = {
                        onConfirm()
                    }
                )
            }


        }
    }
}