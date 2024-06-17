package fr.utbm.bindoomobile.domain.core

data class AppError(val errorType: ErrorType): Exception()