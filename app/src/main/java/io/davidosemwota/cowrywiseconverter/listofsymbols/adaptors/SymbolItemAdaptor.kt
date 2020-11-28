package io.davidosemwota.cowrywiseconverter.listofsymbols.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.davidosemwota.core.base.BaseListAdaptor
import io.davidosemwota.core.data.SymbolItem
import io.davidosemwota.cowrywiseconverter.listofsymbols.SymbolListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * [RecyclerView] Adaptor displaying a list of all currency symbols
 * from the database.
 */
@ExperimentalCoroutinesApi
class SymbolItemAdaptor(
    private val viewModel: SymbolListViewModel
) : BaseListAdaptor<SymbolItem>(
    itemsSame = { old, new -> old.code == new.code },
    contentsSame = { old, new -> old.code == new.code && old.name == new.name }
) {

    /**
     * Called when recyclerView needs a new [RecyclerView.ViewHolder] for the view type.
     * @param parent The parent view into which the new ViewHolder is added.
     * @param inflater Instantiate the xml layout for the view.
     * @param viewType Specifies the new view type.
     * @return A new viewHolder for the viewType.
     *
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder = SymbolItemViewHolder(inflater)

    /**
     * Called by recyclerView to display data for a specified position.
     *
     * @param holder The viewHolder to be updated.
     * @param position The viewHolder's position.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SymbolItemViewHolder -> holder.bind(getItem(position), viewModel)
        }
    }
}
