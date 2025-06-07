package com.shinjaehun.onlinetictactoe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "GameViewMdoel"
class GameViewModel: ViewModel() {
    private var _gameModel : MutableLiveData<GameModel> = MutableLiveData()
    var gameModel: LiveData<GameModel> = _gameModel

    fun saveGameModel(model: GameModel) {
        Log.i(TAG, "gameModel: $model")
        _gameModel.postValue(model)
    }
}