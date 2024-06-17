package fr.utbm.bindoomobile.ui.feature_qr_codes.scanned_contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import fr.utbm.bindoomobile.domain.core.OperationResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScannedContactViewModel(
    //private val loadUserFromQrCodeUseCase: LoadUserFromQrCodeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ScannedContactScreenState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: ScannedContactIntent) {
        when (intent) {
            is ScannedContactIntent.LoadContact -> {
                _state.update {
                    it.copy(
                        isContactLoading = true,
                        isLoading = false,
                        error = null
                    )
                }

                viewModelScope.launch {
                    /*TODO : SEARCH USER*/
                }
            }

            is ScannedContactIntent.AddContact -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }

                    delay(1000)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            addContactResEvent = triggered(OperationResult.Success(Unit))
                        )
                    }
                }
            }
        }
    }

    fun consumeContactAddedEvent() {
        _state.update {
            it.copy(
                addContactResEvent = consumed()
            )
        }
    }
}