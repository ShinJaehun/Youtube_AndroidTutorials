package com.shinjaehun.packyourbag.interfaces

import com.shinjaehun.packyourbag.models.Item

interface OnItemClick {
    fun deleteItem(item: Item)
    fun checkUncheck(id: Int, checked: Boolean)
}