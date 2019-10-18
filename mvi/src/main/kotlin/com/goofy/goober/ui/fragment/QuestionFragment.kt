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
import com.goofy.goober.R
import com.goofy.goober.databinding.QuestionFragmentBinding
import com.goofy.goober.model.Question
import com.goofy.goober.ui.viewmodel.PizzaViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class QuestionFragment : Fragment() {

    private val parentViewModel: PizzaViewModel by sharedViewModel()
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
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            config = parentViewModel.childRenderer.questionConfig
        }
    }

    data class Config(
        val question: Question,
        val clickListener: (String) -> Unit
    )
}

@BindingAdapter("options")
fun LinearLayout.bindOptions(config: QuestionFragment.Config?) {
    if (config == null) return

    removeAllViewsInLayout()

    config.question.options.values.forEach { option ->
        Button(
            context
        ).apply {
            text = option
            isAllCaps = false
            setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.pizza_button_text_size)
            )
            setOnClickListener{
                config.clickListener(option)
            }
        }.also {
            addView(it)
            it.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = resources.getDimension(R.dimen.pizza_button_margin).toInt()
            }
        }
    }
    requestLayout()
}
