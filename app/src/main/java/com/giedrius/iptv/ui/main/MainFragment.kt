package com.giedrius.iptv.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

  override fun onViewCreated(
          view: View,
          savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    handleObservers()
  }

  private fun handleObservers() {
    viewModel.onDataReceived.observe(viewLifecycleOwner, {
        context?.toast("Data fetched successfully!")
        message.text = it.toString()
    })
    viewModel.onDataError.observe(viewLifecycleOwner, {
        context?.toast("Error while fetching data!")
    })
  }
}