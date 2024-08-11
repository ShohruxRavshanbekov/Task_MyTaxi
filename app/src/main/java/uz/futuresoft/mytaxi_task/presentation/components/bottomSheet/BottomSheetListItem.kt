package uz.futuresoft.mytaxi_task.presentation.components.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.futuresoft.mytaxi_task.R
import uz.futuresoft.mytaxi_task.presentation.components.HorizontalSpace
import uz.futuresoft.mytaxi_task.presentation.ui.theme.IconTintColorLight2
import uz.futuresoft.mytaxi_task.presentation.ui.theme.IconTintColorLight3
import uz.futuresoft.mytaxi_task.presentation.ui.theme.TextColorLight1
import uz.futuresoft.mytaxi_task.presentation.ui.theme.TextColorLight2
import uz.futuresoft.mytaxi_task.presentation.ui.theme.font.latoFontFamily

@Composable
fun BottomSheetListItem(
    icon: Painter,
    iconTint: Color = IconTintColorLight2,
    title: String = "",
    titleTextColor: Color = TextColorLight1,
    titleFontSize: TextUnit = 18.sp,
    titleFontWeight: FontWeight = FontWeight.SemiBold,
    fontFamily: FontFamily = latoFontFamily,
    endIcon: Painter = painterResource(id = R.drawable.ic_arrow_right),
    endIconTint: Color = IconTintColorLight3,
    countText: String = "",
    countTextColor: Color = TextColorLight2,
    countTextFontSize: TextUnit = 18.sp,
    countTextFontWeight: FontWeight = FontWeight.SemiBold,
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .padding(top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = icon,
                contentDescription = "icon",
                tint = iconTint
            )
            HorizontalSpace(width = 8.dp)
            Text(
                text = title,
                color = titleTextColor,
                fontSize = titleFontSize,
                fontFamily = fontFamily,
                fontWeight = titleFontWeight
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = countText,
                textAlign = TextAlign.End,
                color = countTextColor,
                fontSize = countTextFontSize,
                fontFamily = fontFamily,
                fontWeight = countTextFontWeight
            )
            HorizontalSpace(width = 2.dp)
            Icon(
                painter = endIcon,
                contentDescription = "endIcon",
                tint = endIconTint
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetListItemPreview() {
    BottomSheetListItem(
        icon = painterResource(id = R.drawable.ic_switch),
        title = "Tarif",
        countText = "6 / 8"
    )
}