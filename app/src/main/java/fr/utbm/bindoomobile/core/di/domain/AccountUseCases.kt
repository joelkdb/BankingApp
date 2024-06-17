package fr.utbm.bindoomobile.core.di.domain

import fr.utbm.bindoomobile.domain.usecases.account.GetMainAccountUseCase
import fr.utbm.bindoomobile.domain.usecases.account.GetTotalAccountBalanceUseCase
import org.koin.dsl.module

val accountUseCasesModule = module {
    factory {
        GetTotalAccountBalanceUseCase(
            accountRepository = get()
        )
    }

    factory {
        GetMainAccountUseCase(
            accountRepository = get()
        )
    }

//    factory {
//        GetSuggestedTopUpValuesUseCase()
//    }
//
//    factory {
//        GetSuggestedSendValuesForCardBalance()
//    }
//
//    factory {
//        TopUpAccountUseCase(
//            transactionRepository = get()
//        )
//    }
//
//    factory {
//        SendMoneyUseCase(
//            transactionRepository = get()
//        )
//    }
}