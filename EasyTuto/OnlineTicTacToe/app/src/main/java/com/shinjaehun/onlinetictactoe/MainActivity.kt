package com.shinjaehun.onlinetictactoe

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.shinjaehun.onlinetictactoe.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playOfflineBtn.setOnClickListener {
            createOfflineGame()
        }

        binding.createOnlineGameBtn.setOnClickListener {
            createOnlineGame()
        }

        binding.joinOnlineGameBtn.setOnClickListener {
            joinOnlineGame()
        }
    }

    private fun joinOnlineGame() {
        val gameId = binding.gameIdInput.text.toString()
        if(gameId.isEmpty()) {
            binding.gameIdInput.setError("Please enter gameId")
            return //?
        }

        Firebase.firestore.collection("games")
            .document(gameId)
            .get()
            .addOnSuccessListener {
                val model = it?.toObject(GameModel::class.java)
                if (model == null) {
                    binding.gameIdInput.setError("Please enter valid game Id")
                } else {
                    model.gameStatus = GameStatus.JOINED
                    viewModel.saveGameModel(model)
                    startGame(gameId, "X")
                }
            }
    }

    private fun createOnlineGame() {
        val gameId = Random.nextInt(1000..9999).toString()
        viewModel.saveGameModel(
            GameModel(
                gameStatus = GameStatus.CREATED,
                gameId = gameId
            )
        )
        startGame(gameId, "O")
    }

    private fun createOfflineGame() {
        viewModel.saveGameModel(
            GameModel(
                gameStatus = GameStatus.JOINED
            )
        )
        startGame("-1", "X")
    }

    fun startGame(gameId: String, player: String?){
        startActivity(Intent(this, GameActivity::class.java).putExtra("id", gameId).putExtra("player", player))
    }
}