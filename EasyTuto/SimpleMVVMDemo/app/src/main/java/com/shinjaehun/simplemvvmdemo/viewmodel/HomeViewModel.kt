package com.shinjaehun.simplemvvmdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shinjaehun.simplemvvmdemo.model.UserData
import com.shinjaehun.simplemvvmdemo.model.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val userRepository: UserRepository = UserRepository()

//    private val _userData = MutableLiveData<UserData>()
//    val userData: LiveData<UserData> = _userData

    private val _userData = MutableStateFlow<UserData?>(null)
    val userData: StateFlow<UserData?> = _userData.asStateFlow()

//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getUserData() {
//        _isLoading.postValue(true)
        _isLoading.value = true

        viewModelScope.launch {
            val userResult = userRepository.fetchUserData()
//            _isLoading.postValue(false)
//            _userData.postValue(userResult)
            _isLoading.value = false
            _userData.value = userResult
        }
    }
}