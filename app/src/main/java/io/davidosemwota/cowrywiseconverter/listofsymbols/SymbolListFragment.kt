package io.davidosemwota.cowrywiseconverter.listofsymbols

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import io.davidosemwota.cowrywiseconverter.R
import io.davidosemwota.cowrywiseconverter.databinding.FragmentSymbolListBinding


/**
 */
class SymbolListFragment : Fragment() {

    private val args: SymbolListFragmentArgs by navArgs()
    private lateinit var binding: FragmentSymbolListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSymbolListBinding.inflate(inflater)
        return binding.root
    }
}