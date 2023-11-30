package com.aay.compose.lineChart.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.utils.formatToThousandsMillionsBillions

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.circleWithRectAndText(
    animatedProgress: Animatable<Float, AnimationVector1D>,
    textMeasure: TextMeasurer,
    info: Double,
    xInfo: String,
    stroke: Stroke,
    line: LineParameters,
    x: Dp,
    y: Double,
) {
    chartCircle(x.toPx(), y.toFloat(), line.lineColor, animatedProgress, stroke)
    chartRectangleWithText(x, y, line.lineColor, textMeasure, info, xInfo)
}


@OptIn(ExperimentalTextApi::class)
private fun DrawScope.chartRectangleWithText(
    x: Dp, y: Double, color: Color, textMeasurer: TextMeasurer, infoText: Double, xInfo: String,
) {
    val rectSize = Size(70.dp.toPx(), 40.dp.toPx())
    val rectTopLeft = Offset(
        x.toPx() - rectSize.width / 1.5.toFloat(),
        y.toFloat() - rectSize.height * 1.5.toFloat()
    )
    val rectBounds = Rect(rectTopLeft, rectSize)
    val text = "시간: $xInfo \n가격: ${infoText.toFloat().formatToThousandsMillionsBillions()}"

    val textStyle = TextStyle(fontSize = 20.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)

    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(text),
        style = textStyle
    )

    val textOffset = Offset(
        rectTopLeft.x + rectSize.width / 2 - textLayoutResult.size.width / 2,
        rectTopLeft.y + rectSize.height / 2 - textLayoutResult.size.height / 2
    )

//    drawRoundRect(
//        color = color,
//        topLeft = rectBounds.topLeft,
//        size = rectBounds.size,
//        cornerRadius = CornerRadius(16.dp.toPx()),
//        style = Stroke(width = 1.dp.toPx())
//    )

    drawContext.canvas.nativeCanvas.apply {
        drawText(
            textMeasurer = textMeasurer,
            text = text,
            style = textStyle,
            topLeft = textOffset
        )
    }

}