package com.shinjaehun.packyourbag.data

import android.app.Application
import android.util.Log
import com.shinjaehun.packyourbag.constants.MyConstants
import com.shinjaehun.packyourbag.database.RoomDB
import com.shinjaehun.packyourbag.models.Item

private const val TAG = "AppData"
class AppData(
    private val database: RoomDB
) : Application() {

//    companion object {
//        const val LAST_VERSION = "LAST_VERSION"
//        const val NEW_VERSION = 3
//    }
//
//    fun getBasicData(): List<Item> {
//        val category = "Basic Needs"
//        var basicItem = arrayListOf<Item>()
//        basicItem.add(Item("Visa", category, "system",false))
//        basicItem.add(Item("Passport", category, "system",false))
//        basicItem.add(Item("Tickets", category, "system",false))
//        basicItem.add(Item("Wallet", category, "system",false))
//        basicItem.add(Item("Driving License", category, "system",false))
//        basicItem.add(Item("Currency", category, "system",false))
//        basicItem.add(Item("House Key", category, "system",false))
//        basicItem.add(Item("Book", category, "system",false))
//        basicItem.add(Item("Travel Pillow", category, "system",false))
//        basicItem.add(Item("Eye Patch", category, "system",false))
//        basicItem.add(Item("Umbrella", category, "system",false))
//        basicItem.add(Item("Note Book", category, "system",false))
//        return basicItem
//    }
//
//    fun getPersonalCareData(): List<Item> {
//        val data = listOf("Tooth-brush", "Tooth-paste", "Floss", "Mouthwash")
//        return prepareItemList(MyConstants.PERSONAL_CARE_CAMEL_CASE, data)
//    }
//
//    fun getClothingData(): List<Item> {
//        val data = listOf("Stocking", "Underwear", "T-shirts")
//        return prepareItemList(MyConstants.CLOTHING_CAMEL_CASE, data)
//    }
//
//    fun getBabyNeedsData(): List<Item> {
//        val data = listOf("Snapsuit", "Outfit", "Jumpsuit", "Baby Socks")
//        return prepareItemList(MyConstants.BABY_NEEDS_CAMEL_CASE, data)
//    }
//
//    fun getHealthData(): List<Item> {
//        val data = listOf("Aspirin", "Drugs Used")
//        return prepareItemList(MyConstants.HEALTH_CAMEL_CASE, data)
//    }
//
//    fun getTechnologyData(): List<Item> {
//        val data = listOf("Mobile Phone", "Phone cover", "Camera")
//        return prepareItemList(MyConstants.TECHNOLOGY_CAMEL_CASE, data)
//    }
//
//    fun getFoodData(): List<Item> {
//        val data = listOf("Snack", "Sandwich", "Juice")
//        return prepareItemList(MyConstants.FOOD_CAMEL_CASE, data)
//    }
//
//    fun getBeachSuppliesData(): List<Item> {
//        val data = listOf("Sea Glasses", "Sea Bed", "Suntan Cream", "Beach Umbrella", "Swim Fins", "Beach Bag", "Beach Towel")
//        return prepareItemList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE, data)
//    }
//
//    fun getCarSuppliesData(): List<Item> {
//        val data = listOf("Pump", "Car Jack", "Spare Car Key")
//        return prepareItemList(MyConstants.CAR_SUPPLIES_CAMEL_CASE, data)
//    }
//
//    fun getNeedsData(): List<Item> {
//        val data = listOf("Backpack", "Daily Bags", "Laundry Bag")
//        return prepareItemList(MyConstants.NEEDS_CAMEL_CASE, data)
//    }
//
//
//    fun prepareItemList(category: String, data: List<String>): ArrayList<Item> {
//        val dataList = arrayListOf<Item>()
//        data.forEach {
//            dataList.add(Item(it, category, "system", false))
//        }
//        return dataList
//    }
//
//    fun getAllData(): List<List<Item>> {
//        var listOfAllItems = arrayListOf<List<Item>>()
//        listOfAllItems.clear()
//        listOfAllItems.add(getPersonalCareData())
//        listOfAllItems.add(getBasicData())
//        listOfAllItems.add(getClothingData())
//        listOfAllItems.add(getBabyNeedsData())
//        listOfAllItems.add(getHealthData())
//        listOfAllItems.add(getTechnologyData())
//        listOfAllItems.add(getFoodData())
//        listOfAllItems.add(getBeachSuppliesData())
//        listOfAllItems.add(getCarSuppliesData())
//        listOfAllItems.add(getNeedsData())
//        return listOfAllItems
//    }
//
//    fun persistentAllData(){
//        var listOfAllItems = getAllData()
//        listOfAllItems.forEach { lisfOfItems ->
//            lisfOfItems.forEach {
//                database.itemDao().saveItem(it)
//            }
//        }
//        Log.i(TAG, "Data added")
//    }
}