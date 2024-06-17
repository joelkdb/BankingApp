package fr.utbm.bindoomobile.ui.feature_qr_codes

import fr.utbm.bindoomobile.domain.models.feature_qr_codes.QrPurpose


sealed class DisplayQrIntent {
    data class GenerateQr(val qrPurpose: QrPurpose): DisplayQrIntent()
}
