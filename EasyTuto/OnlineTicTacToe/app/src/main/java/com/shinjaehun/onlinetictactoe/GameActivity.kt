package com.shinjaehun.onlinetictactoe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.shinjaehun.onlinetictactoe.databinding.ActivityGameBinding

private const val TAG = "GameActivity"

class GameActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityGameBinding
    private var gameModel: GameModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)

        binding.startGameBtn.setOnClickListener {
            startGame()
        }

        GameData.gameModel.observe(this) {
            gameModel = it
            setUI()
        }
    }

    private fun setUI() {
        gameModel?.apply {
            binding.btn0.text = fieldPos[0]
            binding.btn1.text = fieldPos[1]
            binding.btn2.text = fieldPos[2]
            binding.btn3.text = fieldPos[3]
            binding.btn4.text = fieldPos[4]
            binding.btn5.text = fieldPos[5]
            binding.btn6.text = fieldPos[6]
            binding.btn7.text = fieldPos[7]
            binding.btn8.text = fieldPos[8]

            binding.startGameBtn.visibility = View.VISIBLE

            binding.gameStatusText.text =
                when (gameStatus) {
                    GameStatus.CREATED -> {
                        binding.startGameBtn.visibility = View.INVISIBLE
                        "Game ID : " + gameId
                    }
                    GameStatus.JOINED -> {
                        "Click on Start Game"
                    }
                    GameStatus.PROGRESS -> {
                        binding.startGameBtn.visibility = View.INVISIBLE
                        currentPlayer + " turn"
                    }
                    GameStatus.FINISHED -> {
                        if (winner.isNotEmpty()) winner + " Won"
                        else "Draw"
                    }
                }
        }
    }

    private fun startGame() {
        gameModel?.apply {
            updateGameData(
                GameModel(
                    gameId = gameId,
                    gameStatus = GameStatus.PROGRESS
                )
            )
        }
    }

    private fun updateGameData(model: GameModel){
        GameData.saveGameModel(model)
    }

    private fun checkForWinner(){
        val winningPos = arrayOf(
            intArrayOf(0,1,2),
            intArrayOf(3,4,5),
            intArrayOf(6,7,8),
            intArrayOf(0,3,6),
            intArrayOf(1,4,7),
            intArrayOf(2,5,8),
            intArrayOf(0,4,8),
            intArrayOf(2,4,6),
        )

        gameModel?.apply {
//            Log.i(TAG, "fieldPos: " + fieldPos)
            for(i in winningPos) {
                if(
                    fieldPos[i[0]] == fieldPos[i[1]] &&
                    fieldPos[i[1]] == fieldPos[i[2]] &&
                    fieldPos[i[0]].isNotEmpty()
                ) {
//                    Log.i(TAG, "fieldPos[i[0]]: " + fieldPos[i[0]])
//                    Log.i(TAG, "fieldPos[i[1]]: " + fieldPos[i[1]])
//                    Log.i(TAG, "fieldPos[i[2]]: " + fieldPos[i[2]])
                    gameStatus = GameStatus.FINISHED
                    winner = fieldPos[i[0]]
                }
            }
            if(fieldPos.none(){ it.isEmpty() }){
                gameStatus = GameStatus.FINISHED
            }
            updateGameData(this)
        }
    }

    override fun onClick(v: View?) {
        gameModel?.apply {
            if(gameStatus!=GameStatus.PROGRESS) {
                Toast.makeText(applicationContext, "Game not started", Toast.LENGTH_SHORT).show()
                return
            }
            val clickedPos = (v?.tag as String).toInt()
            if(fieldPos[clickedPos].isEmpty()) {
                fieldPos[clickedPos] = currentPlayer
                currentPlayer = if(currentPlayer == "X") "O" else "X"
                checkForWinner()
                updateGameData(this)
            }
        }
    }
}