package com.giedrius.iptv.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.giedrius.iptv.R
import com.giedrius.iptv.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        handleObservers()
    }

    private fun handleObservers() {
        viewModel.onDataReceived.observe(
            viewLifecycleOwner,
            {
                context?.toast("Data fetched successfully!")
                message.text = it.toString()
            }
        )
        viewModel.onDataError.observe(
            viewLifecycleOwner,
            {
                context?.toast("Error while fetching data!")
            }
        )
    }
}
