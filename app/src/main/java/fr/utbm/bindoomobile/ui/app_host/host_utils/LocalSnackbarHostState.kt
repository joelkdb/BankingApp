package fr.utbm.bindoomobile.ui.app_host.host_utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import fr.utbm.bindoomobile.ui.components.snackbar.SnackBarMode
import fr.utbm.bindoomobile.ui.components.snackbar.showResultSnackBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class ScopedSnackBarState(
    private val value: SnackbarHostState,
    private val coroutineScope: CoroutineScope,
) {
    fun show(message: String, snackBarMode: SnackBarMode = SnackBarMode.Neutral) {
        coroutineScope.launch {
            value.showResultSnackBar(message, snackBarMode)
        }
    }

    fun show(context: Context, @StringRes message: Int, snackBarMode: SnackBarMode = SnackBarMode.Neutral) {
        coroutineScope.launch {
            value.showResultSnackBar(context.getString(message), snackBarMode)
        }
    }
}

val LocalScopedSnackbarState = compositionLocalOf<ScopedSnackBarState> { error("No ScopedSnackBarState provided") }