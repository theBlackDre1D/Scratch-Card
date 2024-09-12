package co.init.scratchcardapp.features.card_scratch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import co.init.base.BaseFragment
import co.init.common.extensions.onClickDebounce
import co.init.scratchcardapp.MainActivityVM
import co.init.scratchcardapp.databinding.ScratchCardFragmentBinding

class ScratchCardFragment : BaseFragment<ScratchCardFragmentBinding>() {

    private val sharedActivityViewModel: MainActivityVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScratchCardFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupButton()
        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedActivityViewModel.cancelScratch()
    }

    private fun setupButton() {
        binding.scratchCardBtn.onClickDebounce {
            sharedActivityViewModel.scratchCard()
        }
    }

    private fun initObservers() {
        sharedActivityViewModel.scratchCardLiveData.observe(viewLifecycleOwner) { card ->
            binding.cardNumberValue.text = card.cardNumber
        }

        sharedActivityViewModel.scratchCardLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progress.isVisible = isLoading
        }
    }
}