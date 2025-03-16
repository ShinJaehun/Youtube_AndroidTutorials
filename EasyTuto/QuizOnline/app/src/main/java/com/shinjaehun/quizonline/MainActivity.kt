package com.shinjaehun.quizonline

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.shinjaehun.quizonline.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var quizModelList: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()

    }

    private fun getDataFromFirebase() {

//        val listQuestionModel = mutableListOf<QuestionModel>()
//        listQuestionModel.add(QuestionModel("What is android?", mutableListOf("Language", "OS", "Product", "None"), "OS"))
//        listQuestionModel.add(QuestionModel("Who owns android?", mutableListOf("Apple", "Google", "Samsung", "Microsoft"), "Google"))
//        listQuestionModel.add(QuestionModel("Which assistant android uses?", mutableListOf("Siri", "Cortana", "Google Assistant", "None"), "Google Assistant"))
//
//        quizModelList.add(QuizModel("1", "Programming", "All the basic programming", "10", listQuestionModel))
//        quizModelList.add(QuizModel("2", "Computer", "All the computer questions", "20", listQuestionModel))
//        quizModelList.add(QuizModel("3", "Geography", "Boost your geographic knowledge", "15", listQuestionModel))
//        quizModelList.add(QuizModel("4", "Geography", "Boost your geographic knowledge", "15", listQuestionModel))
//        quizModelList.add(QuizModel("5", "Geography", "Boost your geographic knowledge", "15", listQuestionModel))
//        quizModelList.add(QuizModel("6", "Geography", "Boost your geographic knowledge", "15", listQuestionModel))
//        quizModelList.add(QuizModel("7", "Geography", "Boost your geographic knowledge", "15", listQuestionModel))
//        quizModelList.add(QuizModel("8", "Geography", "Boost your geographic knowledge", "15", listQuestionModel))

        binding.progressBar.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            quizModelList.add(quizModel)
                        }
                    }
                }
                setRecyclerView()
            }

    }

    private fun setRecyclerView() {
        binding.progressBar.visibility = View.GONE

        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}