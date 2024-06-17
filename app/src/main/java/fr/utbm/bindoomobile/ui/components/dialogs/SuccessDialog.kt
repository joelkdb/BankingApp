package fr.utbm.bindoomobile.ui.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import fr.utbm.bindoomobile.ui.components.PrimaryButton
import fr.utbm.bindoomobile.ui.components.ScreenPreview
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.theme.BindooMobileTheme
import fr.utbm.bindoomobile.ui.theme.primaryFontFamily

@Composable
fun SuccessDialog(
    title: UiText,
    message: UiText,
    onDismiss: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = {
            onDismiss.invoke()
        }) {
            Column(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.background
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(R.drawable.img_success),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = title.asString(),  style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF333333),
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = message.asString(), style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF999999),
                        textAlign = TextAlign.Center,
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    onClick = {
                        onDismiss.invoke()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.continue_msg)
                )
            }
    }
}

@Composable
@Preview
fun SuccessDialog_Preview() {
    BindooMobileTheme {
        ScreenPreview {
            SuccessDialog(
                title = UiText.DynamicString("Top Up Successfully"),
                message = UiText.DynamicString("The amount  will be reflected in your account with in few minutes")
            )
        }
    }
}