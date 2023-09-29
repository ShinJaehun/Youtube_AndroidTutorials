package com.shinjaehun.packyourbag.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shinjaehun.packyourbag.etc.MyConstants
import com.shinjaehun.packyourbag.database.RoomDB
import com.shinjaehun.packyourbag.etc.Init
import com.shinjaehun.packyourbag.models.Item
import kotlinx.coroutines.launch

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
//        Log.i(TAG, "items: ${items.value.toString()}")
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

    fun persistDataByCategory(category: String, onlyDelete: Boolean) {
        try {
            val list: List<Item> = deleteAndGetListByCategory(category, onlyDelete)
            if (!onlyDelete) {
                list.forEach {
                    saveItem(it)
                }
                Toast.makeText(app.applicationContext, "$category Reset successfully", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(app.applicationContext, "$category Reset successfully", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(app.applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
    private fun deleteAndGetListByCategory(category: String, onlyDelete: Boolean) : List<Item> {
        if (onlyDelete) {
            viewModelScope.launch {
                RoomDB.getInstance(app)?.itemDao()!!.deleteAllByCategoryAndAddedBy(category, MyConstants.SYSTEM_SMALL)
            }
        } else {
            viewModelScope.launch {
                RoomDB.getInstance(app)?.itemDao()!!.deleteAllByCategory(category)
            }
        }
        when(category) {
              MyConstants.BASIC_NEEDS_CAMEL_CASE -> {
                  Log.i(TAG, "getBasicData: ${Init.getBasicData().toString()}")
                  return Init.getBasicData()
              }

            MyConstants.CLOTHING_CAMEL_CASE -> return Init.getClothingData()
            MyConstants.PERSONAL_CARE_CAMEL_CASE -> return Init.getPersonalCareData()
            MyConstants.BABY_NEEDS_CAMEL_CASE -> return Init.getBabyNeedsData()
            MyConstants.HEALTH_CAMEL_CASE -> return Init.getHealthData()
            MyConstants.TECHNOLOGY_CAMEL_CASE -> return Init.getTechnologyData()
            MyConstants.FOOD_CAMEL_CASE -> return Init.getFoodData()
            MyConstants.BEACH_SUPPLIES_CAMEL_CASE -> return Init.getBeachSuppliesData()
            MyConstants.CAR_SUPPLIES_CAMEL_CASE -> return Init.getCarSuppliesData()
            MyConstants.NEEDS_CAMEL_CASE -> return Init.getNeedsData()
            else -> return listOf()
        }
    }
}