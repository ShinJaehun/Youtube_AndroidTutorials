package com.shinjaehun.packyourbag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.shinjaehun.packyourbag.adapter.Adapter
import com.shinjaehun.packyourbag.constants.MyConstants
import com.shinjaehun.packyourbag.databinding.ActivityMainBinding
import com.shinjaehun.packyourbag.databinding.MainItemBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TIME_INTERVAL = 2000
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
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

        adapter = Adapter(titles!!, images!!, layoutInflater)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

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