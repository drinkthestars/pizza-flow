package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.goofy.goober.databinding.EndFragmentBinding
import com.goofy.goober.ui.bindConfig

class EndFragment : Fragment() {

    private val fragmentConfig: FragmentConfig by bindConfig()
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
            viewConfig = fragmentConfig.endConfig()
        }
    }

    data class ViewConfig(val answer: String)

    interface FragmentConfig {
        fun endConfig(): LiveData<ViewConfig>
    }
}
