package io.davidosemwota.cowrywiseconverter.convertamount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.davidosemwota.cowrywiseconverter.R
import io.davidosemwota.cowrywiseconverter.databinding.FragmentConvertAmountBinding

/**
 */
class ConvertAmountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentConvertAmountBinding.inflate(inflater)
        return binding.root
    }
}
