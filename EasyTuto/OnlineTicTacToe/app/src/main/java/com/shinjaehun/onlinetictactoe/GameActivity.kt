package com.shinjaehun.onlinetictactoe

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shinjaehun.onlinetictactoe.databinding.ActivityGameBinding

private const val TAG = "GameActivity"

class GameActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityGameBinding
//    private var gameModel: GameModel? = null
    val viewModel: GameViewModel by viewModels()

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

        viewModel.gameModel.observe(this) {
            setUI(it)
        }

//        GameData.gameModel.observe(this) {
//            gameModel = it
//            setUI(it)
//        }
    }

    private fun setUI(gameModel: GameModel) {

            binding.btn0.text = gameModel.fieldPos[0]
            binding.btn1.text = gameModel.fieldPos[1]
            binding.btn2.text = gameModel.fieldPos[2]
            binding.btn3.text = gameModel.fieldPos[3]
            binding.btn4.text = gameModel.fieldPos[4]
            binding.btn5.text = gameModel.fieldPos[5]
            binding.btn6.text = gameModel.fieldPos[6]
            binding.btn7.text = gameModel.fieldPos[7]
            binding.btn8.text = gameModel.fieldPos[8]

            binding.startGameBtn.visibility = View.VISIBLE

            binding.gameStatusText.text =
                when (gameModel.gameStatus) {
                    GameStatus.CREATED -> {
                        binding.startGameBtn.visibility = View.INVISIBLE
                        "Game ID : " + gameModel.gameId
                    }
                    GameStatus.JOINED -> {
                        "Click on Start Game"
                    }
                    GameStatus.PROGRESS -> {
                        binding.startGameBtn.visibility = View.INVISIBLE
                        gameModel.currentPlayer + " turn"
                    }
                    GameStatus.FINISHED -> {
                        if (gameModel.winner.isNotEmpty()) gameModel.winner + " Won"
                        else "Draw"
                    }
                }
    }

    private fun startGame() {
        updateGameData(
            GameModel(
//                gameId = viewModel.gameModel.value.gameId,
                gameStatus = GameStatus.PROGRESS
            )
        )
    }

    private fun updateGameData(gameModel: GameModel){
        viewModel.saveGameModel(gameModel)
    }

    private fun checkForWinner(gameModel: GameModel){
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

        for(i in winningPos) {
            if(
                gameModel.fieldPos[i[0]] == gameModel.fieldPos[i[1]] &&
                gameModel.fieldPos[i[1]] == gameModel.fieldPos[i[2]] &&
                gameModel.fieldPos[i[0]].isNotEmpty()
            ) {
                gameModel.gameStatus = GameStatus.FINISHED
                gameModel.winner = gameModel.fieldPos[i[0]]
            }
        }
        if(gameModel.fieldPos.none(){ it.isEmpty() }) {
            gameModel.gameStatus = GameStatus.FINISHED
        }
//        updateGameData(gameModel)

//        viewModel.gameModel?.apply {
////            Log.i(TAG, "fieldPos: " + fieldPos)
//            for(i in winningPos) {
//                if(
//                    fieldPos[i[0]] == fieldPos[i[1]] &&
//                    fieldPos[i[1]] == fieldPos[i[2]] &&
//                    fieldPos[i[0]].isNotEmpty()
//                ) {
////                    Log.i(TAG, "fieldPos[i[0]]: " + fieldPos[i[0]])
////                    Log.i(TAG, "fieldPos[i[1]]: " + fieldPos[i[1]])
////                    Log.i(TAG, "fieldPos[i[2]]: " + fieldPos[i[2]])
//                    gameStatus = GameStatus.FINISHED
//                    winner = fieldPos[i[0]]
//                }
//            }
//            if(fieldPos.none(){ it.isEmpty() }){
//                gameStatus = GameStatus.FINISHED
//            }
//            updateGameData(this)
//        }
    }

    override fun onClick(v: View?) {
//        gameModel?.apply {
//            if(gameStatus!=GameStatus.PROGRESS) {
//                Toast.makeText(applicationContext, "Game not started", Toast.LENGTH_SHORT).show()
//                return
//            }
//            val clickedPos = (v?.tag as String).toInt()
//            if(fieldPos[clickedPos].isEmpty()) {
//                fieldPos[clickedPos] = currentPlayer
//                currentPlayer = if(currentPlayer == "X") "O" else "X"
//                checkForWinner()
//                updateGameData(this)
//            }
//        }
        val gameModel = viewModel.gameModel.value

        if(gameModel?.gameStatus != GameStatus.PROGRESS) {
            Toast.makeText(applicationContext, "Game not started", Toast.LENGTH_SHORT).show()
            return
        }
        val clickedPos = (v?.tag as String).toInt()
        if(gameModel.fieldPos[clickedPos].isEmpty()) {
            gameModel.fieldPos[clickedPos] = gameModel.currentPlayer
            gameModel.currentPlayer = if(gameModel.currentPlayer == "X") "O" else "X"
            checkForWinner(gameModel)
            updateGameData(gameModel)
        }
    }
}