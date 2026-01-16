package com.example.quizmeritoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizSummary : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_summary)

        // Odbieranie danych o liczbie błędów
        val badAnswers = intent.getIntExtra("BAD_ANSWERS", 0)

        val badAnswersTextView = findViewById<TextView>(R.id.badAnswersTextView)
        badAnswersTextView.text = "Suma błędnie udzielonych odpowiedzi: $badAnswers"

        val backToMenuButton = findViewById<Button>(R.id.backToMenuButton)
        backToMenuButton.setOnClickListener {
            // Powrót do menu głównego i wyczyszczenie stosu aktywności
            val intent = Intent(this, Menu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}