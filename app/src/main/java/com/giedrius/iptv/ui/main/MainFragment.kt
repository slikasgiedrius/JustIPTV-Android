package com.giedrius.iptv.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.giedrius.iptv.R
import com.giedrius.iptv.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

    companion object {
        fun newInstance() = MainFragment()
    }
}