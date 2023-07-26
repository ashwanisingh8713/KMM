package theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Shapes

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(10.dp),
    extraLarge = RoundedCornerShape(14.dp)

)

val Shape = Shapes(
    small = RoundedCornerShape(Spacing.size_4),
    medium = RoundedCornerShape(Spacing.size_8),
    large = RoundedCornerShape(Spacing.size_12)
)