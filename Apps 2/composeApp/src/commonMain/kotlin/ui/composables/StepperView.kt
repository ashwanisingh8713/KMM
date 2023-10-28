package ui.composables

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Stepper_n(
    numberOfSteps: Int,
    currentStep: Int,
    stepDescriptionList: List<String> = List(numberOfSteps) { "" },
    stepValueList: List<Int> = List(numberOfSteps) { 20 },
    unSelectedColor: Color = Color.LightGray,
    selectedColor: Color? = null,
    isRainbow: Boolean = false,
    step1Click: (Int) -> Unit = {},
    step2Click: (Int) -> Unit = {},
    step3Click: (Int) -> Unit = {},
    step4Click: (Int) -> Unit = {}
) {

    val descriptionList = MutableList(numberOfSteps) { "" }
    val valueList = MutableList(numberOfSteps) { 20 }

    stepDescriptionList.forEachIndexed { index, element ->
        if (index < numberOfSteps)
            descriptionList[index] = element
    }

    stepValueList.forEachIndexed { index, element ->
        if (index < numberOfSteps)
            valueList[index] = element
    }
    val borderStroke = BorderStroke(2.dp, Color.Gray)
        Box(
            modifier = Modifier.fillMaxSize().padding(top = 60.dp, start = 10.dp, end = 10.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                border = borderStroke,
                color = Color.White,
            ) {
                Row(
                    modifier = Modifier.padding(start = 50.dp, top = 10.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Step_n(
                        modifier = Modifier.weight(1F),
                        step = 1,
                        isCompete = 1 < currentStep,
                        isCurrent = 1 == currentStep,
                        isComplete = 1 == numberOfSteps,
                        isRainbow = isRainbow,
                        stepDescription = descriptionList[1 - 1],
                        stepValue = valueList[1 - 1],
                        unSelectedColor = unSelectedColor,
                        selectedColor = selectedColor,
                        stepClick = step1Click
                    )
                    Step_n(
                        modifier = Modifier.weight(1F),
                        step = 2,
                        isCompete = 2 < currentStep,
                        isCurrent = 2 == currentStep,
                        isComplete = 2 == numberOfSteps,
                        isRainbow = isRainbow,
                        stepDescription = descriptionList[2 - 1],
                        stepValue = valueList[2 - 1],
                        unSelectedColor = unSelectedColor,
                        selectedColor = selectedColor,
                        stepClick = step2Click
                    )
                    Step_n(
                        modifier = Modifier.weight(1F),
                        step = 3,
                        isCompete = 3 < currentStep,
                        isCurrent = 3 == currentStep,
                        isComplete = 3 == numberOfSteps,
                        isRainbow = isRainbow,
                        stepDescription = descriptionList[3 - 1],
                        stepValue = valueList[3 - 1],
                        unSelectedColor = unSelectedColor,
                        selectedColor = selectedColor,
                        stepClick = step3Click
                    )
                    Step_n(
                        modifier = Modifier.weight(1F),
                        step = 4,
                        isCompete = 4 < currentStep,
                        isCurrent = 4 == currentStep,
                        isComplete = 4 == numberOfSteps,
                        isRainbow = isRainbow,
                        stepDescription = descriptionList[4 - 1],
                        stepValue = valueList[4 - 1],
                        unSelectedColor = unSelectedColor,
                        selectedColor = selectedColor,
                        stepClick = step4Click
                    )
                }
            }
    }
}

@Composable
private fun Step_n(
    modifier: Modifier = Modifier,
    step: Int,
    isCompete: Boolean,
    isCurrent: Boolean,
    isComplete: Boolean,
    isRainbow: Boolean,
    stepDescription: String,
    stepValue: Int,
    unSelectedColor: Color,
    selectedColor: Color?,
    stepClick: (Int) -> Unit = {}
) {


//    val transition = updateTransition(isCompete, label = "")
    val transition = updateTransition(isCurrent, label = "")

    val innerCircleColor by transition.animateColor(label = "innerCircleColor") {
        if (it) selectedColor ?: MaterialTheme.colors.primary else unSelectedColor
    }
    val txtColor by transition.animateColor(label = "txtColor")
    { if (it || isCurrent) selectedColor ?: MaterialTheme.colors.primary else unSelectedColor }

    val color by transition.animateColor(label = "color")
    { if (it || isCurrent) selectedColor ?: MaterialTheme.colors.primary else Color.Gray }

    val borderStroke: BorderStroke = BorderStroke(2.dp, color)

    val textSize by remember { mutableStateOf(12.sp) }

    Row(modifier = modifier) {
        val circleSize = 30.dp
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Surface(
                shape = CircleShape,
                border = borderStroke,
                color = innerCircleColor,
                modifier = Modifier.size(circleSize).clickable { stepClick(stepValue) }

            ) {

                Box(contentAlignment = Alignment.Center) {
                    if (isCurrent)
                        Icon(
                            imageVector = Icons.Default.Done, "done",
                            modifier = modifier.padding(4.dp),
                            tint = Color.White
                        )
                    else
                        Text(
                            text = "", // step.toString
                            color = color,
                            fontSize = 9.sp
                        )
                }
            }

            Text(
                modifier = Modifier.padding(3.dp).align(alignment = Alignment.CenterHorizontally),
                fontSize = textSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = stepDescription,
                color = txtColor,
            )
        }

        if (!isComplete) {
            //Line
            Divider(
                modifier = Modifier.padding(top = circleSize / 2),
                color = unSelectedColor,
                thickness = 1.dp,
            )
        }
    }
}