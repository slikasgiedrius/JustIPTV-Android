package com.giedrius.iptv.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.giedrius.iptv.R
import com.giedrius.iptv.utils.toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import logValidUrl
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initDatabase()
        handleObservers()
    }

    private fun handleObservers() {
        viewModel.users.observe(viewLifecycleOwner, {
            context?.toast("Data fetched successfully!")
            message.text = it.toString()
        })
        viewModel.error.observe(viewLifecycleOwner, {
            context?.toast("Error while fetching data!")
        })
    }

    private fun initDatabase() {
        database = Firebase.database.reference
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}