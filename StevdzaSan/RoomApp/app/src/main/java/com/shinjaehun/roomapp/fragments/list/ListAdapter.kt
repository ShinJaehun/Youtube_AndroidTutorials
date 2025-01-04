package com.shinjaehun.roomapp.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.shinjaehun.roomapp.model.User
import com.shinjaehun.roomapp.databinding.CustomRowBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(val binding: CustomRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.apply {
            binding.apply {
//                idTxt.text = currentItem.id.toString()
                photoIv.load(currentItem.profilePhoto)
                firstNameTxt.text = currentItem.firstName
                lastNameTxt.text = currentItem.lastName
                ageTxt.text = currentItem.age.toString()
                streetNameTxt.text = currentItem.address.streetName
                streetNumberTxt.text = currentItem.address.streetNumber.toString()

                rowLayout.setOnClickListener {
                    val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    fun setData(users: List<User>) {
        this.userList = users
        notifyDataSetChanged()
    }

}