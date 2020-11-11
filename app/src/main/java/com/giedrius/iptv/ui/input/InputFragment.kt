package com.giedrius.iptv.ui.input

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.giedrius.iptv.R
import com.giedrius.iptv.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.input_fragment.*

@AndroidEntryPoint
class InputFragment : Fragment() {

    private val viewModel: InputViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.input_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleObservers()
        editTextTextMultiLine.setText("http://uran.iptvboss.net:80/get.php?username=GiedriusSlikas&password=GiedriusSlikas&type=m3u_plus&output=ts")
        button.setOnClickListener {
//            viewModel.validateUrl(editTextTextMultiLine.text.toString())
            val action = InputFragmentDirections.actionInputFragmentToChannelsFragment(editTextTextMultiLine.text.toString())
            view.findNavController().navigate(action)
        }
    }

    private fun handleObservers() {
        viewModel.onUrlIsValid.observe(viewLifecycleOwner, {
            context?.toast("URL is valid!")
        })
        viewModel.onUrlIsInvalid.observe(viewLifecycleOwner, {
            context?.toast("URL is invalid :(")
        })
    }

    private fun navigateToChannelsFragment() {

        context?.toast("URL is valid!")
    }
}