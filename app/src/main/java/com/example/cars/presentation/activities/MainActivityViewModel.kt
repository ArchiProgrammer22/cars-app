package com.example.cars.presentation.activities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cars.data.database.dao.UserDao
import com.example.cars.data.database.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    app: Application,
    private val userDao: UserDao
) : AndroidViewModel(app) {

    fun insertUser(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.insertUser(User(username, password))
        }
    }
}