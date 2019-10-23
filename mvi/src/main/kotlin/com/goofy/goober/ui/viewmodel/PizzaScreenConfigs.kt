package com.goofy.goober.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goofy.goober.ui.fragment.EndFragment
import com.goofy.goober.ui.fragment.QuestionFragment
import com.goofy.goober.ui.fragment.WelcomeFragment

class PizzaScreenConfigs : PizzaChildFragmentConfigs {

    private val welcomeConfig = MutableLiveData<WelcomeFragment.ViewConfig>()

    private val questionConfig = MutableLiveData<QuestionFragment.ViewConfig>()

    private val endConfig = MutableLiveData<EndFragment.ViewConfig>()

    fun updateWelcomeConfig(newConfig: WelcomeFragment.ViewConfig) {
        welcomeConfig.value = newConfig
    }

    fun updateQuestionConfig(newConfig: QuestionFragment.ViewConfig) {
        questionConfig.value = newConfig
    }

    fun updateEndConfig(newConfig: EndFragment.ViewConfig) {
        endConfig.value = newConfig
    }

    override fun welcomeConfig(): LiveData<WelcomeFragment.ViewConfig> = welcomeConfig
    override fun questionConfig(): LiveData<QuestionFragment.ViewConfig> = questionConfig
    override fun endConfig(): LiveData<EndFragment.ViewConfig> = endConfig
}

interface FragmentConfigProvider<T : Any> {
    fun provideFragmentConfigs(): T
}

interface PizzaChildFragmentConfigs :
    WelcomeFragment.FragmentConfig,
    EndFragment.FragmentConfig,
    QuestionFragment.FragmentConfig
