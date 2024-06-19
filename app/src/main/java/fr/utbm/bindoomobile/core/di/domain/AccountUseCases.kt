package fr.utbm.bindoomobile.core.di.domain

import fr.utbm.bindoomobile.domain.usecases.account.GetAccountByIdUseCase
import fr.utbm.bindoomobile.domain.usecases.account.GetDefaultAccountUseCase
import fr.utbm.bindoomobile.domain.usecases.account.GetMainAccountUseCase
import fr.utbm.bindoomobile.domain.usecases.account.GetRecipientByAccountUseCase
import fr.utbm.bindoomobile.domain.usecases.account.GetTotalAccountBalanceUseCase
import fr.utbm.bindoomobile.domain.usecases.account.SendMoneyUseCase
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

    factory {
        SendMoneyUseCase(
            transactionRepository = get()
        )
    }

    factory {
        GetAccountByIdUseCase(
            accountRepository = get()
        )
    }

    factory {
        GetDefaultAccountUseCase(
            accountRepository = get()
        )
    }

    factory {
        GetRecipientByAccountUseCase(
            accountRepository = get()
        )
    }
}