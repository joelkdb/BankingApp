package fr.utbm.bindoomobile.domain.usecases.cards

import fr.utbm.bindoomobile.domain.repositories.CardsRepository

class SetCardAsPrimaryUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(cardId: String, setAsPrimary: Boolean) {
        return cardsRepository.markCardAsPrimary(cardId, setAsPrimary)
    }
}