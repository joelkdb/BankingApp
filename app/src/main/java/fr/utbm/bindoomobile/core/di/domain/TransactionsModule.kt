package fr.utbm.bindoomobile.core.di.domain

import fr.utbm.bindoomobile.domain.usecases.transactions.GetTransactionsUseCase
import fr.utbm.bindoomobile.domain.usecases.transactions.ObserveTransactionStatusUseCase
import org.koin.dsl.module

val transactionsModule = module {
    factory { GetTransactionsUseCase(get()) }
    factory { ObserveTransactionStatusUseCase(get()) }
}