package fr.utbm.bindoomobile.domain.core

enum class ErrorType {
    USER_NOT_FOUND,
    WRONG_PASSWORD,
    WRONG_CONFIRMATION_CODE,
    FIELD_IS_EMPTY,
    INVALID_CARD_NUMBER,
    CARD_EXPIRED,
    INVALID_CVV,
    DATE_UNSPECIFIED,
    CARD_NOT_FOUND,
    CARD_ALREADY_ADDED,
    CARD_HAS_BEEN_DELETED,
    INVALID_PASSWORD_FIELD,
    INVALID_EMAIL_FIELD,
    TRANSACTION_NOT_FOUND,
    GENERIC_VALIDATION_ERROR,
    GENERIC_NOT_FOUND_ERROR,
    INSUFFICIENT_CARD_BALANCE,
    DATA_FETCHING_ERROR,
    INVALID_LOGIN_FIELD,
    INVALID_TOKEN,
    ACCOUNT_NOT_FOUND,

    UNKNOWN_ERROR, ;

    companion object {
        fun fromThrowable(e: Throwable): ErrorType {
            return when (e) {
                is AppError -> e.errorType
                else -> UNKNOWN_ERROR
            }
        }
    }
}