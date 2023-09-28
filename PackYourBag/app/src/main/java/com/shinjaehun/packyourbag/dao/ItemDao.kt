package com.shinjaehun.packyourbag.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.shinjaehun.packyourbag.models.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(item: Item)

    @Query("select * from items where category = :category order by id asc")
    fun getAllCategory(category: String): LiveData<List<Item>>
//    @Query("select * from items where category = :category order by id asc")
//    fun getAllCategory(category: String): MutableList<Item>

//    @Query("select * from items")
//    fun getAll(): LiveData<List<Item>>

    @Query("select * from items where checked = :checked order by id asc")
    fun getAllSelected(checked: Boolean) : LiveData<List<Item>>

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("update items set checked = :checked where ID = :id")
    suspend fun checkUncheck(id: Int, checked: Boolean)

    @Query("select count(*) from items")
    fun getItemsCount(): Int

    @Query("delete from items where addedby = :addedby")
    fun deleteAllSystemItems(addedby: String): Int

    @Query("delete from items where category = :category")
    fun deleteAllByCategory(category: String): Int

    @Query("delete from items where category = :category and addedby = :addedby")
    fun deleteAllByCategoryAndAddedBy(category: String, addedby: String): Int
}