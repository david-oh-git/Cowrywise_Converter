package io.davidosemwota.cowrywiseconverter.listofsymbols.adaptors

import android.view.LayoutInflater
import io.davidosemwota.core.base.BaseViewHolder
import io.davidosemwota.cowrywiseconverter.convertamount.SymbolItem
import io.davidosemwota.cowrywiseconverter.databinding.SymbolItemBinding

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
    fun bind(symbolItem: SymbolItem) {
        binding.apply {
            this.symbolItem = symbolItem
            executePendingBindings()
        }
    }
}
