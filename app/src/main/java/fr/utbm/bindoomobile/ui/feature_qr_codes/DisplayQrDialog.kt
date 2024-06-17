package fr.utbm.bindoomobile.ui.feature_qr_codes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.domain.models.feature_qr_codes.QrPurpose
import fr.utbm.bindoomobile.ui.components.DotsProgressIndicator
import fr.utbm.bindoomobile.ui.components.error.ErrorFullScreen
import fr.utbm.bindoomobile.ui.components.qr.QrCodeCard
import fr.utbm.bindoomobile.ui.core.effects.EnterScreenEffect
import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.theme.primaryFontFamily
import org.koin.androidx.compose.koinViewModel

private val QR_SIZE_FIXED = 240.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowQrDialog(
    viewModel: DisplayQrViewModel = koinViewModel(),
    qrPurpose: QrPurpose,
    onDismiss: () -> Unit = {},
    qrLabel: UiText? = null
) {
    val dialogState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val title = when (qrPurpose) {
        QrPurpose.PROFILE_CONNECTION -> UiText.StringResource(R.string.my_qr)
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = dialogState,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.wrapContentHeight().fillMaxWidth()
        ) {
            Text(
                text = title.asString(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF262626),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.padding(56.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DotsProgressIndicator()
                    }
                }

                state.qrString != null -> {
                    QrCodeCard(
                        qr = state.qrString,
                        label = qrLabel,
                        modifier = Modifier.width(QR_SIZE_FIXED)
                    )
                }

                state.error != null -> {
                    ErrorFullScreen(
                        error = state.error,
                        onRetry = {
                            viewModel.emitIntent(DisplayQrIntent.GenerateQr(qrPurpose))
                        },
                    )

                    LaunchedEffect(Unit) {
                        dialogState.expand()
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }

    EnterScreenEffect {
        viewModel.emitIntent(DisplayQrIntent.GenerateQr(qrPurpose))
    }
}