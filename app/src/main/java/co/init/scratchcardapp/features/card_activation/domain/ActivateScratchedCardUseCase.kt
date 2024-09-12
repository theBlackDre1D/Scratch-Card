package co.init.scratchcardapp.features.card_activation.domain

import javax.inject.Inject

class ActivateScratchedCardUseCase @Inject constructor(
    private val repository: ScratchedCardRepository
) {

    suspend operator fun invoke(cardNumber: String) = repository.activateCard(cardNumber)
}