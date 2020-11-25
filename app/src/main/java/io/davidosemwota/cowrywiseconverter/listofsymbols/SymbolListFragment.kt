package io.davidosemwota.cowrywiseconverter.listofsymbols

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import io.davidosemwota.core.ServiceLocator
import io.davidosemwota.core.extentions.observe
import io.davidosemwota.core.mapper.SymbolListMapper
import io.davidosemwota.core.utils.FROM_CODE_KEY
import io.davidosemwota.core.utils.TO_CODE_KEY
import io.davidosemwota.cowrywiseconverter.convertamount.SymbolItem
import io.davidosemwota.cowrywiseconverter.databinding.FragmentSymbolListBinding
import io.davidosemwota.cowrywiseconverter.listofsymbols.adaptors.SymbolItemAdaptor

/**
 */
class SymbolListFragment : Fragment() {

    private val args: SymbolListFragmentArgs by navArgs()
    private lateinit var binding: FragmentSymbolListBinding
    private val viewModel: SymbolListViewModel by viewModels {
        SymbolListViewModelFactory(
            ServiceLocator.provideRepository(requireContext().applicationContext),
            SymbolListMapper()
        )
    }
    private val adaptor: SymbolItemAdaptor by lazy { SymbolItemAdaptor() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSymbolListBinding.inflate(inflater)

        binding.apply {
            this.viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (args.fragmentType) {
            FROM_CODE_KEY -> viewModel.setState(SymbolListViewState.LoadedFromSymbol)
            TO_CODE_KEY -> viewModel.setState(SymbolListViewState.LoadedToSymbol)
        }

        observe(viewModel.listOfSymbols, ::onViewDataChange)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.includeSymbolList.currencyItemRecyclerview.adapter = adaptor
    }

    private fun onViewDataChange(data: List<SymbolItem>) {
        adaptor.submitList(data)
    }
}
