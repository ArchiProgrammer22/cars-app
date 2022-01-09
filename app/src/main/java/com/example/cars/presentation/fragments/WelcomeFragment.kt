package com.example.cars.presentation.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.example.cars.App
import com.example.cars.NAME
import com.example.cars.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment() {

    lateinit var usernameText: EditText
    lateinit var passwordText: EditText

    lateinit var signButton: Button
    lateinit var liveVideoButton: Button

    lateinit var viewModel: WelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        usernameText = view.findViewById(R.id.usernameText)
        passwordText = view.findViewById(R.id.passwordText)

        signButton = view.findViewById(R.id.signButton)
        liveVideoButton = view.findViewById(R.id.liveVideoButton)


        CoroutineScope(Dispatchers.IO).launch {
            viewModel = WelcomeViewModel(
                requireActivity().application,
                App.instance!!.userDao
            )
        }

        signButton.setOnClickListener {
            val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            val result = viewModel.checkUserData(
                usernameText.text.toString(),
                passwordText.text.toString()
            )
            if(result) {
                val bundle = Bundle()
                val fragment = MainFragment()
                bundle.putString(NAME, usernameText.text.toString())
                fragment.arguments = bundle
                changeFragment(fragment)
            }
        }

        liveVideoButton.setOnClickListener {
            changeFragment(UnauthorizedUserFragment())
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun changeFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}