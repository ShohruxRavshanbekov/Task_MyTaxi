package uz.futuresoft.mytaxi_task.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.futuresoft.mytaxi_task.R
import uz.futuresoft.mytaxi_task.presentation.ui.theme.ButtonBackgroundColorLight
import uz.futuresoft.mytaxi_task.presentation.ui.theme.ButtonBorderColorLight
import uz.futuresoft.mytaxi_task.presentation.ui.theme.IconTintColorLight2
import uz.futuresoft.mytaxi_task.presentation.ui.theme.OnButtonBackgroundColorLight

@Composable
fun IconButton(
    size: Dp = 56.dp,
    shape: RoundedCornerShape = RoundedCornerShape(14.dp),
    cardColor: Color = ButtonBackgroundColorLight,
    borderWidth: Dp = 0.dp,
    borderColor: Color = ButtonBorderColorLight,
    icon: Painter,
    iconSize: Dp = 24.dp,
    iconTint: Color = IconTintColorLight2,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.size(size),
        shape = shape,
        colors = CardColors(
            containerColor = cardColor,
            contentColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        ),
        border = BorderStroke(
            width = borderWidth,
            color = borderColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                painter = icon,
                contentDescription = "Icon",
                tint = iconTint
            )
        }
    }
}

@Preview
@Composable
fun IconButtonPreview() {
    IconButton(
        cardColor = OnButtonBackgroundColorLight.copy(alpha = 0.8F),
        borderWidth = 4.dp,
        borderColor = ButtonBorderColorLight.copy(alpha = 0.8F),
        icon = painterResource(id = R.drawable.ic_chevrons),
        onClick = {}
    )
}