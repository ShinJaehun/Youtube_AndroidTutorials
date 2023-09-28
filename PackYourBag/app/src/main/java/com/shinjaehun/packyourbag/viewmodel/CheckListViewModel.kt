package com.shinjaehun.packyourbag.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shinjaehun.packyourbag.database.RoomDB
import com.shinjaehun.packyourbag.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "CheckListViewModel"

class CheckListViewModel(
    val app: Application
): AndroidViewModel(app) {
//    var items: MutableList<Item> = mutableListOf()
    var items: LiveData<List<Item>> = MutableLiveData()

//    fun getAllCategory(category: String, onItemFetched: (List<Item>?) -> Unit) {
//        viewModelScope.launch(Dispatchers.IO) {
//            items = RoomDB.getInstance(app)?.itemDao()!!.getAllCategory(category)
//            withContext(Dispatchers.Main) {
//                Log.i(TAG, "checklistviewmodelitems: ${items.toString()}")
//                onItemFetched(items)
//            }
//        }
//    }
    fun getAllCategory(category: String) {
        items = RoomDB.getInstance(app)?.itemDao()!!.getAllCategory(category)
        Log.i(TAG, "items: ${items.value.toString()}")
    }

    fun getAllSelected(checked: Boolean) {
        items = RoomDB.getInstance(app)?.itemDao()!!.getAllSelected(checked)
    }

    fun saveItem(item: Item) {
        viewModelScope.launch {
            RoomDB.getInstance(app)?.itemDao()!!.saveItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            RoomDB.getInstance(app)?.itemDao()!!.deleteItem(item)
        }
    }

    fun checkUncheck(id: Int, checked: Boolean) {
        viewModelScope.launch {
            RoomDB.getInstance(app)?.itemDao()!!.checkUncheck(id, checked)
        }
    }


}