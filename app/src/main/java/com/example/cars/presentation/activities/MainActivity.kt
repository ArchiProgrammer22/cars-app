package com.example.cars.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cars.App
import com.example.cars.FIRST_RUN
import com.example.cars.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = MainActivityViewModel(
            application,
            App.instance?.userDao!!
        )

        val prefs = getSharedPreferences(FIRST_RUN, 0)
        val editor = prefs.edit()
        val firstRun = prefs.getBoolean(FIRST_RUN, true)

        if(firstRun){
            editor.putBoolean(FIRST_RUN, false)
            editor.apply()

            viewModel.insertUser("dmitry", "test")
            viewModel.insertUser("vlad", "test")
            viewModel.insertUser("kirill", "test")
        }
    }
}