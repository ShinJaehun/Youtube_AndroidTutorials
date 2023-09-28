package com.shinjaehun.packyourbag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shinjaehun.packyourbag.adapter.CheckListAdapter
import com.shinjaehun.packyourbag.constants.MyConstants
import com.shinjaehun.packyourbag.databinding.ActivityCheckListBinding
import com.shinjaehun.packyourbag.interfaces.OnItemClick
import com.shinjaehun.packyourbag.models.Item
import com.shinjaehun.packyourbag.viewmodel.CheckListViewModel
import com.shinjaehun.packyourbag.viewmodel.CheckListViewModelFactory

private const val TAG = "CheckListActivity"
class CheckListActivity : AppCompatActivity(), OnItemClick {

    private lateinit var binding: ActivityCheckListBinding
    private lateinit var adapter: CheckListAdapter
    private lateinit var checkListViewModel: CheckListViewModel

    private var header: String? = null
    private var show: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        header = intent.getStringExtra(MyConstants.HEADER_SMALL)
        show = intent.getStringExtra(MyConstants.SHOW_SMALL)

        supportActionBar?.title = header

        val viewModelProviderFactory = CheckListViewModelFactory(application)
        checkListViewModel = ViewModelProvider(this, viewModelProviderFactory).get(CheckListViewModel::class.java)


        if (show.equals(MyConstants.FALSE_STRING)) {
            binding.linearLayout.visibility = View.GONE
            checkListViewModel.getAllSelected(true)
        } else {
            checkListViewModel.getAllCategory(header!!)
        }

//        checkListViewModel.getAllCategory(header!!) {
//            Log.i(TAG, "activityitems: ${it.toString()}")
//            updateRecycler(it!!)
//        }

        checkListViewModel.items.observe(this, Observer {
            updateRecycler(it)
        })

        binding.btnAdd.setOnClickListener {
            val itemName = binding.txtAdd.text.toString()
            if (itemName != null && !itemName.isEmpty()) {
                addNewItem(itemName)
                Toast.makeText(
                    applicationContext,
                    "Item added",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Item name shouldn't be empty.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun addNewItem(itemName: String) {
        val item = Item(itemName, header!!, MyConstants.USER_SMALL, false)
        Log.i(TAG, "item to save: ${item.toString()}")
        checkListViewModel.saveItem(item)

        binding.recyclerView.scrollToPosition(adapter.itemCount - 1) // 이게 정상적으로 동작하니?
        binding.txtAdd.setText("")
    }

    private fun updateRecycler(itemsList: List<Item>) {
        binding.recyclerView.setHasFixedSize(true)

        adapter = CheckListAdapter(itemsList, layoutInflater, show!!, this)
            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
            binding.recyclerView.adapter = adapter
    }

    override fun deleteItem(item: Item) {
        checkListViewModel.deleteItem(item)
    }

    override fun checkUncheck(id: Int, checked: Boolean) {
        checkListViewModel.checkUncheck(id, checked)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

