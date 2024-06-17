package fr.utbm.bindoomobile.domain.usecases.cards

import fr.utbm.bindoomobile.domain.models.feature_cards.PaymentCard
import fr.utbm.bindoomobile.domain.repositories.CardsRepository


class GetAllCardsUseCase(private val cardsRepository: CardsRepository) {
    suspend fun execute(): List<PaymentCard> {
        val all = cardsRepository.getCards()

        val primary = all
            .filter { it.isPrimary }
            .sortedByDescending { it.addedDate }

        val other = all
            .filter { !it.isPrimary }
            .sortedByDescending { it.addedDate }

        return primary + other
    }
}