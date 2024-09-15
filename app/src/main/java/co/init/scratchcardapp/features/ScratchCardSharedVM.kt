package co.init.scratchcardapp.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.init.base.BaseVM
import co.init.common.extensions.doInCoroutine
import co.init.common.extensions.doInIOCoroutine
import co.init.common.extensions.safe
import co.init.scratchcardapp.data.Card
import co.init.scratchcardapp.dialogManager.throwables.CanNotActivateCardThrowable
import co.init.scratchcardapp.features.card_activation.domain.ActivateScratchedCardUseCase
import co.init.scratchcardapp.features.card_activation.domain.ScratchCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject


@HiltViewModel
class ScratchCardSharedVM @Inject constructor(
    private val activateScratchedCardUseCase: ActivateScratchedCardUseCase,
    private val scratchedCardUseCase: ScratchCardUseCase
) : BaseVM() {

    data class ScratchCardState(
        val card: Card? = Card(),
        val loading: Boolean = false
    )

    private val _scratchCardState = MutableLiveData(ScratchCardState())
    val scratchCardState: LiveData<ScratchCardState> = _scratchCardState

    private val _activateCardResult = MutableLiveData<Result<Card>?>()
    val activateCardResult: LiveData<Result<Card>?> = _activateCardResult

    private var scratchCardJob: Job? = null
    private var activationJob: Job? = null

    fun scratchCard() {
        if (scratchCardJob?.isActive.safe()) return

        scratchCardJob = doInCoroutine {
            _scratchCardState.value?.card?.let { card ->
                _scratchCardState.value = _scratchCardState.value?.copy(loading = true)

                scratchedCardUseCase(card).collect { result ->
                    _scratchCardState.value = _scratchCardState.value?.copy(
                        card = result.getOrNull(),
                        loading = false
                    )
                }
            }
        }
    }

    fun cancelScratch() {
        _scratchCardState.value = _scratchCardState.value?.copy(loading = false)
        scratchCardJob?.cancel()
    }

    fun resetActivationResult() {
        _activateCardResult.value = null
    }

    fun activateCard() {
        if (activationJob?.isActive.safe()) return

        activationJob = doInIOCoroutine {
            _scratchCardState.value?.card?.let { card ->
                if (card.canBeActivated) {
                    activateScratchedCardUseCase(card).collect { result ->
                        result.onSuccess { card ->
                            _scratchCardState.postValue(_scratchCardState.value?.copy(card = card))
                        }
                        _activateCardResult.postValue(result)
                    }
                } else {
                    _activateCardResult.postValue(Result.failure(CanNotActivateCardThrowable()))
                }
            }
        }
    }
}