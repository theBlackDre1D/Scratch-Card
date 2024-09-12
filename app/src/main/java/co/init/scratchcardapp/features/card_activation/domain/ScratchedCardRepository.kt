package co.init.scratchcardapp.features.card_activation.domain

import javax.inject.Inject

class ScratchedCardRepository @Inject constructor(
    private val remoteDataSource: ScratchedCardRemoteDataSource
) {

    suspend fun activateCard(cardNumber: String) = remoteDataSource.activateCard(cardNumber)
}