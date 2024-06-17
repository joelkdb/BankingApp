package fr.utbm.bindoomobile.domain.usecases.cards

import fr.utbm.bindoomobile.domain.repositories.CardsRepository

class RemoveCardUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(cardId: String) {
        cardsRepository.deleteCardById(cardId)
    }
}