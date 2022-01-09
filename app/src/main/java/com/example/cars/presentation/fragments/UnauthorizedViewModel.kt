package com.example.cars.presentation.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.cars.VIDEOS_JSON
import com.example.cars.model.Videos
import com.google.gson.GsonBuilder
import kotlin.random.Random

class UnauthorizedViewModel(
    app: Application
) : AndroidViewModel(app) {

    fun getRandomLink() : String{
        val builder = GsonBuilder()
        builder.setLenient()

        val json = builder.create()
        val video = json.fromJson(VIDEOS_JSON, Videos::class.java)
        val videos = video.videos
        val randInt = Random.nextInt(0, videos.size)

        return videos[randInt].sources[0]
    }
}