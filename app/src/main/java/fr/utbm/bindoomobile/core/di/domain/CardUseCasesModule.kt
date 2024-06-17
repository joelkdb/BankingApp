package fr.utbm.bindoomobile.core.di.domain

import fr.utbm.bindoomobile.domain.usecases.account.GetCardBalanceObservableUseCase
import fr.utbm.bindoomobile.domain.usecases.cards.AddCardUseCase
import fr.utbm.bindoomobile.domain.usecases.cards.GetAllCardsUseCase
import fr.utbm.bindoomobile.domain.usecases.cards.GetCardByIdUseCase
import fr.utbm.bindoomobile.domain.usecases.cards.GetDefaultCardUseCase
import fr.utbm.bindoomobile.domain.usecases.cards.GetHomeCardsUseCase
import fr.utbm.bindoomobile.domain.usecases.cards.RemoveCardUseCase
import fr.utbm.bindoomobile.domain.usecases.cards.SetCardAsPrimaryUseCase
import org.koin.dsl.module

val cardUseCasesModule = module {
    factory { GetAllCardsUseCase(get()) }
    factory { AddCardUseCase(get()) }
    factory { GetHomeCardsUseCase(get()) }
    factory { GetCardByIdUseCase(get()) }
    factory { RemoveCardUseCase(get()) }
    factory { GetDefaultCardUseCase(get()) }
    factory { GetCardBalanceObservableUseCase(get()) }
    factory { SetCardAsPrimaryUseCase(get()) }
}