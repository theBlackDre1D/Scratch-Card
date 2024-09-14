package co.init.scratchcardapp.features.card_activation.domain

import co.init.scratchcardapp.data.Card
import co.init.scratchcardapp.data.ScratchCardState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val MOCK_CARD_SCRATCH_DURATION = 2000L

class ScratchCardUseCase @Inject constructor() {

    suspend operator fun invoke(card: Card): Flow<Result<Card>> = flow {
        delay(MOCK_CARD_SCRATCH_DURATION)

        val cardCopy = card.copy(
            cardNumber = Card.generatePartNumber(),
            cardState = ScratchCardState.NEEDS_ACTIVATION
        )

        emit(Result.success(cardCopy))
    }.catch {
        emit(Result.failure(it))
    }
}