package co.init.scratchcardapp.features.card_activation.domain

import co.init.scratchcardapp.data.Card
import co.init.scratchcardapp.data.ScratchCardState
import co.init.scratchcardapp.data.throwables.FailedActivationThrowable
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val LIMIT_RESPONSE_VALUE = 277028

class ActivateScratchedCardUseCase @Inject constructor(
    private val repository: ScratchedCardRepository
) {

    suspend operator fun invoke(card: Card) = repository.activateCard(card.cardNumber).map { result ->
        result.fold(
            onSuccess = { response ->
                if (response.android.toInt() > LIMIT_RESPONSE_VALUE) {
                    val activatedCard = card.copy(cardState = ScratchCardState.ACTIVATED)
                    return@fold Result.success(activatedCard)
                } else {
                    return@fold Result.failure(FailedActivationThrowable())
                }
            },
            onFailure = {
                return@fold Result.failure(it)
            }
        )
    }
}