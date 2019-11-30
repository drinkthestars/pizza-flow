package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.goofy.goober.databinding.WelcomeFragmentBinding
import com.goofy.goober.ui.bindConfig

class WelcomeFragment : Fragment() {

    private val fragmentConfig: FragmentConfig by bindConfig()
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
            viewConfig = fragmentConfig.welcomeConfig()
        }
    }

    data class ViewConfig(
        val progressVisibility: Int,
        val welcomeVisibility: Int,
        val onStartClick: View.OnClickListener
    )

    interface FragmentConfig {
        fun welcomeConfig(): LiveData<ViewConfig>
    }
}
