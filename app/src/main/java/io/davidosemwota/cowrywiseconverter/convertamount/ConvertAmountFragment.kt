package io.davidosemwota.cowrywiseconverter.convertamount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.davidosemwota.core.extentions.observe
import io.davidosemwota.core.utils.FROM_SYMBOL_FRAGMENT
import io.davidosemwota.core.utils.TO_SYMBOL_FRAGMENT
import io.davidosemwota.cowrywiseconverter.ConverterApp
import io.davidosemwota.cowrywiseconverter.R
import io.davidosemwota.cowrywiseconverter.databinding.FragmentConvertAmountBinding
import io.davidosemwota.cowrywiseconverter.util.extentions.sendSnackMessage
import io.davidosemwota.cowrywiseconverter.util.network.NetworkCallback
import io.davidosemwota.cowrywiseconverter.util.network.NetworkChecker
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * [Fragment] Displays the entry currency convert view.
 */

@ExperimentalCoroutinesApi
class ConvertAmountFragment : Fragment() {

    private lateinit var binding: FragmentConvertAmountBinding
    private val viewModel: ConvertAmountViewModel by viewModels {
        ConvertAmountViewModelFactory(
            (requireContext().applicationContext as ConverterApp).repository
        )
    }

    private var networkCheckCallback: NetworkCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkCheckCallback = object : NetworkCallback() {

            override fun onNetworkInActive() {
                viewModel.hasNetworkConnection = false
            }

            override fun onNetworkActive() {
                viewModel.hasNetworkConnection = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConvertAmountBinding.inflate(inflater)
            .apply {
                this.vm = viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        networkCheckCallback?.let {
            NetworkChecker.registerNetworkAvailabilityCallback(requireContext(), it)
        }
    }

    override fun onPause() {
        super.onPause()

        networkCheckCallback?.let {
            NetworkChecker.unRegisterNetworkAvailabilityCallback(requireContext(), it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()

        observe(viewModel.event, ::onViewEvent)
    }

    /**
     * Set on click listeners for view items.
     */
    private fun setOnClickListeners() {
        binding.fromFakeSpinner.setOnClickListener {
            navigateToSymbolListFragment(FROM_SYMBOL_FRAGMENT)
        }

        binding.toFakeSpinner.setOnClickListener {
            navigateToSymbolListFragment(TO_SYMBOL_FRAGMENT)
        }

        binding.convertBtn.setOnClickListener {
            viewModel.convert()
        }
    }

    /**
     * Navigate from [ConvertAmountFragment] to SymbolListFragment.
     *
     * @param code States the type of SymbolListFragment.
     */
    private fun navigateToSymbolListFragment(code: String) {
        val action = ConvertAmountFragmentDirections
            .actionConvertAmountFragmentToSymbolListFragment(code)
        findNavController().navigate(action)
    }

    private fun onViewEvent(viewEvent: ConvertAmountViewEvent) = when (viewEvent) {
        is ConvertAmountViewEvent.ConvertAmount -> {
        }

        is ConvertAmountViewEvent.NoNetwork -> {
            sendSnackMessage(
                binding.convertAmountContainer,
                getString(R.string.no_network)
            )
        }

        is ConvertAmountViewEvent.InputTextIsEmpty -> {
            sendSnackMessage(
                binding.convertAmountContainer,
                getString(R.string.enter_amount)
            )
        }

        is ConvertAmountViewEvent.ErrorFetchingRate -> {
            sendSnackMessage(
                binding.convertAmountContainer,
                getString(R.string.error_fetching_rates)
            )
        }
    }
}
