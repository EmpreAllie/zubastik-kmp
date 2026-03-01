package com.features.teeth.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import com.features.teeth.ui.model.rememberIncisor1Path
import com.features.teeth.ui.model.rememberIncisor2Path
import com.features.ui.theme.MainTheme

@Composable
fun TeethMapPanel(

) {
    val incisor1Path: Path = rememberIncisor1Path()
    val incisor2Path: Path = rememberIncisor2Path()

    val strokeColor: Color = MainTheme.colors.toothStroke

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(0.75f)
    ) {

        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val scaleFactor = 2f

        val pathBounds = incisor1Path.getBounds()



        scale(scale = scaleFactor) {
            translate(left = centerX, top = centerY)
            {
                drawPath(
                    path = incisor1Path,
                    color = Color.White
                )
                drawPath(
                    path = incisor1Path,
                    style = Stroke(width = 1f),
                    color = strokeColor
                )
            }
        }


    }
}