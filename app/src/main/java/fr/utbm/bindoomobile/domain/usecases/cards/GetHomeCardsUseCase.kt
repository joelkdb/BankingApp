package fr.utbm.bindoomobile.domain.usecases.cards

import fr.utbm.bindoomobile.domain.models.feature_cards.PaymentCard
import fr.utbm.bindoomobile.domain.repositories.CardsRepository

class GetHomeCardsUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(): List<PaymentCard> {
        val allCards = cardsRepository.getCards()

        val (primary, other) = allCards.partition { it.isPrimary }

        val sortedPrimary = primary.sortedByDescending { it.addedDate }
        val sortedOther = other.sortedByDescending { it.addedDate }

        return (sortedPrimary + sortedOther).take(DISPLAYED_COUNT)
    }

    companion object {
        private const val DISPLAYED_COUNT = 3
    }
}