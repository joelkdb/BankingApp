package fr.utbm.bindoomobile.ui.components.decoration

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.utbm.bindoomobile.ui.theme.BindooMobileTheme

@Composable
fun DecorationRectangle(
    modifier: Modifier,
    strokeWidth: Dp = 16.dp
) {
    Box(
        modifier = modifier.then(
            Modifier.border(
                width = strokeWidth,
                shape = RectangleShape,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0x7AFFFFFF), Color(0x00FFFFFF)),
                )
            )
        )
    )
}

@Composable
fun DecorationCircle(
    modifier: Modifier,
    strokeWidth: Dp = 16.dp
) {
    Box(
        modifier = modifier.then(
            Modifier.border(
                width = strokeWidth,
                shape = CircleShape,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0x7AFFFFFF), Color(0x00FFFFFF)),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            )
        )
    )
}

@Preview
@Composable
fun DecorationShapes_Preview() {
    BindooMobileTheme() {
        Column(
            Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            DecorationRectangle(
                modifier = Modifier.size(100.dp),
            )

            DecorationCircle(modifier = Modifier.size(100.dp))
        }
    }

}