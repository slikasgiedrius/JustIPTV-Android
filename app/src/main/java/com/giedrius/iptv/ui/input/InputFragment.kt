package com.giedrius.iptv.ui.input

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.giedrius.iptv.R
import com.giedrius.iptv.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.input_fragment.*

@AndroidEntryPoint
class InputFragment : Fragment(R.layout.input_fragment) {

    private val viewModel: InputViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        handleObservers()
        button.setOnClickListener {
            viewModel.validateUrl(editTextTextMultiLine.text.toString())
        }
    }

    private fun handleObservers() {
        viewModel.onUrlIsValid.observe(viewLifecycleOwner) {
            context?.toast("$it is valid url!")
            val action = InputFragmentDirections.actionInputFragmentToChannelsFragment(
                editTextTextMultiLine.text.toString()
            )
            view?.findNavController()?.navigate(action)
        }

        viewModel.onUrlIsInvalid.observe(viewLifecycleOwner) {
            it.message?.let { message -> context?.toast(message) }
        }
    }
}
