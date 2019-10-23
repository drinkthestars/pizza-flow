package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.goofy.goober.R
import com.goofy.goober.databinding.QuestionFragmentBinding
import com.goofy.goober.model.Question
import com.goofy.goober.ui.bindConfig
import com.goofy.goober.ui.bindWithViewLifecycleOwner

class QuestionFragment : Fragment() {

    private val fragmentConfig: FragmentConfig by bindConfig()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return QuestionFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                bindWithViewLifecycleOwner { viewLifecycleOwner ->
                    lifecycleOwner = viewLifecycleOwner
                    viewConfig = fragmentConfig.questionConfig()
                }
            }
            .root
    }

    data class ViewConfig(
        val question: Question,
        val clickListener: (String) -> Unit
    )

    interface FragmentConfig {
        fun questionConfig(): LiveData<ViewConfig>
    }
}

@BindingAdapter("options")
internal fun LinearLayout.bindOptions(viewConfig: QuestionFragment.ViewConfig?) {
    if (viewConfig == null) return

    removeAllViewsInLayout()

    viewConfig.question.options.values.forEach { option ->
        Button(context).apply {
            text = option
            isAllCaps = false
            setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.pizza_button_text_size)
            )
            setOnClickListener { viewConfig.clickListener(option) }
        }.also {
            addView(it)
            it.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = resources.getDimension(R.dimen.pizza_button_margin).toInt()
            }
        }
    }
}
