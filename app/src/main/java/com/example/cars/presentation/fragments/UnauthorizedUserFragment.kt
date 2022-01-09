package com.example.cars.presentation.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import com.example.cars.R

class UnauthorizedUserFragment : Fragment() {

    private lateinit var firstVideoView: VideoView
    private lateinit var secondVideoView: VideoView

    private lateinit var viewModel: UnauthorizedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_unauthorized_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firstVideoView = view.findViewById(R.id.firstVideoView)
        secondVideoView = view.findViewById(R.id.secondVideoView)

        viewModel = UnauthorizedViewModel(requireActivity().application)

        startVideos(viewModel.getRandomLink(), viewModel.getRandomLink())

        super.onViewCreated(view, savedInstanceState)
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