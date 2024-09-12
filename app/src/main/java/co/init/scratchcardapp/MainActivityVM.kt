package co.init.scratchcardapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.init.base.BaseVM
import co.init.common.SingleLiveEvent
import co.init.common.extensions.doInCoroutine
import co.init.common.extensions.doInIOCoroutine
import co.init.common.extensions.safe
import co.init.scratchcardapp.data.Card
import co.init.scratchcardapp.data.ScratchCardState
import co.init.scratchcardapp.data.throwables.CanNotActivateCardThrowable
import co.init.scratchcardapp.features.card_activation.domain.ActivateScratchedCardUseCase
import co.init.scratchcardapp.features.card_activation.domain.CardActivationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import javax.inject.Inject

private const val MOCK_CARD_SCRATCH_DURATION = 2000L

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val activateScratchedCardUseCase: ActivateScratchedCardUseCase
) : BaseVM() {

    private val _scratchCardLiveData = MutableLiveData(Card())
    val scratchCardLiveData: LiveData<Card> = _scratchCardLiveData

    private val _activateCardResult = SingleLiveEvent<Result<CardActivationResponse>>()
    val activateCardResult: LiveData<Result<CardActivationResponse>> = _activateCardResult

    private val _scratchCardLoading = SingleLiveEvent<Boolean>()
    val scratchCardLoading: LiveData<Boolean> get() = _scratchCardLoading

    private var scratchCardJob: Job? = null
    private var activationJob: Job? = null

    fun activateCard() {
        if (activationJob?.isActive.safe()) return

        activationJob = doInIOCoroutine {
            _scratchCardLiveData.value?.let { card ->
                if (card.cardState == ScratchCardState.NEEDS_ACTIVATION && card.cardNumber != null) {
                    activateScratchedCardUseCase.invoke(card.cardNumber).collect { result ->
                        if (result.isSuccess) {
                            _scratchCardLiveData.postValue(_scratchCardLiveData.value?.copy(cardState = ScratchCardState.ACTIVATED))
                        }
                        _activateCardResult.postValue(result)
                    }
                } else {
                    _activateCardResult.postValue(Result.failure(CanNotActivateCardThrowable()))
                }
            }
        }
    }

    fun scratchCard() {
        if (scratchCardJob?.isActive.safe()) return

        scratchCardJob = doInCoroutine {
            _scratchCardLoading.value = true

            delay(MOCK_CARD_SCRATCH_DURATION)
            var card = _scratchCardLiveData.value
            card = card?.copy(cardNumber = Card.generatePartNumber())
            _scratchCardLiveData.value = card

            setCardState(ScratchCardState.NEEDS_ACTIVATION)
            _scratchCardLoading.value = false
        }
    }

    fun cancelScratch() {
        _scratchCardLoading.value = false
        scratchCardJob?.cancel()
    }

    private fun setCardState(state: ScratchCardState) {
        _scratchCardLiveData.value = _scratchCardLiveData.value?.copy(cardState = state)
    }
}