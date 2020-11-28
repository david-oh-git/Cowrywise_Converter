package io.davidosemwota.cowrywiseconverter.convertamount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.davidosemwota.core.utils.FROM_SYMBOL_FRAGMENT
import io.davidosemwota.core.utils.TO_SYMBOL_FRAGMENT
import io.davidosemwota.cowrywiseconverter.ConverterApp
import io.davidosemwota.cowrywiseconverter.databinding.FragmentConvertAmountBinding
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
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
}
