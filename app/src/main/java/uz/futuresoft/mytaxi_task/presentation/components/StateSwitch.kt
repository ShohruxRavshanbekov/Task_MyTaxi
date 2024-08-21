package uz.futuresoft.mytaxi_task.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.futuresoft.mytaxi_task.presentation.ui.theme.Black
import uz.futuresoft.mytaxi_task.presentation.ui.theme.Green
import uz.futuresoft.mytaxi_task.presentation.ui.theme.MyTaxiTaskTheme
import uz.futuresoft.mytaxi_task.presentation.ui.theme.Red
import uz.futuresoft.mytaxi_task.presentation.ui.theme.font.latoFontFamily

@Composable
fun StateSwitch(
    checked: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    parentShape: RoundedCornerShape = RoundedCornerShape(14.dp),
    toggleShape: RoundedCornerShape = RoundedCornerShape(14.dp),
    activeTextColor: Color = Black,
    inactiveTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
) {

    var switchState by remember { mutableStateOf(checked) }
    val offset by animateDpAsState(
        targetValue = if (switchState) 46.dp else (-46).dp,
        animationSpec = animationSpec,
        label = "offset"
    )

    Box(
        modifier = Modifier
            .width(192.dp)
            .height(56.dp)
            .clip(shape = parentShape)
            .clickable { switchState = !switchState }
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(92.dp)
                .height(48.dp)
                .offset(x = offset)
                .clip(shape = toggleShape)
                .background(if (switchState) Green else Red),
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(
                modifier = Modifier
                    .width(92.dp)
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Band",
                    color = if (!switchState) activeTextColor else inactiveTextColor,
                    fontSize = 18.sp,
                    fontFamily = latoFontFamily,
                    fontWeight = if (!switchState) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier
                    .width(92.dp)
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Faol",
                    color = if (switchState) activeTextColor else inactiveTextColor,
                    fontSize = 18.sp,
                    fontFamily = latoFontFamily,
                    fontWeight = if (switchState) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StateSwitchPreview() {
    MyTaxiTaskTheme {
        StateSwitch()
    }
}