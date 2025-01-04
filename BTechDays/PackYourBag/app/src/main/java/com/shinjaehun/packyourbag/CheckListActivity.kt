package com.shinjaehun.packyourbag

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shinjaehun.packyourbag.adapter.CheckListAdapter
import com.shinjaehun.packyourbag.etc.MyConstants
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

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_one, menu)
        if (header!!.equals(MyConstants.MY_SELECTIONS)) {
            menu.getItem(0).setVisible(false)
            menu.getItem(2).setVisible(false)
            menu.getItem(3).setVisible(false)
        } else if (header!!.equals(MyConstants.MY_LIST_CAMEL_CASE)) {
            menu.getItem(1).setVisible(false)
        }

        val menuItem: MenuItem = menu.findItem(R.id.btnSearch)
        val searchView : SearchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var mFinalList : MutableList<Item> = mutableListOf()
                if (!newText.isNullOrEmpty()) {
                    checkListViewModel.items.value!!.forEach {
                        if (it.itemname.toLowerCase().startsWith(newText.toLowerCase())) {
                            mFinalList.add(it)
                        }
                    }
                    updateRecycler(mFinalList)
                } else {
                    updateRecycler(checkListViewModel.items.value!!)
                }
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, CheckListActivity::class.java)
        when (item.itemId) {
            R.id.btnMySelections -> {
                intent.putExtra(MyConstants.HEADER_SMALL, MyConstants.MY_SELECTIONS)
                intent.putExtra(MyConstants.SHOW_SMALL, MyConstants.FALSE_STRING)
                startActivity(intent)
                return true
            }
            R.id.btnCustomList -> {
                intent.putExtra(MyConstants.HEADER_SMALL, MyConstants.MY_LIST_CAMEL_CASE)
                intent.putExtra(MyConstants.SHOW_SMALL, MyConstants.TRUE_STRING)
                startActivity(intent)
                return true
            }
            R.id.btnDeleteDefault -> {
                AlertDialog.Builder(this)
                    .setTitle("Delete default data")
                    .setMessage("Are you sure?\n\nAs this will delete the data provided by (Pack Your Bag) while installing...")
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, i ->
                        checkListViewModel.persistDataByCategory(header!!, true)
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, i ->

                    })
                    .setIcon(R.drawable.ic_alert)
                    .show()
                return true
            }
            R.id.btnReset -> {
                AlertDialog.Builder(this)
                    .setTitle("Reset to default")
                    .setMessage("Are you sure?\n\nAs this will load the default data provided by (Pack Your Bag) and will delete the custom data you have added in (${header!!})")
                    .setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, i ->
                        checkListViewModel.persistDataByCategory(header!!, false)
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, i ->

                    })
                    .setIcon(R.drawable.ic_alert)
                    .show()
                return true
            }
            R.id.btnAboutUs -> {
                val intent = Intent(this, AboutUSActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.btnExit -> {
                this.finishAffinity()
                Toast.makeText(
                    applicationContext,
                    "Pack your bag\nExit completed",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
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

