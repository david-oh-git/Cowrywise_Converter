package io.davidosemwota.cowrywiseconverter.listofsymbols

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.davidosemwota.core.data.SymbolItem
import io.davidosemwota.core.extentions.observe
import io.davidosemwota.cowrywiseconverter.ConverterApp
import io.davidosemwota.cowrywiseconverter.convertamount.SymbolItemMapper
import io.davidosemwota.cowrywiseconverter.databinding.FragmentSymbolListBinding
import io.davidosemwota.cowrywiseconverter.listofsymbols.adaptors.SymbolItemAdaptor
import timber.log.Timber

/**
 */
class SymbolListFragment : Fragment() {

    private val args: SymbolListFragmentArgs by navArgs()
    private lateinit var binding: FragmentSymbolListBinding
    private val viewModel: SymbolListViewModel by viewModels {
        SymbolListViewModelFactory(
            ( requireContext().applicationContext as ConverterApp).repository,
            SymbolItemMapper()
        )
    }
    private val adaptor: SymbolItemAdaptor by lazy { SymbolItemAdaptor(viewModel) }

    var fragmentTypeCode = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSymbolListBinding.inflate(inflater)

        binding.apply {
            this.vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.listOfSymbols, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
        setUpRecyclerView()
        fragmentTypeCode = args.fragmentType
    }

    private fun setUpRecyclerView() {
        binding.includeSymbolList.currencyItemRecyclerview.adapter = adaptor
    }

    private fun onViewDataChange(data: List<SymbolItem>) {
        adaptor.submitList(data)

        Timber.d("data size is ${data.size}")
        Timber.d("State is ${viewModel.state.value}")
    }

    private fun onViewEvent(viewEvent: SymbolListViewEvent){

        when(viewEvent){
            is SymbolListViewEvent.SaveSymbolCodeAndClose -> {
                viewModel.save(fragmentTypeCode, viewEvent.code)
                findNavController().popBackStack()
            }
        }
    }
}
