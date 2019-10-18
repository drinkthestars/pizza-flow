package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goofy.goober.databinding.EndFragmentBinding
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class EndFragment : Fragment() {

    private val parentViewModel: PizzaViewModel by sharedViewModel()
    private lateinit var binding: EndFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return EndFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .also { binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            config = parentViewModel.childRenderer.endConfig
        }
    }

    data class Config(val answer: String)
}
