package com.example.quizmeritoapp

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)