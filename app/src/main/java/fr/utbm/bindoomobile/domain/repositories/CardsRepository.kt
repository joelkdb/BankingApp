package fr.utbm.bindoomobile.domain.repositories

import fr.utbm.bindoomobile.domain.models.feature_cards.AddCardPayload
import fr.utbm.bindoomobile.domain.models.feature_cards.PaymentCard


interface CardsRepository {
    suspend fun getCards(): List<PaymentCard>
    suspend fun addCard(data: AddCardPayload)
    suspend fun getCardById(id: String): PaymentCard
    suspend fun deleteCardById(id: String)
    suspend fun markCardAsPrimary(cardId: String, isPrimary: Boolean = false)
}