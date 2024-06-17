package fr.utbm.bindoomobile.domain.usecases.cards

import fr.utbm.bindoomobile.domain.models.feature_cards.PaymentCard
import fr.utbm.bindoomobile.domain.repositories.CardsRepository


class GetDefaultCardUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(): PaymentCard? {
        val cards = cardsRepository.getCards()

        return cards.find {
            it.isPrimary
        } ?: cards.firstOrNull()
    }
}