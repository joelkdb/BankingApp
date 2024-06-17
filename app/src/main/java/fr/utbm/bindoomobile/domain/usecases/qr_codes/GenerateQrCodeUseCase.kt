package fr.utbm.bindoomobile.domain.usecases.qr_codes

import fr.utbm.bindoomobile.domain.models.feature_qr_codes.QrPurpose
import kotlinx.coroutines.delay

class GenerateQrCodeUseCase {
    suspend fun execute(qrPurpose: QrPurpose): String {
        delay(300L)
        return when (qrPurpose) {
            // Handle in repositories later
            QrPurpose.PROFILE_CONNECTION -> "soklibou.kadaba@utbm.fr:addcontact"
        }
    }
}