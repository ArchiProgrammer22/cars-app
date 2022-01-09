package com.example.cars.presentation.fragments

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.cars.R
import com.example.cars.data.database.dao.UserDao
import kotlinx.coroutines.*

class WelcomeViewModel(
    private val app: Application,
    private val userDao: UserDao
) : AndroidViewModel(app) {

    fun checkUserData(usernameText: String, passwordText: String) : Boolean {
        if (usernameText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(
                getApplication(), R.string.data_not_valid, Toast.LENGTH_SHORT
            ).show()
            return false
        }

        val userDeferred = CoroutineScope(Dispatchers.IO).async {
            userDao.getByUsername(usernameText)
        }
        val user = runBlocking {
            userDeferred.await()
        }

        if(user == null){
            Toast.makeText(app, R.string.user_not_found, Toast.LENGTH_SHORT).show()
            return false
        }

        if(user.password == passwordText) return true
        Toast.makeText(app, R.string.invalid_password, Toast.LENGTH_SHORT).show()
        return false
    }
}