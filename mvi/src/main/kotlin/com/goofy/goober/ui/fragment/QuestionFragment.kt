package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.goofy.goober.databinding.QuestionFragmentBinding
import com.goofy.goober.ui.state.bindState
import com.goofy.goober.ui.view.QuestionView

class QuestionFragment : Fragment() {

    private val fragmentState: FragmentState by bindState()
    private lateinit var binding: QuestionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return QuestionFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .also { binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            questionViewState = fragmentState.questionState()
        }
    }

    interface FragmentState {
        fun questionState(): LiveData<QuestionView.State>
    }
}
