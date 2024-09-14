package co.init.scratchcardapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.init.base.BaseVM
import co.init.common.extensions.doInCoroutine
import co.init.common.extensions.doInIOCoroutine
import co.init.common.extensions.safe
import co.init.scratchcardapp.data.Card
import co.init.scratchcardapp.data.throwables.CanNotActivateCardThrowable
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

    private val _scratchCardState = MutableLiveData(Card())
    val scratchCardState: LiveData<Card> = _scratchCardState

    private val _activateCardResult = MutableLiveData<Result<Card>?>()
    val activateCardResult: LiveData<Result<Card>?> = _activateCardResult

    private val _scratchCardLoading = MutableLiveData<Boolean>()
    val scratchCardLoading: LiveData<Boolean> get() = _scratchCardLoading

    private var scratchCardJob: Job? = null
    private var activationJob: Job? = null

    fun scratchCard() {
        if (scratchCardJob?.isActive.safe()) return

        scratchCardJob = doInCoroutine {
            _scratchCardState.value?.let { card ->
                _scratchCardLoading.value = true

                scratchedCardUseCase(card).collect { result ->
                    _scratchCardState.value = result.getOrNull()
                    _scratchCardLoading.value = false
                }
            }
        }
    }

    fun cancelScratch() {
        _scratchCardLoading.value = false
        scratchCardJob?.cancel()
    }

    fun resetActivationResult() {
        _activateCardResult.value = null
    }

    fun activateCard() {
        if (activationJob?.isActive.safe()) return

        activationJob = doInIOCoroutine {
            _scratchCardState.value?.let { card ->
                if (card.canBeActivated) {
                    activateScratchedCardUseCase(card).collect { result ->
                        result.onSuccess { card -> _scratchCardState.postValue(card) }
                        _activateCardResult.postValue(result)
                    }
                } else {
                    _activateCardResult.postValue(Result.failure(CanNotActivateCardThrowable()))
                }
            }
        }
    }
}