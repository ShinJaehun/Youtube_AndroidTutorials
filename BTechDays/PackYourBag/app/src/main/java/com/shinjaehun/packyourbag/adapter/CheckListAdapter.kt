package com.shinjaehun.packyourbag.adapter

import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.packyourbag.interfaces.OnItemClick
import com.shinjaehun.packyourbag.R
import com.shinjaehun.packyourbag.etc.MyConstants
import com.shinjaehun.packyourbag.databinding.CheckListItemBinding
import com.shinjaehun.packyourbag.models.Item

private const val TAG = "CheckListAdapter"
class CheckListAdapter(
    private val itemsList: List<Item>,
    private val inflater: LayoutInflater,
    private val show: String,
    private val listener: OnItemClick
): RecyclerView.Adapter<CheckListAdapter.CheckListViewHolder>() {

    inner class CheckListViewHolder(val binding: CheckListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListViewHolder {
        if (itemsList.size == 0) {
            Toast.makeText(inflater.context, "nothing to show", Toast.LENGTH_SHORT).show()
        }
        return CheckListViewHolder(CheckListItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(holder: CheckListViewHolder, position: Int) {
        with(holder) {
            with(itemsList[position]) {
                binding.checkbox.text = this.itemname
                binding.checkbox.isChecked = this.checked

                if (show.equals(MyConstants.FALSE_STRING)) {
                    binding.btnDelete.visibility = View.GONE
                    binding.linearLayout.setBackgroundDrawable(inflater.context.resources.getDrawable(R.drawable.border_one))
                } else {
                    if (itemsList[position].checked) {
                        binding.linearLayout.setBackgroundColor(Color.parseColor("#8e546f"))
                    } else {
                        binding.linearLayout.setBackgroundDrawable(inflater.context.resources.getDrawable(R.drawable.border_one))
                    }
                }

                binding.checkbox.setOnClickListener {
                    val check = binding.checkbox.isChecked()
                    Log.i(TAG, "check: $check")

                    listener.checkUncheck(this.id, check)

                    Log.i(TAG, "itemList checked: ${itemsList[position].checked}")
                    var toastMessage: Toast? = null
                    toastMessage = if (!itemsList[position].checked) {
                        // 이게 좀 이상하게 동작하긴 하는데
                        // ViewModel을 거쳐서 DB에 check 된 값을 비교하는 게 아니라
                        // adapter에 의해 보여지는 itemList의 상태를 보여주기 때문...
                        Toast.makeText(inflater.context, "(" + binding.checkbox.text + ") Packed", Toast.LENGTH_SHORT)
                    } else {
                        Toast.makeText(inflater.context, "(" + binding.checkbox.text + ") Un-Packed", Toast.LENGTH_SHORT)
                    }
                    toastMessage.show()

                }

                binding.btnDelete.setOnClickListener {
                    Log.i(TAG, "Deleting~~~~~~~~~~~~~~~~~~")

                    AlertDialog.Builder(inflater.context)
                        .setTitle("Delete ( " + itemsList[position].itemname + ")")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, i ->
                            listener.deleteItem(this)
                        })
                        .setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, i ->
                            Toast.makeText(inflater.context, "canceled", Toast.LENGTH_SHORT).show()
                        })
                        .setIcon(R.drawable.ic_delete)
                        .show()
                }
            }
        }
    }
}