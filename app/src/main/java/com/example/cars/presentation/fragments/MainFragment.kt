
package com.example.cars.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cars.*
import com.example.cars.model.Car
import com.example.cars.model.Cars
import com.example.cars.presentation.adapters.CarRecyclerViewAdapter

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var secondsText: TextView
    private lateinit var usernameText: TextView
    private lateinit var logOutText: TextView
    private lateinit var viewModel: MainViewModel
    private lateinit var filterSpinner: Spinner
    private lateinit var firstVideoView: VideoView
    private lateinit var secondVideoView: VideoView

    private val list = arrayOf("no filter", "available", "hidden", "disabled")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerView)
        secondsText = view.findViewById(R.id.timerText)
        usernameText = view.findViewById(R.id.usernamePanelText)
        logOutText = view.findViewById(R.id.logOutText)
        filterSpinner = view.findViewById(R.id.filterSpinner)
        firstVideoView = view.findViewById(R.id.firstVideoViewMain)
        secondVideoView = view.findViewById(R.id.secondVideoViewMain)

        usernameText.append( arguments?.getString(NAME))

        viewModel = MainViewModel(requireActivity().application,
            App.instance?.getRetrofit()!!
        )

        filterSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            list
        )
        recyclerView.adapter = adapterUpdate()
        startVideos(viewModel.getRandomLink(), viewModel.getRandomLink())

        recyclerView.layoutManager = LinearLayoutManager(activity)

        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsText.text = activity?.resources
                    ?.getString(R.string.stopwatch, (millisUntilFinished / 1000).toInt())
            }
            override fun onFinish() {
                recyclerView.adapter = adapterUpdate()
                startVideos(viewModel.getRandomLink(), viewModel.getRandomLink())
                start()
            }
        }.start()

        logOutText.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, WelcomeFragment())
                .commit()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun adapterUpdate() : CarRecyclerViewAdapter{
        val response = viewModel.updateData()
        var anotherList: List<Car> = response.cars
        if(filterSpinner.selectedItem != list[0]) {
            anotherList = response.cars.filter {
                it.state == filterSpinner.selectedItem.toString()
            }
        }
        val adapter = CarRecyclerViewAdapter(Cars(anotherList))
        adapter.setOnItemClickListener(object : CarRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(number: String, status: String, date: String) {
                FullInfoDialogFragment(number, status, date)
                    .show(parentFragmentManager, DIALOG)
            }
        })
        return adapter
    }

    private fun startVideos(firstLink: String, secondLink: String){
        val firstUri = Uri.parse(firstLink)
        val secondUri = Uri.parse(secondLink)

        firstVideoView.setVideoURI(firstUri)
        secondVideoView.setVideoURI(secondUri)
        firstVideoView.start()
        secondVideoView.start()
        secondVideoView.setOnPreparedListener {
            it.setVolume(0f, 0f)
        }
    }
}