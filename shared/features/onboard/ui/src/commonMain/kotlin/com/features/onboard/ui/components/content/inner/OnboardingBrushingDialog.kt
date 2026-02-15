package com.features.onboard.ui.components.content.inner

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
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.features.ui.Res
import com.features.ui.brushingFrequency
import com.features.ui.brushingFrequencyHint
import com.features.ui.ic_back_arrow
import com.features.ui.ic_close_o
import com.features.ui.ic_dropdown_list
import com.features.ui.theme.MainTheme
import kotlinx.serialization.builtins.ArraySerializer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingBrushingDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val items = (0..5).toList()
    var selectedValue by remember { mutableStateOf(items[1]) }

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column (
            modifier = Modifier
                .background(color = MainTheme.colors.containerBackground, shape = RoundedCornerShape(size = 20.dp))
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

            // Выпадающий список с надписью "Период чистки"
            Column {
                // Подпись "Частота ухода"
                Text(
                    text = stringResource(Res.string.brushingFrequencyHint),
                    style = MainTheme.typography.onboardingBrushingDialog.brushingFrequencyHint,
                    color = MainTheme.colors.secondary
                )

                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, MainTheme.colors.black, RoundedCornerShape(80.dp))
                            .clickable { expanded = true }
                            .padding(vertical = 16.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$selectedValue раз в день",
                            style = MainTheme.typography.onboardingBrushingDialog.brushingFrequencyListItem
                        )
                        Icon(
                            painter = painterResource(Res.drawable.ic_dropdown_list),
                            contentDescription = "Open dropdown list",
                            tint = MainTheme.colors.secondary
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        items.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "$item раз(а) в день",
                                        style = MainTheme.typography.onboardingBrushingDialog.brushingFrequencyListItem
                                    )
                                },
                                onClick = {
                                    selectedValue = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }


        }
    }
}