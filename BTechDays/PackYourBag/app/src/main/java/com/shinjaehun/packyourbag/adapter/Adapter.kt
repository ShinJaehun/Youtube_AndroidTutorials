package com.shinjaehun.packyourbag.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.packyourbag.CheckListActivity
import com.shinjaehun.packyourbag.etc.MyConstants
import com.shinjaehun.packyourbag.databinding.MainItemBinding

class Adapter(
//    private val context: Context,
    private val titles: List<String>,
    private val images: List<Int>,
//    private val activity: Activity,
    private val inflater: LayoutInflater,
): RecyclerView.Adapter<Adapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: MainItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MainItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = titles.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            this.binding.title.setText(titles[position])
            this.binding.img.setImageResource(images[position])
            this.binding.linearLayout.alpha = 0.8F
            this.binding.linearLayout.setOnClickListener {
//                Toast.makeText(inflater.context, "clicked on card", Toast.LENGTH_SHORT).show()
                val intent = Intent(inflater.context, CheckListActivity::class.java)
                intent.putExtra(MyConstants.HEADER_SMALL, titles[position])
                if (titles[position].equals(MyConstants.MY_SELECTIONS)) {
                    intent.putExtra(MyConstants.SHOW_SMALL, MyConstants.FALSE_STRING)
                } else {
                    intent.putExtra(MyConstants.SHOW_SMALL, MyConstants.TRUE_STRING)
                }
                inflater.context.startActivity(intent)
            }
        }
    }
}