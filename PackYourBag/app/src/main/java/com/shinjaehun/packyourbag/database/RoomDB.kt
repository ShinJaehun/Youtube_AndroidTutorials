package com.shinjaehun.packyourbag.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shinjaehun.packyourbag.etc.MyConstants
import com.shinjaehun.packyourbag.dao.ItemDao
import com.shinjaehun.packyourbag.etc.Init
import com.shinjaehun.packyourbag.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var instance: RoomDB? = null

        fun getInstance(context: Context): RoomDB? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "MyDB"
                )
                    .addCallback(seedDatabaseCallback(context))
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

        private fun seedDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    var listOfAllItems = Init.getAllData()

                    GlobalScope.launch {
                        withContext(Dispatchers.IO){
                            getInstance(context)?.run {
                                listOfAllItems.forEach { listOfItems ->
                                    listOfItems.forEach {
                                        itemDao().saveItem(it)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

//        fun getBasicData(): List<Item> {
//            val category = "Basic Needs"
//            var basicItem = arrayListOf<Item>()
//            basicItem.add(Item("Visa", category, "system",false))
//            basicItem.add(Item("Passport", category, "system",false))
//            basicItem.add(Item("Tickets", category, "system",false))
//            basicItem.add(Item("Wallet", category, "system",false))
//            basicItem.add(Item("Driving License", category, "system",false))
//            basicItem.add(Item("Currency", category, "system",false))
//            basicItem.add(Item("House Key", category, "system",false))
//            basicItem.add(Item("Book", category, "system",false))
//            basicItem.add(Item("Travel Pillow", category, "system",false))
//            basicItem.add(Item("Eye Patch", category, "system",false))
//            basicItem.add(Item("Umbrella", category, "system",false))
//            basicItem.add(Item("Note Book", category, "system",false))
//            return basicItem
//        }
//
//        private fun getPersonalCareData(): List<Item> {
//            val data = listOf("Tooth-brush", "Tooth-paste", "Floss", "Mouthwash")
//            return prepareItemList(MyConstants.PERSONAL_CARE_CAMEL_CASE, data)
//        }
//
//        private fun getClothingData(): List<Item> {
//            val data = listOf("Stocking", "Underwear", "T-shirts")
//            return prepareItemList(MyConstants.CLOTHING_CAMEL_CASE, data)
//        }
//
//        private fun getBabyNeedsData(): List<Item> {
//            val data = listOf("Snapsuit", "Outfit", "Jumpsuit", "Baby Socks")
//            return prepareItemList(MyConstants.BABY_NEEDS_CAMEL_CASE, data)
//        }
//
//        private fun getHealthData(): List<Item> {
//            val data = listOf("Aspirin", "Drugs Used")
//            return prepareItemList(MyConstants.HEALTH_CAMEL_CASE, data)
//        }
//
//        private fun getTechnologyData(): List<Item> {
//            val data = listOf("Mobile Phone", "Phone cover", "Camera")
//            return prepareItemList(MyConstants.TECHNOLOGY_CAMEL_CASE, data)
//        }
//
//        private fun getFoodData(): List<Item> {
//            val data = listOf("Snack", "Sandwich", "Juice")
//            return prepareItemList(MyConstants.FOOD_CAMEL_CASE, data)
//        }
//
//        private fun getBeachSuppliesData(): List<Item> {
//            val data = listOf("Sea Glasses", "Sea Bed", "Suntan Cream", "Beach Umbrella", "Swim Fins", "Beach Bag", "Beach Towel")
//            return prepareItemList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE, data)
//        }
//
//        private fun getCarSuppliesData(): List<Item> {
//            val data = listOf("Pump", "Car Jack", "Spare Car Key")
//            return prepareItemList(MyConstants.CAR_SUPPLIES_CAMEL_CASE, data)
//        }
//
//        private fun getNeedsData(): List<Item> {
//            val data = listOf("Backpack", "Daily Bags", "Laundry Bag")
//            return prepareItemList(MyConstants.NEEDS_CAMEL_CASE, data)
//        }
//
//        private fun prepareItemList(category: String, data: List<String>): ArrayList<Item> {
//            val dataList = arrayListOf<Item>()
//            data.forEach {
//                dataList.add(Item(it, category, "system", false))
//            }
//            return dataList
//        }
//
//        private fun getAllData(): List<List<Item>> {
//            var listOfAllItems = arrayListOf<List<Item>>()
//            listOfAllItems.clear()
//            listOfAllItems.add(getPersonalCareData())
//            listOfAllItems.add(getBasicData())
//            listOfAllItems.add(getClothingData())
//            listOfAllItems.add(getBabyNeedsData())
//            listOfAllItems.add(getHealthData())
//            listOfAllItems.add(getTechnologyData())
//            listOfAllItems.add(getFoodData())
//            listOfAllItems.add(getBeachSuppliesData())
//            listOfAllItems.add(getCarSuppliesData())
//            listOfAllItems.add(getNeedsData())
//            return listOfAllItems
//        }
    }
}