package fr.utbm.bindoomobile.ui.feature_transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.data.datasource.local.dao.AgencyDao
import fr.utbm.bindoomobile.data.datasource.local.dao.SFDDao
import fr.utbm.bindoomobile.data.datasource.local.entities.AccountEntity
import fr.utbm.bindoomobile.domain.core.OperationResult
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.domain.usecases.account.GetAccountByIdUseCase
import fr.utbm.bindoomobile.domain.usecases.account.GetDefaultAccountUseCase
import fr.utbm.bindoomobile.domain.usecases.account.GetRecipientByAccountUseCase
import fr.utbm.bindoomobile.domain.usecases.account.SendMoneyUseCase
import fr.utbm.bindoomobile.ui.core.error.asUiTextError
import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_cards.screen_add_card.UiField
import fr.utbm.bindoomobile.ui.feature_transfer.model.AccountUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SendMoneyViewModel(
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val getDefaultAccountUseCase: GetDefaultAccountUseCase,
    private val getRecipientByAccountUseCase: GetRecipientByAccountUseCase,
    private val sendMoneyUseCase: SendMoneyUseCase,
    private val agencyDao: AgencyDao,
    private val sfdDao: SFDDao
) : ViewModel() {
    private val _state = MutableStateFlow(SendMoneyScreenState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: SendMoneyScreenIntent) {
        when (intent) {
            is SendMoneyScreenIntent.EnterScreen -> {
                reduceLoadAccount(
                    accountId = intent.selectedAccountId, showErrorIfAny = false
                )

            }

            is SendMoneyScreenIntent.ChooseAccount -> {
                reduceLoadAccount(
                    accountId = intent.accountId, showErrorIfAny = true
                )
            }

            is SendMoneyScreenIntent.ToggleAccountPicker -> {
                _state.update {
                    it.copy(
                        accountPickerState = it.accountPickerState.copy(
                            showAccountPicker = intent.show
                        )
                    )
                }
            }

            is SendMoneyScreenIntent.UpdateSelectedValue -> {
                reduceChosenAmount(intent.amount)
            }

            is SendMoneyScreenIntent.ProceedClick -> {
                reduceSubmit()
            }

            is SendMoneyScreenIntent.DismissSuccessDialog -> {
                _state.update {
                    it.copy(
                        showSuccessDialog = false,
                        requiredBackNavEvent = triggered
                    )
                }
            }

            is SendMoneyScreenIntent.SearchRecipient -> {
                reduceSearchRecipient()
            }

            is SendMoneyScreenIntent.StringFieldChanged -> {
                when (intent.fieldType) {
                    SendMoneyFieldType.RECIPIENT_ACCOUNT -> _state.update {
                        it.copy(
                            recipientAccount = UiField(intent.fieldValue)
                        )
                    }

                    SendMoneyFieldType.COMMENT -> _state.update {
                        it.copy(
                            comment = UiField(intent.fieldValue)
                        )
                    }
                }
            }
        }
    }

    private fun reduceSearchRecipient() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            val account = _state.value.recipientAccount.value

            if (account.isNotEmpty()) {
                val recipientResult = OperationResult.runWrapped {
                    getRecipientByAccountUseCase.execute(account)
                }
                when (recipientResult) {
                    is OperationResult.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                recipientName = UiField(recipientResult.data)
                            )
                        }
                    }

                    is OperationResult.Failure -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = recipientResult.error.errorType.asUiTextError()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun reduceSubmit() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            val accountId = _state.value.accountPickerState.selectedAccount?.accountNumber
            val recipientId = _state.value.recipientAccount.value
            val comment = _state.value.comment.value

            if (accountId != null) {
                val sendRes = OperationResult.runWrapped {
                    sendMoneyUseCase.execute(
                        amount = _state.value.amountState.selectedAmount,
                        fromAccountId = accountId,
                        recipientId = recipientId,
                        comment = comment
                    )
                }

                when (sendRes) {
                    is OperationResult.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                showSuccessDialog = true,
                            )
                        }
                    }

                    is OperationResult.Failure -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = sendRes.error.errorType.asUiTextError()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun reduceChosenAmount(amount: MoneyAmount) {
        _state.update {
            it.copy(
                amountState = it.amountState.copy(
                    selectedAmount = amount
                )
            )
        }
    }


    private fun reduceLoadAccount(
        accountId: String?, showErrorIfAny: Boolean
    ) {
        _state.update {
            it.copy(
                accountPickerState = it.accountPickerState.copy(
                    isLoading = true
                )
            )
        }

        viewModelScope.launch {
            val accountResult = if (accountId == null) {
                OperationResult.runWrapped {
                    getDefaultAccountUseCase.execute()
                }
            } else {
                OperationResult.runWrapped {
                    getAccountByIdUseCase.execute(accountId)
                }
            }

            when (accountResult) {
                is OperationResult.Success -> {
                    if (accountResult.data != null) {
                        val agency = agencyDao.getAgencyById(accountResult.data.agencyId)
                        val sfd = agency?.let { sfdDao.getSFDById(it.sfdId) }
                        _state.update {
                            it.copy(
                                accountPickerState = it.accountPickerState.copy(
                                    selectedAccount = AccountUi.mapFromDomain(
                                        accountResult.data,
                                        bankName = sfd!!.label
                                    ),

                                    isLoading = false
                                )
                            )
                        }

                        reduceAmountPickersState(account = accountResult.data)
                    } else {
                        _state.update {
                            it.copy(
                                accountPickerState = it.accountPickerState.copy(
                                    selectedAccount = null, isLoading = false
                                )
                            )
                        }

                        reduceAmountPickersState(account = null)
                    }
                }

                is OperationResult.Failure -> {
                    _state.update {
                        it.copy(
                            accountPickerState = it.accountPickerState.copy(
                                selectedAccount = null,
                                isLoading = false,
                            )
                        )
                    }

                    if (showErrorIfAny) {
                        _state.update {
                            it.copy(
                                accountPickerState = it.accountPickerState.copy(
                                    accountSelectErrorEvent = triggered(accountResult.error.errorType)
                                )
                            )
                        }
                    }

                    reduceAmountPickersState(account = null)
                }
            }
        }
    }

    private fun reduceAmountPickersState(
        account: AccountEntity?
    ) {
        val amounts = setOf(
            500, 1000, 1500, 2000, 5000, 6000, 7000, 8000, 9000, 10000, 50000
        ).map {
            MoneyAmount(it.toFloat())
        }.toSet()
        val proposedSendValues = account?.let { amounts } ?: emptySet()

        val balanceValue = account?.balance?.toFloat() ?: 0f
        val showInsufficientBalance = account != null && balanceValue == 0F

        val insufficientBalanceError = if (showInsufficientBalance) {
            UiText.StringResource(R.string.insufficient_account_balance)
        } else {
            null
        }

        val maxAmount = if (balanceValue > 0f) {
            MoneyAmount(balanceValue)
        } else {
            null
        }

        _state.update {
            it.copy(
                amountState = it.amountState.copy(
                    proposedValues = proposedSendValues,
                    selectedAmount = proposedSendValues.firstOrNull() ?: MoneyAmount(0f),
                    pickersEnabled = balanceValue > 0f,
                    error = insufficientBalanceError,
                    maxAmount = maxAmount
                )
            )
        }
    }


    fun consumeLoadAccountErrorEvent() {
        _state.update {
            it.copy(
                accountPickerState = it.accountPickerState.copy(
                    accountSelectErrorEvent = consumed()
                )
            )
        }
    }


    fun consumeBackNavEvent() {
        _state.update {
            it.copy(
                requiredBackNavEvent = consumed
            )
        }
    }
}