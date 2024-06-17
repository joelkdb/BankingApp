package fr.utbm.bindoomobile.ui.feature_qr_codes

import fr.utbm.bindoomobile.ui.core.resources.UiText

data class DisplayQrState(
    val isLoading: Boolean = false,
    val qrString: String? = null,
    val error: UiText? = null,
)
