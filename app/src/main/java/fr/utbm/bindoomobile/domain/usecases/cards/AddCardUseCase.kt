package fr.utbm.bindoomobile.domain.usecases.cards

import fr.utbm.bindoomobile.domain.models.feature_cards.AddCardPayload
import fr.utbm.bindoomobile.domain.repositories.CardsRepository

class AddCardUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(payload: AddCardPayload) {
        cardsRepository.addCard(payload)
    }
}