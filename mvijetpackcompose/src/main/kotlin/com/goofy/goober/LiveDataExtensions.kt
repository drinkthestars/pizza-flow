package com.goofy.goober

import androidx.compose.effectOf
import androidx.compose.memo
import androidx.compose.onCommit
import androidx.compose.state
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe() = effectOf<T?> {
    val result = +state { value }
    val observer = +memo { Observer<T> { result.value = it } }

    +onCommit(this) {
        observeForever(observer)
        onDispose { removeObserver(observer) }
    }

    result.value
}
