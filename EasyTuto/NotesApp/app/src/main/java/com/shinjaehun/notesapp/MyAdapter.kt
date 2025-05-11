package com.shinjaehun.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import java.text.DateFormat

class MyAdapter(
    private val context: Context,
    private val noteList: RealmResults<Note>
): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleOutput = itemView.findViewById<TextView>(R.id.titleoutput)
        val descriptionOutput = itemView.findViewById<TextView>(R.id.descriptionoutput)
        val timeOutput = itemView.findViewById<TextView>(R.id.timeoutput)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = noteList.get(position)
        holder.titleOutput.text = note?.title
        holder.descriptionOutput.text = note?.description
        holder.timeOutput.text = DateFormat.getDateInstance().format(note?.createdTime)

        holder.itemView.setOnLongClickListener {
            val menu = PopupMenu(context, it)
            menu.menu.add("DELETE")
            menu.setOnMenuItemClickListener { item ->
                if(item.title!!.equals("DELETE")) {
                    val realm = Realm.getDefaultInstance()
                    realm.beginTransaction()
                    note?.deleteFromRealm()
                    realm.commitTransaction()
                    Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                }
                true
            }
            menu.show()
            true
        }
    }
}