package com.example.cars

import android.app.Application
import androidx.room.Room
import com.example.cars.data.api.ApiService
import com.example.cars.data.database.UserDatabase
import com.example.cars.data.database.dao.UserDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    private lateinit var retrofit: Retrofit

    lateinit var db: UserDatabase
    lateinit var userDao: UserDao

    override fun onCreate() {
        super.onCreate()
        instance = this

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        db = Room.databaseBuilder(
            this,
            UserDatabase::class.java,
            DB_NAME
        ).build()

        userDao = db.getUserDao()
    }

    fun getRetrofit() : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    companion object {
        var instance: App? = null
            private set
    }
}