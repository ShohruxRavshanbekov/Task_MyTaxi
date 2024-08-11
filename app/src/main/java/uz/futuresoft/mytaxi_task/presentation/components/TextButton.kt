package uz.futuresoft.mytaxi_task.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.futuresoft.mytaxi_task.presentation.ui.theme.ButtonBackgroundColorLight
import uz.futuresoft.mytaxi_task.presentation.ui.theme.ButtonBorderColorLight
import uz.futuresoft.mytaxi_task.presentation.ui.theme.Green
import uz.futuresoft.mytaxi_task.presentation.ui.theme.TextColorLight1
import uz.futuresoft.mytaxi_task.presentation.ui.theme.font.latoFontFamily

@Composable
fun TextButton(
    size: Dp = 56.dp,
    shape: RoundedCornerShape = RoundedCornerShape(14.dp),
    cardColor: Color = ButtonBackgroundColorLight,
    borderWidth: Dp = 0.dp,
    borderColor: Color = ButtonBorderColorLight,
    text: String,
    textColor: Color = TextColorLight1,
    fontSize: TextUnit = 20.sp,
    fontFamily: FontFamily = latoFontFamily,
    fontWeight: FontWeight = FontWeight.Bold,
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
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = fontSize,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )
        }
    }
}

@Preview
@Composable
fun TextButtonPreview() {
    TextButton(
        cardColor = Green,
        borderWidth = 4.dp,
        text = "95",
        onClick = {}
    )
}