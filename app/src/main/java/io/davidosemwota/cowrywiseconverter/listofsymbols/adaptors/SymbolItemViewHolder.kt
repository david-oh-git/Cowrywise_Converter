package io.davidosemwota.cowrywiseconverter.listofsymbols.adaptors

import android.view.LayoutInflater
import io.davidosemwota.core.base.BaseViewHolder
import io.davidosemwota.core.data.SymbolItem
import io.davidosemwota.cowrywiseconverter.databinding.SymbolItemBinding
import io.davidosemwota.cowrywiseconverter.listofsymbols.SymbolListViewModel

internal class SymbolItemViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<SymbolItemBinding>(
    binding = SymbolItemBinding.inflate(inflater)
) {

    /**
     * Bind view data variable [SymbolItem].
     *
     * @param symbolItem data variable.
     */
    fun bind(symbolItem: SymbolItem, viewModel: SymbolListViewModel) {
        binding.apply {
            this.symbolItem = symbolItem
            this.viewModel = viewModel
            executePendingBindings()
        }
    }
}
