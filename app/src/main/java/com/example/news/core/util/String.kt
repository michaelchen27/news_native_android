package com.example.news.core.util

fun String.toTitleCase(): String {
    return split(" ").joinToString(" ") { it.capitalize() }
}