package fr.utbm.bindoomobile.ui.feature_qr_codes.scanned_contact

sealed class ScannedContactIntent {
    data class LoadContact(val qrCode: String): ScannedContactIntent()

    object AddContact: ScannedContactIntent()
}
