package com.goofy.goober

private val String.sauce: String
    get() = if (this == "white") "a $this" else this

private val String.style: String
    get() = if (this == "thin as paper") this else "a $this"

fun List<String>.makeAnswer(): String {
    return "Your pizza's ${this[0].style} with: \n" +
            "${this[1].sauce} sauce,  ${this[2]}, and ${this[3]}"
}
