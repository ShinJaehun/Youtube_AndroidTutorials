package com.shinjaehun.roomapp.repository

import androidx.lifecycle.LiveData
import com.shinjaehun.roomapp.data.UserDao
import com.shinjaehun.roomapp.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    // interface 또는 abstract class로 정의해야 하는 거 아닌가요?
//    val readAllData: LiveData<List<User>> = userDao.readAllData()
    val readAllData: Flow<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

    fun searchUser(searchQuery: String): Flow<List<User>> = userDao.searchUser(searchQuery)
}