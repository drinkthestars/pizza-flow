package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.goofy.goober.databinding.EndFragmentBinding
import com.goofy.goober.ui.state.bindState

class EndFragment : Fragment() {

    private val fragmentState: FragmentState by bindState()
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
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            state = fragmentState.endState()
        }
    }

    data class State(val answer: String)

    interface FragmentState {
        fun endState(): LiveData<State>
    }
}
