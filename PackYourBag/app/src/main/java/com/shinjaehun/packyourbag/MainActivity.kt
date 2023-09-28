package com.shinjaehun.packyourbag

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.shinjaehun.packyourbag.adapter.Adapter
import com.shinjaehun.packyourbag.constants.MyConstants
import com.shinjaehun.packyourbag.data.AppData
import com.shinjaehun.packyourbag.database.RoomDB
import com.shinjaehun.packyourbag.databinding.ActivityMainBinding
import com.shinjaehun.packyourbag.databinding.MainItemBinding
import com.shinjaehun.packyourbag.viewmodel.ItemViewModel
import com.shinjaehun.packyourbag.viewmodel.ItemViewModelFactory

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TIME_INTERVAL = 2000
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
//    private lateinit var itemViewModel: ItemViewModel
//    private lateinit var database: RoomDB

    private var titles: ArrayList<String>? = null
    private var images: ArrayList<Int>? = null
    private var mBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        addAllTitles()
        addAllImages()

//        val viewModelProviderFactory = ItemViewModelFactory(application)
//        itemViewModel = ViewModelProvider(this, viewModelProviderFactory).get(ItemViewModel::class.java)

//        itemViewModel.persistetAllData()
//        persistentAppData()
//        database = RoomDB.getInstance(this)!!
//        itemViewModel.getAll()
//        itemViewModel.items.observe(this, Observer {
//            Log.i(TAG, "------------------------------------> " + it[0].itemname)
//            // 얘는 아예 실행도 되지 않음...
//        })


        adapter = Adapter(titles!!, images!!, layoutInflater)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

//    fun persistentAppData() {
//        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//        val editor: SharedPreferences.Editor = prefs.edit()
//
//        database = RoomDB.getInstance(this)!!
//        val appData : AppData = AppData(database)
//        val last = prefs.getInt(AppData.LAST_VERSION, 0)
//        if (!prefs.getBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, false)) {
//            appData.persistentAllData()
//            editor.putBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, true)
//            editor.commit()
//        } else if (last < AppData.NEW_VERSION) {
//            database.itemDao().deleteAllSystemItems(MyConstants.SYSTEM_SMALL)
//            appData.persistentAllData()
//            editor.putInt(AppData.LAST_VERSION, AppData.NEW_VERSION)
//            editor.commit()
//        }
//    }


    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(this, "Tab back button in order to exit", Toast.LENGTH_SHORT).show()
        }
        mBackPressed = System.currentTimeMillis()
    }

    private fun addAllTitles(){
        titles = arrayListOf()
        titles!!.add(MyConstants.BASIC_NEEDS_CAMEL_CASE)
        titles!!.add(MyConstants.CLOTHING_CAMEL_CASE)
        titles!!.add(MyConstants.PERSONAL_CARE_CAMEL_CASE)
        titles!!.add(MyConstants.BABY_NEEDS_CAMEL_CASE)
        titles!!.add(MyConstants.HEALTH_CAMEL_CASE)
        titles!!.add(MyConstants.TECHNOLOGY_CAMEL_CASE)
        titles!!.add(MyConstants.FOOD_CAMEL_CASE)
        titles!!.add(MyConstants.BEACH_SUPPLIES_CAMEL_CASE)
        titles!!.add(MyConstants.CAR_SUPPLIES_CAMEL_CASE)
        titles!!.add(MyConstants.NEEDS_CAMEL_CASE)
        titles!!.add(MyConstants.MY_LIST_CAMEL_CASE)
        titles!!.add(MyConstants.MY_SELECTIONS_CAMEL_CASE)
    }

    private fun addAllImages(){
        images = arrayListOf()
        images!!.add(R.drawable.p1)
        images!!.add(R.drawable.p2)
        images!!.add(R.drawable.p3)
        images!!.add(R.drawable.p4)
        images!!.add(R.drawable.p5)
        images!!.add(R.drawable.p6)
        images!!.add(R.drawable.p7)
        images!!.add(R.drawable.p8)
        images!!.add(R.drawable.p9)
        images!!.add(R.drawable.p10)
        images!!.add(R.drawable.p11)
        images!!.add(R.drawable.p12)
    }
}