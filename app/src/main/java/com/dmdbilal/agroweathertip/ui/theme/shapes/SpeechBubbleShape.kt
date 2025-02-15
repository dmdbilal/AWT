package com.dmdbilal.agroweathertip.ui.theme.shapes

import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class SpeechBubbleShape(
    private val tipSize: Dp = 15.dp,
    private val cornerRadius: Dp = 15.dp
): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val tipSize = with(density) {tipSize.toPx()}
        val cornerRadius = with(density) {cornerRadius.toPx()}
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = tipSize,
                    right = size.width,
                    bottom = size.height,
                    top = 0f,
                    radiusX = cornerRadius,
                    radiusY = cornerRadius,
                )
            )

            moveTo(
                x = tipSize + cornerRadius,
                y = 0f
            )

            lineTo(
                x = 0f,
                y = 0f
            )

            lineTo(
                x = tipSize,
                y = tipSize + cornerRadius
            )


            close()
        }

        return Outline.Generic(path = path)
    }
}
