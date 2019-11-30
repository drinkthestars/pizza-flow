package com.goofy.goober.ui

import androidx.fragment.app.Fragment
import com.goofy.goober.ui.viewmodel.FragmentConfigProvider

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> Fragment.bindConfig(fromParentFragment: Boolean = false): Lazy<T> {
    return lazy {
        if (fromParentFragment) {
            checkNotNull(requireParentFragment() as? FragmentConfigProvider<T>) {
                "Parent Fragment is not FragmentConfigProvider for ${T::class}"
            }
        } else {
            checkNotNull(requireActivity() as? FragmentConfigProvider<T>) {
                "Parent Activity is not a FragmentConfigProvider for ${T::class}"
            }
        }.provideFragmentConfigs()
    }
}
