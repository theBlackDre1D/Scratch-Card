package co.init.scratchcardapp.features.card_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import co.init.base.BaseFragment
import co.init.common.extensions.onClickDebounce
import co.init.scratchcardapp.MainActivityVM
import co.init.scratchcardapp.R
import co.init.scratchcardapp.databinding.CardHomeFragmentBinding

class CardHomeFragment : BaseFragment<CardHomeFragmentBinding>() {

    private val sharedActivityViewModel: MainActivityVM by activityViewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CardHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
        initObservers()
    }

    private fun setupButtons() {
        binding.scratchCardBtn.onClickDebounce {
            navController.navigate(R.id.action_cardHomeFragment_to_scratchCardFragment)
        }

        binding.activationCardBtn.onClickDebounce {
            navController.navigate(R.id.action_cardHomeFragment_to_cardActivationFragment)
        }
    }

    private fun initObservers() {
        sharedActivityViewModel.scratchCardLiveData.observe(viewLifecycleOwner) { card ->
            binding.scratchCardStateValue.text = card.cardState.toString()
        }
    }
}