package fr.utbm.bindoomobile.core.di.presentation

import fr.utbm.bindoomobile.ui.app_host.AppViewModel
import fr.utbm.bindoomobile.ui.core.notifications.TransactionNotificationHelper
import fr.utbm.bindoomobile.ui.core.permissions.PermissionHelper
import fr.utbm.bindoomobile.ui.feature_app_lock.lock_screen.LockScreenViewModel
import fr.utbm.bindoomobile.ui.feature_app_lock.setup_applock.biometrics.EnableBiometricsViewModel
import fr.utbm.bindoomobile.ui.feature_app_lock.setup_applock.pin.CreatePinViewModel
import fr.utbm.bindoomobile.ui.feature_app_settings.AppSettingsViewModel
import fr.utbm.bindoomobile.ui.feature_cards.dialog_card_picker.CardPickerViewModel
import fr.utbm.bindoomobile.ui.feature_cards.screen_add_card.AddCardViewModel
import fr.utbm.bindoomobile.ui.feature_cards.screen_card_details.CardDetailsViewModel
import fr.utbm.bindoomobile.ui.feature_cards.screen_card_list.CardListViewModel
import fr.utbm.bindoomobile.ui.feature_home.HomeViewModel
import fr.utbm.bindoomobile.ui.feature_login.LoginViewModel
import fr.utbm.bindoomobile.ui.feature_onboarding.OnboardingViewModel
import fr.utbm.bindoomobile.ui.feature_profile.ProfileViewModel
import fr.utbm.bindoomobile.ui.feature_qr_codes.DisplayQrViewModel
import fr.utbm.bindoomobile.ui.feature_qr_codes.scanned_contact.ScannedContactViewModel
import fr.utbm.bindoomobile.ui.feature_transactions.TransactionHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single {
        TransactionNotificationHelper(
            applicationContext = get()
        )
    }

    single {
        PermissionHelper(
            appSettings = get()
        )
    }

    viewModel {
        OnboardingViewModel(
            passOnboardingUseCase = get()
        )
    }

    viewModel {
        LoginViewModel(
            loginUseCase = get(),
            validateLoginUseCase = get(),
            validatePasswordUseCase = get()
        )
    }

    viewModel {
        AppViewModel(
            checkIfLoggedInUseCase = get(),
            checkIfPassedOnboardingUseCase = get(),
            checkAppLockUseCase = get()
        )
    }

    viewModel {
        ProfileViewModel(
            getCompactProfileUseCase = get(),
            logoutUseCase = get()
        )
    }

    viewModel {
        HomeViewModel(
            getHomeCardsUseCase = get(),
            getCompactProfileUseCase = get(),
            getTotalAccountBalanceUseCase = get(),
            getCardBalanceObservableUseCase = get(),
            getMainAccountUseCase = get()
        )
    }

    viewModel {
        CardListViewModel(
            getAllCardsUseCase = get(),
            getCardBalanceObservableUseCase = get()
        )
    }

    viewModel {
        CardDetailsViewModel(
            getCardByIdUseCase = get(),
            deleteCardByNumberUseCase = get(),
            getCardBalanceObservableUseCase = get(),
            setCardAsPrimaryUseCase = get()
        )
    }

    viewModel {
        AddCardViewModel(
            validateCardNumberUseCase = get(),
            validateCvvCodeUseCase = get(),
            validateCardExpirationUseCase = get(),
            validateCardHolderUseCase = get(),
            validateBillingAddressUseCase = get(),
            addCardUseCase = get()
        )
    }

    viewModel {
        LockScreenViewModel(
            authenticateWithPinUseCase = get(),
            checkIfBiometricsAvailableUseCase = get(),
            checkIfAppLockedWithBiometricsUseCase = get(),
            logoutUseCase = get(),
        )
    }

    viewModel {
        CreatePinViewModel(
            setupAppLockUseCase = get(),
            checkIfBiometricsAvailableUseCase = get(),
        )
    }

    viewModel {
        EnableBiometricsViewModel(
            setupAppLockedWithBiometricsUseCase = get(),
            checkIfBiometricsAvailableUseCase = get(),
        )
    }

//    viewModel {
//        TopUpScreenViewModel(
//            getSuggestedTopUpValuesUseCase = get(),
//            getCardByIdUseCase = get(),
//            getDefaultCardUseCase = get(),
//            topUpAccountUseCase = get(),
//        )
//    }

    viewModel {
        CardPickerViewModel(
            getAllCardsUseCase = get()
        )
    }

    viewModel {
        TransactionHistoryViewModel(
            getTransactionsUseCase = get(),
            observeTransactionStatusUseCase = get()
        )
    }

//    viewModel {
//        SendMoneyViewModel(
//            getSuggestedSendValuesForCardBalance = get(),
//            getCardByIdUseCase = get(),
//            getDefaultCardUseCase = get(),
//            getRecentContactUseCase = get(),
//            getContactByIdUseCase = get(),
//            sendMoneyUseCase = get(),
//        )
//    }


    viewModel {
        DisplayQrViewModel(
            generateQrCodeUseCase = get()
        )
    }

    viewModel {
        ScannedContactViewModel(

        )
    }

    viewModel {
        AppSettingsViewModel(
            checkIfBiometricsAvailableUseCase = get(),
            checkAppLockedWithBiometricsUseCase = get(),
            lockAppLockedWithBiometricsUseCase = get(),
        )
    }
}