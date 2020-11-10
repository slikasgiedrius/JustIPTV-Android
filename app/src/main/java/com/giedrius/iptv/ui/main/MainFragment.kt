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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

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
        handleObservers()
    }

    private fun handleObservers() {
        viewModel.users.observe(viewLifecycleOwner, {
            context?.toast("Data fetched successfully")
            message.text = it.toString()
        })
        viewModel.error.observe(viewLifecycleOwner, {
            context?.toast("Error while fetching data")
        })
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}