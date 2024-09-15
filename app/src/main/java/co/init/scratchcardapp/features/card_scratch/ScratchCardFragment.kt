package co.init.scratchcardapp.features.card_scratch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import co.init.base.BaseFragment
import co.init.common.extensions.onClickDebounce
import co.init.common.extensions.safe
import co.init.scratchcardapp.databinding.ScratchCardFragmentBinding
import co.init.scratchcardapp.features.ScratchCardSharedVM

class ScratchCardFragment : BaseFragment<ScratchCardFragmentBinding>() {

    private val sharedActivityViewModel: ScratchCardSharedVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScratchCardFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
        initObservers()
    }

    override fun onBackPressed() { sharedActivityViewModel.cancelScratch() }

    private fun setupButton() {
        binding.scratchCardBtn.onClickDebounce {
            sharedActivityViewModel.scratchCard()
        }
    }

    private fun initObservers() {
        sharedActivityViewModel.scratchCardState.observe(viewLifecycleOwner) { state ->
            binding.cardNumberValue.text = state.card?.cardNumber
            binding.scratchCardBtn.isEnabled = !state.card?.isScratched.safe()
            binding.progress.isVisible = state.loading
        }
    }
}