package io.davidosemwota.cowrywiseconverter.convertamount.adaptors

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.davidosemwota.cowrywiseconverter.convertamount.SymbolItem

class SpinnerAdaptor(
    private val items: MutableList<SymbolItem>
) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        TODO("Yet to be implemented")
    }
}