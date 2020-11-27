package io.davidosemwota.cowrywiseconverter.convertamount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.davidosemwota.core.utils.FROM_CODE_KEY
import io.davidosemwota.core.utils.TO_CODE_KEY
import io.davidosemwota.cowrywiseconverter.ConverterApp
import io.davidosemwota.cowrywiseconverter.databinding.FragmentConvertAmountBinding

/**
 */
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

        binding.fromFakeSpinner.setOnClickListener {
            navigateToSymbolListFragment(FROM_CODE_KEY)
        }

        binding.toFakeSpinner.setOnClickListener {
            navigateToSymbolListFragment(TO_CODE_KEY)
        }

        binding.convertBtn.setOnClickListener {
            viewModel.convert()
        }
    }

    private fun navigateToSymbolListFragment(code: String) {
        val action = ConvertAmountFragmentDirections
            .actionConvertAmountFragmentToSymbolListFragment(code)
        findNavController().navigate(action)
    }
}
