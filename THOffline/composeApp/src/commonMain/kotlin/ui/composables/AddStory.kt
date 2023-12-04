package ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddStory(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 8.dp)
    ) {
        Box {
            CircleBackground(modifier = Modifier.size(75.dp))
            CircleBackground(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                color = Color(0xFF3b82f6),
                contentColor = MaterialTheme.colors.background,
                border = BorderStroke(2.dp, MaterialTheme.colors.background)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(16.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        LabelStory(text = "Your Story")
    }
}

@Composable
fun CircleBackground(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    color: Color = if (MaterialTheme.colors.isLight) Color(0xFFf8fafc) else MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.onBackground,
    border: BorderStroke = BorderStroke(2.dp, Color.LightGray),
    content: @Composable () -> Unit = {},
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        border = border,
    ) {
        content()
    }
}

@Composable
fun ColumnScope.LabelStory(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier
            .align(alignment = Alignment.CenterHorizontally),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.onBackground,
            fontSize = 12.sp
        )
    )
}