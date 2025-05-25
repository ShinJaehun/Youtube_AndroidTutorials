package com.shinjaehun.simplemvvmdemo.model

import kotlinx.coroutines.delay

class UserRepository {

    suspend fun fetchUserData(): UserData{
        delay(2000)
        return UserData("Shin", 45)
    }
}