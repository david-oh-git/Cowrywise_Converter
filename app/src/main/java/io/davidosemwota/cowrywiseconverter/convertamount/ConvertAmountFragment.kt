package io.davidosemwota.cowrywiseconverter.convertamount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.davidosemwota.core.utils.FROM_CODE_KEY
import io.davidosemwota.cowrywiseconverter.databinding.FragmentConvertAmountBinding

/**
 */
class ConvertAmountFragment : Fragment() {

    private lateinit var binding: FragmentConvertAmountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConvertAmountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.convertBtn.setOnClickListener {
            navigateToSymbolListFragment()
        }
    }

    private fun navigateToSymbolListFragment() {
        val action = ConvertAmountFragmentDirections
            .actionConvertAmountFragmentToSymbolListFragment(FROM_CODE_KEY)
        findNavController().navigate(action)
    }
}
