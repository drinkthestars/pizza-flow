package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.goofy.goober.databinding.WelcomeFragmentBinding
import com.goofy.goober.ui.state.bindState

class WelcomeFragment : Fragment() {

    private val fragmentState: FragmentState by bindState()
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
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            state = fragmentState.welcomeState()
        }
    }

    data class State(
        val progressVisibility: Int,
        val welcomeVisibility: Int,
        val onStartClick: View.OnClickListener
    )

    interface FragmentState {
        fun welcomeState(): LiveData<State>
    }
}
