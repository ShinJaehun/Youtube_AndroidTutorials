package com.shinjaehun.mvvmnotefirebase.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.mvvmnotefirebase.data.model.Task
import com.shinjaehun.mvvmnotefirebase.databinding.TaskLayoutBinding

class TaskListingAdapter(
    val onItemClicked: ((Int, Task) -> Unit)? = null,
    val onDeletedClicked: ((Int, Task) -> Unit)? = null,
) : RecyclerView.Adapter<TaskListingAdapter.MyViewHolder>() {
    private var tasks: MutableList<Task> = arrayListOf()

    inner class MyViewHolder(val binding: TaskLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task, position: Int) {
            binding.title.setText(item.description)

            binding.itemLayout.setOnClickListener {
                onItemClicked?.invoke(position, item)
            }

            binding.delete.setOnClickListener {
                onDeletedClicked?.invoke(position, item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item, position)
    }

    fun updateList(tasks: MutableList<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        tasks.removeAt(position)
        notifyItemChanged(position)
    }
}