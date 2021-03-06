package com.example.cars.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo()
    val username: String,
    val password: String,
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}