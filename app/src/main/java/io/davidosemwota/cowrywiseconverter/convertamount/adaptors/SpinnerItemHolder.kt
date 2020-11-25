package io.davidosemwota.cowrywiseconverter.convertamount.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import io.davidosemwota.core.data.SymbolItem
import io.davidosemwota.cowrywiseconverter.databinding.SymbolItemBinding

/**
 * ViewHolder for Custom Spinner Adaptor.
 */
class SpinnerItemHolder(
    val binding: SymbolItemBinding
) {

    /**
     * Bind data variables for viewHolder.
     */
    fun bind(symbolItem: SymbolItem) {
        binding.apply {
            symbolItemCode.text = symbolItem.code
            executePendingBindings()
        }
    }

    companion object {

        /**
         * Helper to create [SpinnerItemHolder].
         */
        fun from(parent: ViewGroup): SpinnerItemHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SymbolItemBinding.inflate(layoutInflater, parent, false)

            return SpinnerItemHolder(binding)
        }
    }
}
