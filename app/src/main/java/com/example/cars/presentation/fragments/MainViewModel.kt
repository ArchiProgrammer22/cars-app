package com.example.cars.presentation.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cars.VIDEOS_JSON
import com.example.cars.data.api.ApiService
import com.example.cars.model.Cars
import com.example.cars.model.Videos
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class MainViewModel(
    app: Application,
    private val apiService: ApiService,
) : AndroidViewModel(app) {

    fun updateData() : Cars {
        val resultDeferred = CoroutineScope(Dispatchers.IO).async {
            apiService.getCars().body()
        }
        var result: Cars?
        runBlocking {
            result = resultDeferred.await()
        }
        return result!!
    }

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