package com.example.notebook.feature_notebook.presentation.util

fun phoneNumberFormatter(number: String): String{
    return "+7 (${number[1]}${number[2]}${number[3]}) ${number[4]}${number[5]}${number[6]}-${number[7]}${number[8]}-${number[9]}${number[10]}"
}