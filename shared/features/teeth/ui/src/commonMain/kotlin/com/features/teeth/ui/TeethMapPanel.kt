package com.features.teeth.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import com.features.teeth.domain.model.Tooth
import com.features.teeth.domain.model.ToothStatus
import com.features.teeth.ui.model.rememberCaninePath
import com.features.teeth.ui.model.rememberIncisor1Path
import com.features.teeth.ui.model.rememberIncisor2Path
import com.features.teeth.ui.model.rememberMolar1Path
import com.features.teeth.ui.model.rememberMolar2Path
import com.features.teeth.ui.model.rememberPremolar1Path
import com.features.teeth.ui.model.rememberPremolar2Path
import com.features.teeth.ui.model.rememberWisdomPath
import com.features.ui.theme.MainTheme
import kotlin.collections.mutableMapOf

@Composable
fun TeethMapPanel(
    teeth: List<Tooth>,
    selectedToothId: Int?,
    onToothClick: (Int?) -> Unit
) {
    val incisor1Path: Path = rememberIncisor1Path()
    val incisor2Path: Path = rememberIncisor2Path()
    val caninePath: Path = rememberCaninePath()
    val premolar1Path: Path = rememberPremolar1Path()
    val premolar2Path: Path = rememberPremolar2Path()
    val molar1Path: Path = rememberMolar1Path()
    val molar2Path: Path = rememberMolar2Path()
    val wisdomPath: Path = rememberWisdomPath()

    val teethDrawPaths = remember { mutableMapOf<Int, Path>() }

    val strokeColor: Color = MainTheme.colors.toothStroke

    val selectedColor = MainTheme.colors.toothSelected
    val problematicColor = MainTheme.colors.toothProblematic
    val missingColor = MainTheme.colors.toothMissing
    val defaultColor = MainTheme.colors.white

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(0.75f)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->

                        val clickedToothEntry = teethDrawPaths.entries.find { entry ->
                            val contains = entry.value.contains(offset)
                            contains
                        }

                        val clickedToothId = clickedToothEntry?.key
                        onToothClick(clickedToothId)
                    }
                )
            }
    ) {
        teethDrawPaths.clear()

        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val scaleFactor = 2.4f

        val globalOffsetX = centerX - 29f
        val globalOffsetY = centerY - 220f

        val incisor1Width = incisor1Path.getBounds().width - 2f
        val incisor1Height = incisor1Path.getBounds().height
        val incisor2Width = incisor2Path.getBounds().width
        val incisor2Height = incisor2Path.getBounds().height
        val canineWidth = caninePath.getBounds().width
        val premolar1Height = premolar1Path.getBounds().height - 4f
        val premolar2Height = premolar2Path.getBounds().height
        val molar1Height = molar1Path.getBounds().height
        val molar2Height = molar2Path.getBounds().height




        fun DrawScope.drawQuarter(quarterId: Int) {



            fun DrawScope.drawAndSaveSingleTooth(id: Int, path: Path, x: Float, y: Float) {

                val m = Matrix()

                m.translate(centerX, centerY)
                m.scale(scaleFactor, scaleFactor)
                m.translate(-centerX, -centerY)

                val q = id / 10
                m.translate(centerX, centerY)
                when(q) {
                    1 -> {}
                    2 -> m.scale(-1f, 1f)
                    3 -> m.scale(-1f, -1f)
                    4 -> m.scale(1f, -1f)
                }
                m.translate(-centerX, -centerY)


                m.translate(globalOffsetX + x, globalOffsetY + y) // перемещаем на координаты зуба


                val finalPath = Path().apply {
                    addPath(path)
                    transform(m)
                }


                // сохраняем ID зуба связанным с его Path
                teethDrawPaths[id] = finalPath
                val toothData = teeth.find { it.id == id }

                val isSelected = id == selectedToothId
                val fillColor = when {
                    isSelected -> selectedColor
                    toothData?.status == ToothStatus.PROBLEMATIC -> problematicColor
                    toothData?.status == ToothStatus.MISSING -> missingColor
                    else -> defaultColor
                }

                drawPath(path = path, color = fillColor) // fill
                drawPath(path = path, style = Stroke(width = 1f), color = strokeColor) // stroke
            }



            // incisor 1
            val x1 = 0f
            val y1 = 0f
            translate(left = 0f, top = 0f) {
                drawAndSaveSingleTooth(id = quarterId + 1, path = incisor1Path, x = x1, y = y1)
            }

            // incisor 2
            val x2 = -incisor1Width
            val y2 = incisor1Height / 5f
            translate(left = x2, top = y2) {
                drawAndSaveSingleTooth(id = quarterId + 2, path = incisor2Path, x = x2, y = y2)
            }

            // canine
            val x3 = -(incisor1Width + incisor2Width)
            val y3 = incisor1Height / 2f
            translate(left = x3, top = y3) {
                drawAndSaveSingleTooth(id = quarterId + 3, path = caninePath, x = x3, y = y3)
            }

            // premolar 1
            val x4 = -(incisor1Width + incisor2Width + canineWidth / 2f)
            val y4 = (incisor1Height + incisor2Height / 3.1f)
            translate(
                left = x4,
                top = y4
            ) {
                drawAndSaveSingleTooth(id = quarterId + 4, path = premolar1Path, x = x4, y = y4)
            }

            // premolar 2
            val x5 = -(incisor1Width + incisor2Width + canineWidth / 1.2f)
            val y5 = incisor1Height + premolar1Height * 1.45f
            translate(
                left = x5,
                top = y5
            ) {
                drawAndSaveSingleTooth(id = quarterId + 5, path = premolar2Path, x = x5, y = y5)
            }

            // molar 1
            val x6 = -(incisor1Width + incisor2Width + canineWidth * 1.1f)
            val y6 = incisor1Height + premolar1Height + premolar2Height * 1.25f
            translate(
                left = x6,
                top = y6
            ) {
                drawAndSaveSingleTooth(id = quarterId + 6, path = molar1Path, x = x6, y = y6)
            }

            // molar 2
            val x7 = -(incisor1Width + incisor2Width + canineWidth + 7f)
            val y7 = incisor1Height + premolar1Height + premolar2Height + molar1Height * 1.175f
            translate(
                left = x7,
                top = y7
            ) {
                drawAndSaveSingleTooth(id = quarterId + 7, path = molar2Path, x = x7, y = y7)
            }

            // wisdom
            val x8 = -(incisor1Width + incisor2Width + canineWidth * 1.4f)
            val y8 = incisor1Height + premolar1Height + premolar2Height + molar1Height * 1.15f + molar2Height
            translate(
                left = x8,
                top = y8
            ) {
                drawAndSaveSingleTooth(id = quarterId + 8, path = wisdomPath, x = x8, y = y8)
            }

        }



        scale(scale = scaleFactor) {

            // top left
            translate(left = globalOffsetX, top = globalOffsetY)
            {
                drawQuarter(quarterId = 10)
            }

            // top right
            scale(scaleX = -1f, scaleY = 1f) {
                translate(left = globalOffsetX, top = globalOffsetY) {
                    drawQuarter(quarterId = 20)
                }
            }

            // bottom left
            scale(scaleX = 1f, scaleY = -1f) {
                translate(left = globalOffsetX, top = globalOffsetY) {
                    drawQuarter(quarterId = 40)
                }
            }

            // bottom right
            scale(scaleX = -1f, scaleY = -1f) {
                translate(left = globalOffsetX, top = globalOffsetY) {
                    drawQuarter(quarterId = 30)
                }
            }

        }

        /*
        // рисуем квадраты там, где есть teethDrawPaths...
        teethDrawPaths.forEach { (id, path) ->
            val b = path.getBounds()
            drawRect(
                color = Color.Red.copy(alpha = 0.4f),
                topLeft = Offset(b.left, b.top),
                size = b.size,
                style = Stroke(width = 1f)
            )
        }*/

    }
}


fun Path.contains(offset: Offset): Boolean {
    val bounds = this.getBounds()

    if (!bounds.contains(offset)) return false

    return bounds.contains(offset)
}