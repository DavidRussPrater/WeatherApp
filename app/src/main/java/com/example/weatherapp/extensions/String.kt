package com.example.weatherapp.extensions

fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercaseChar() } }
