package co.init.scratchcardapp.features.card_activation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import co.init.base.BaseFragment
import co.init.common.extensions.onClickDebounce
import co.init.scratchcardapp.R
import co.init.scratchcardapp.ScratchCardSharedVM
import co.init.scratchcardapp.databinding.CardActivationFragmentBinding
import co.init.scratchcardapp.dialogManager.DialogManager
import co.init.scratchcardapp.errorManager.ErrorManager

class CardActivationFragment : BaseFragment<CardActivationFragmentBinding>() {

    private val sharedActivityViewModel: ScratchCardSharedVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CardActivationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
        initObservers()
    }

    private fun setupButton() {
        binding.activateCardB.onClickDebounce {
            sharedActivityViewModel.activateCard()
        }
    }

    private fun initObservers() {
        sharedActivityViewModel.activateCardResult.observe(viewLifecycleOwner) { result ->
            result?.fold(
                onSuccess = {
                    DialogManager.showSuccessOkDialog(context, getString(R.string.activation_card__activation_success)) {
                        sharedActivityViewModel.resetActivationResult()
                    }
                },
                onFailure = { throwable ->
                    val errorText = ErrorManager.getMessageFromThrowable(context, throwable)
                    DialogManager.showErrorOkDialog(context, errorText) {
                        sharedActivityViewModel.resetActivationResult()
                    }
                }
            )
        }

        sharedActivityViewModel.scratchCardState.observe(viewLifecycleOwner) { card ->
            binding.scratchCardStateValueTV.text = card.cardState.toString()
            binding.activateCardB.isEnabled = !card.isActivated
        }
    }
}