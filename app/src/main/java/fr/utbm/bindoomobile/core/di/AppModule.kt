package fr.utbm.bindoomobile.core.di

import fr.utbm.bindoomobile.core.di.data.dataModule
import fr.utbm.bindoomobile.core.di.data.networkModule
import fr.utbm.bindoomobile.core.di.data.workerModule
import fr.utbm.bindoomobile.core.di.domain.accountUseCasesModule
import fr.utbm.bindoomobile.core.di.domain.appLockUseCasesModule
import fr.utbm.bindoomobile.core.di.domain.cardUseCasesModule
import fr.utbm.bindoomobile.core.di.domain.connectionsModule
import fr.utbm.bindoomobile.core.di.domain.loginUseCasesModule
import fr.utbm.bindoomobile.core.di.domain.onboardingModule
import fr.utbm.bindoomobile.core.di.domain.profileUseCasesModule
import fr.utbm.bindoomobile.core.di.domain.transactionsModule
import fr.utbm.bindoomobile.core.di.domain.validationUseCasesModule
import fr.utbm.bindoomobile.core.di.presentation.presentationModule
import org.koin.dsl.module

val appModule = module {
    includes(appLockUseCasesModule)

    includes(loginUseCasesModule)

    includes(cardUseCasesModule)
    includes(validationUseCasesModule)
    includes(profileUseCasesModule)
    includes(onboardingModule)
    includes(accountUseCasesModule)
    includes(transactionsModule)
    includes(connectionsModule)

    includes(dataModule)
    includes(networkModule)
    includes(presentationModule)
    includes(workerModule)
}