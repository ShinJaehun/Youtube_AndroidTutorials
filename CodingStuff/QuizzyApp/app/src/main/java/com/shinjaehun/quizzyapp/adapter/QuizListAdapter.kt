package com.shinjaehun.quizzyapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.shinjaehun.quizzyapp.R
import com.shinjaehun.quizzyapp.databinding.EachQuizBinding
import com.shinjaehun.quizzyapp.model.QuizListModel
import com.shinjaehun.quizzyapp.util.loadImageFromDrawable
import com.shinjaehun.quizzyapp.util.loadImageFromUrl

class QuizListAdapter(
    val onItemClicked: (Int, QuizListModel) -> Unit
): RecyclerView.Adapter<QuizListAdapter.MyViewHolder>()  {

    private var quizList: MutableList<QuizListModel> = arrayListOf()

    inner class MyViewHolder(private val binding: EachQuizBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: QuizListModel) {
            binding.quizTitleList.text = quiz.title
            if(quiz.image.isNotEmpty()){
                binding.quizImageList.loadImageFromUrl(quiz.image)
            }else{
                binding.quizImageList.loadImageFromDrawable(R.drawable.ic_no_image)
            }
            binding.itemLayout.setOnClickListener {
                onItemClicked.invoke(adapterPosition, quiz)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = EachQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = quizList[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<QuizListModel>){
        this.quizList = list
        notifyDataSetChanged()
    }

}