package fr.utbm.bindoomobile.core.di.domain

import fr.utbm.bindoomobile.domain.usecases.qr_codes.GenerateQrCodeUseCase
import org.koin.dsl.module

val connectionsModule = module {
    factory { GenerateQrCodeUseCase() }
}