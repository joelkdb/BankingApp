package fr.utbm.bindoomobile.core.di.domain

import fr.utbm.bindoomobile.domain.usecases.validation.ValidateBillingAddressUseCase
import fr.utbm.bindoomobile.domain.usecases.validation.ValidateCardExpirationUseCase
import fr.utbm.bindoomobile.domain.usecases.validation.ValidateCardHolderUseCase
import fr.utbm.bindoomobile.domain.usecases.validation.ValidateCardNumberUseCase
import fr.utbm.bindoomobile.domain.usecases.validation.ValidateCvvCodeUseCase
import fr.utbm.bindoomobile.domain.usecases.validation.ValidateEmailUseCase
import fr.utbm.bindoomobile.domain.usecases.validation.ValidateLoginUseCase
import fr.utbm.bindoomobile.domain.usecases.validation.ValidatePasswordUseCase
import org.koin.dsl.module

val validationUseCasesModule = module {
    factory { ValidateCardNumberUseCase() }
    factory { ValidateCvvCodeUseCase() }
    factory { ValidateCardExpirationUseCase() }
    factory { ValidateBillingAddressUseCase() }
    factory { ValidateCardHolderUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { ValidateEmailUseCase() }
    factory { ValidateLoginUseCase() }
}