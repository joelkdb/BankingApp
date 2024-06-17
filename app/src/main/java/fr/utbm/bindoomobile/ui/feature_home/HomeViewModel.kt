package fr.utbm.bindoomobile.ui.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.ui.feature_home.model.HomeIntent
import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.usecases.account.GetCardBalanceObservableUseCase
import fr.utbm.bindoomobile.domain.usecases.account.GetMainAccountUseCase
import fr.utbm.bindoomobile.domain.usecases.account.GetTotalAccountBalanceUseCase
import fr.utbm.bindoomobile.domain.usecases.cards.GetHomeCardsUseCase
import fr.utbm.bindoomobile.domain.usecases.profile.GetCompactProfileUseCase
import fr.utbm.bindoomobile.ui.core.error.asUiTextError
import fr.utbm.bindoomobile.ui.feature_account.MoneyAmountUi
import fr.utbm.bindoomobile.ui.feature_cards.model.CardUi
import fr.utbm.bindoomobile.ui.feature_home.model.HomeState
import fr.utbm.bindoomobile.ui.feature_profile.model.ProfileUi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCompactProfileUseCase: GetCompactProfileUseCase,
    private val getHomeCardsUseCase: GetHomeCardsUseCase,
    private val getTotalAccountBalanceUseCase: GetTotalAccountBalanceUseCase,
    private val getCardBalanceObservableUseCase: GetCardBalanceObservableUseCase,
    private val getMainAccountUseCase: GetMainAccountUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(
        HomeState.Loading
    )

    val state = _state.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        reduceError(ErrorType.fromThrowable(exception))
    }

    fun emitIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.EnterScreen -> loadData()
        }
    }

    private fun loadData() {
        _state.update {
            HomeState.Loading
        }

        viewModelScope.launch(errorHandler) {
            val profileJob = async() {
                val res = getCompactProfileUseCase.execute()
                ProfileUi.mapFromDomain(res)
            }

            val cardsJob = async() {
                val res = getHomeCardsUseCase.execute()

                res.map {
                    CardUi.mapFromDomain(
                        card = it,
                        balanceFlow = getCardBalanceFlow(it.cardId)
                    )
                }
            }

            val mainAccountJob = async() {
                val res = getMainAccountUseCase.execute()
                res.number
            }


            val profile = profileJob.await()
            val cards = cardsJob.await()
            val mainAccount = mainAccountJob.await()


            val balanceFlow = getTotalAccountBalanceUseCase.execute()
                .map { accountBalance ->
                    MoneyAmountUi.mapFromDomainTwo(accountBalance)
                }
                .catch {
                    reduceError(ErrorType.fromThrowable(it))
                }

            // Success state
            reduceData(
                profile = profile,
                cards = cards,
                accountNumber = mainAccount,
                balance = balanceFlow
            )
        }
    }

    private fun reduceData(
        profile: ProfileUi,
        cards: List<CardUi>,
        accountNumber: String,
        balance: Flow<MoneyAmountUi>,
    ) {
        _state.update {
            HomeState.Success(
                profile = profile,
                cards = cards,
                accountNumber = accountNumber,
                balance = balance
            )
        }
    }

    private fun reduceError(error: ErrorType) {
        _state.update {
            HomeState.Error(
                error = error.asUiTextError()
            )
        }
    }

    private suspend fun getCardBalanceFlow(cardId: String): Flow<String> {
        return getCardBalanceObservableUseCase.execute(cardId)
            .map {
                MoneyAmountUi.mapFromDomain(it).amountStr
            }
            .catch {
                val error = ErrorType.fromThrowable(it)
                if (error != ErrorType.CARD_HAS_BEEN_DELETED) {
                    reduceError(ErrorType.fromThrowable(it))
                }
            }
    }
}