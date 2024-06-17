package fr.utbm.bindoomobile.ui.feature_contact.model

import fr.utbm.bindoomobile.data.repositories.TransactionRepositoryMock
import fr.utbm.bindoomobile.ui.core.extensions.splitStringWithDivider

data class ContactUi(
    val name: String,
    val id: Long,
    val cardNumber: String,
    val profilePictureUrl: String
) {
    companion object {
        fun mock() = ContactUi(
            name = "Vina Andini",
            id = 0,
            cardNumber = "0000111122223333".splitStringWithDivider(),
            profilePictureUrl = ""
        )

        fun mapFromDomain(contact: TransactionRepositoryMock.Contact) = ContactUi(
            name = contact.name,
            id = contact.id,
            cardNumber = contact.linkedCardNumber.splitStringWithDivider(),
            profilePictureUrl = contact.profilePic
        )
    }
}
