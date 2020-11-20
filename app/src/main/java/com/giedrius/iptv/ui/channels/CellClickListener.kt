package com.giedrius.iptv.ui.channels

import com.giedrius.iptv.parser.M3UItem

interface CellClickListener {
    fun onCellClickListener(item: M3UItem)
}