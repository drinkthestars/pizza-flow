package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goofy.goober.databinding.WelcomeFragmentBinding
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class WelcomeFragment : Fragment() {

    private val parentViewModel: PizzaViewModel by sharedViewModel()
    private lateinit var binding: WelcomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return WelcomeFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .also { binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            config = parentViewModel.childRenderer.welcomeConfig
        }
    }

    data class Config(
        val progressVisibility: Int,
        val welcomeVisibility: Int,
        val onStartClick: View.OnClickListener
    )
}
