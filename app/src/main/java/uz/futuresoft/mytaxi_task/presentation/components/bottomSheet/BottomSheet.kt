@file:OptIn(ExperimentalMaterial3Api::class)

package uz.futuresoft.mytaxi_task.presentation.components.bottomSheet

import android.content.res.Configuration
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.futuresoft.mytaxi_task.R
import uz.futuresoft.mytaxi_task.presentation.ui.theme.MyTaxiTaskTheme

@Composable
fun BottomSheet(
    sheetBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    sheetShape: RoundedCornerShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
    sheetContentBackgroundColor: Color = MaterialTheme.colorScheme.onSurface,
    sheetContentShape: RoundedCornerShape = RoundedCornerShape(size = 12.dp),
    isSheetExpanded: Boolean = false,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onVerticalDrag: (change: PointerInputChange, dragAmount: Float) -> Unit,
) {
    val bottomSheetOffset by animateDpAsState(
        targetValue = if (isSheetExpanded) 0.dp else 92.dp,
        animationSpec = animationSpec,
        label = "offset"
    )
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val dragHandlerOffset by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "dragHandlerOffset"
    )
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .offset(y = bottomSheetOffset)
            .pointerInput(key1 = Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = onVerticalDrag
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .height(5.dp)
                    .width(32.dp)
                    .background(
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(size = 6.dp)
                    )
                    .offset(y = dragHandlerOffset.dp)
            )
        }
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(
                    color = sheetBackgroundColor,
                    shape = sheetShape
                )
        ) {
            Card(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                colors = CardColors(
                    containerColor = sheetContentBackgroundColor,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                ),
                shape = sheetContentShape,
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetListItem(
                        icon = painterResource(id = R.drawable.ic_switch),
                        title = "Tarif",
                        countText = "6 / 8"
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.outline
                    )
                    BottomSheetListItem(
                        icon = painterResource(id = R.drawable.ic_order),
                        title = "Buyurtmalar",
                        countText = "0"
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.outline
                    )
                    BottomSheetListItem(
                        icon = painterResource(id = R.drawable.ic_rocket),
                        title = "Bordur",
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BottomSheetPreview() {
    MyTaxiTaskTheme {
        BottomSheet { _, _ -> }
    }
}