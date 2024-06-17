package fr.utbm.bindoomobile.ui.feature_cards.screen_add_card

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.data.repositories.MockCardConstants
import fr.utbm.bindoomobile.domain.core.OperationResult
import fr.utbm.bindoomobile.ui.core.extensions.getFormattedDate

data class AddCardState(
    val formFields: AddCardFormFields = AddCardFormFields(),
    val isLoading: Boolean = false,
    val showDatePicker: Boolean = false,
    val cardSavedEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
) {
    companion object {
        fun mock(
            isLoading: Boolean = false,
            showDatePicker: Boolean = false
        ): AddCardState {
            // + 365 days
            val randomMockCard = MockCardConstants.randomCard()
            val mockExpiration = System.currentTimeMillis() + 31556926000L

            return AddCardState(
                formFields = AddCardFormFields(
                    cardNumber = UiField(randomMockCard.first),
                    cardHolder = UiField("Alexander Michael"),
                    addressFirstLine = UiField("2890 Pangandaran Street"),
                    addressSecondLine = UiField(""),
                    cvvCode = UiField("123"),
                    expirationDateTimestamp = mockExpiration,
                    expirationDate = UiField(mockExpiration.getFormattedDate("dd MMM yyyy"))
                ),
                isLoading = isLoading,
                showDatePicker = showDatePicker
            )
        }
    }
}
