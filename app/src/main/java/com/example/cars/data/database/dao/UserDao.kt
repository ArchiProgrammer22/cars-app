package com.example.cars.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.cars.data.database.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll() : List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getById(id: Long) : User

    @Query("SELECT * FROM users WHERE username = :username")
    fun getByUsername(username: String) : User

    @Delete
    fun deleteUser(user: User)

    @Insert
    fun insertUser(user: User)
}