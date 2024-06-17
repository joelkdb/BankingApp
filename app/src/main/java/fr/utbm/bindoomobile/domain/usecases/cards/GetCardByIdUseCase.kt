package fr.utbm.bindoomobile.domain.usecases.cards

import fr.utbm.bindoomobile.domain.models.feature_cards.PaymentCard
import fr.utbm.bindoomobile.domain.repositories.CardsRepository


class GetCardByIdUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(cardId: String): PaymentCard {
        return cardsRepository.getCardById(cardId)
    }
}