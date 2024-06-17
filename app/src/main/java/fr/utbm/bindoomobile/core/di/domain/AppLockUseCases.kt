package fr.utbm.bindoomobile.core.di.domain


import fr.utbm.bindoomobile.domain.usecases.feature_app_lock.AuthenticateWithPinUseCase
import fr.utbm.bindoomobile.domain.usecases.feature_app_lock.CheckAppLockUseCase
import fr.utbm.bindoomobile.domain.usecases.feature_app_lock.CheckAppLockedWithBiometricsUseCase
import fr.utbm.bindoomobile.domain.usecases.feature_app_lock.CheckIfBiometricsAvailableUseCase
import fr.utbm.bindoomobile.domain.usecases.feature_app_lock.SetupAppLockUseCase
import fr.utbm.bindoomobile.domain.usecases.feature_app_lock.SetupAppLockedWithBiometricsUseCase
import org.koin.dsl.module

val appLockUseCasesModule = module {
    factory {
        AuthenticateWithPinUseCase(
            appLockRepository = get()
        )
    }

    factory {
        CheckAppLockUseCase(
            appLockRepository = get()
        )
    }

    factory {
        SetupAppLockUseCase(
            appLockRepository = get()
        )
    }

    factory {
        SetupAppLockedWithBiometricsUseCase(
            appLockRepository = get()
        )
    }

    factory {
        CheckAppLockedWithBiometricsUseCase(
            appLockRepository = get()
        )
    }

    factory {
        CheckIfBiometricsAvailableUseCase(
            appLockRepository = get()
        )
    }
}