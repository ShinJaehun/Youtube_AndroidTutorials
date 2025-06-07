package com.shinjaehun.onlinetictactoe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

private const val TAG = "GameViewMdoel"
class GameViewModel: ViewModel() {
    private val _gameModel : MutableLiveData<GameModel> = MutableLiveData()
    val gameModel: LiveData<GameModel> = _gameModel

    fun saveGameModel(model: GameModel) {
        Log.i(TAG, "saveGameModel: $model")
        _gameModel.postValue(model)

        if(model.gameId != "-1") {
            Firebase.firestore.collection("games")
                .document(model.gameId)
                .set(model)
        }
    }

    fun fetchGameModel(gameId: String) {
        Firebase.firestore.collection("games")
            .document(gameId)
            .addSnapshotListener { value, error ->
                val model = value?.toObject(GameModel::class.java)
                _gameModel.postValue(model)
            }
        Log.i(TAG, "fetchGameModel: ${gameModel.value}")
    }
}