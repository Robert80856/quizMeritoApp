package com.example.quizmeritoapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class QuizActivity : AppCompatActivity() {

    private val questions = listOf(
        Question("Jak brzmi obecna pełna nazwa dawnej Wyższej Szkoły Bankowej w Gdańsku?", listOf("Akademia Bankowa w Gdańsku", "Uniwersytet WSB Merito Gdańsk", "Gdańska Szkoła Biznesu", "Pomorski Uniwersytet Ekonomiczny"), 1),
        Question("Przy której głównej arterii komunikacyjnej Gdańska znajduje się kampus uczelni?", listOf("ul. Marynarki Polskiej", "al. Zwycięstwa", "al. Grunwaldzka", "ul. Kartuska"), 2),
        Question("Który z wymienionych kierunków jest jednym z najpopularniejszych na gdańskiej filii WSB Merito?", listOf("Informatyka", "Górnictwo", "Astronomia", "Oceanografia"), 0),
        Question("W której dzielnicy Gdańska zlokalizowane są główne budynki WSB Merito?", listOf("Oliwa", "Wrzeszcz", "Przymorze", "Śródmieście"), 1),
        Question("Jaki status uzyskała uczelnia w 2023 roku, co skutkowało zmianą nazwy?", listOf("Kolegium", "Akademia", "Szkoła Policealna", "Uniwersytet"), 3),
        Question("Co wyróżnia model kształcenia na WSB Merito?", listOf("Tylko zajęcia teoretyczne", "Praktyczne podejście i współpraca z biznesem", "Brak praktyk studenckich", "Wyłącznie egzaminy ustne"), 1),
        Question("Jak nazywa się system online służący studentom WSB Merito do sprawdzania ocen i planu zajęć?", listOf("Extranet", "Facebook", "EduPortal", "Studnet"), 0),
        Question("W bezpośrednim sąsiedztwie jakiego przystanku SKM znajduje się uczelnia?", listOf("Gdańsk Główny", "Gdańsk Oliwa", "Gdańsk Zaspa", "Gdańsk Wrzeszcz"), 2),
        Question("Czy WSB Merito Gdańsk oferuje studia w trybie Dualnym (łączącym naukę z pracą)?", listOf("Tak", "Nie", "Tylko dla obcokrajowców", "Tylko na kierunku Medycyna"), 0),
        Question("Do której grupy uczelni należy WSB Merito Gdańsk?", listOf("Grupa Vistula", "Grupa Merito", "Grupa Edukacyjna Żak", "Kolegium Jagiellońskie"), 1),
        Question("Kto jest założycielem Uniwersytetu WSB Merito (dawniej WSB)?", listOf("Skarb Państwa", "Centrum Edukacji TEB Akademia", "Miasto Gdańsk", "Uniwersytet Gdański"), 1),
        Question("Gdzie studenci WSB Merito Gdańsk mogą uzyskać pomoc w znalezieniu stażu lub pierwszej pracy?", listOf("W Dziekanacie", "W Biurze Karier", "W Kwesturze", "W Bibliotece"), 1),
        Question("Który z budynków gdańskiego kampusu charakteryzuje się nowoczesną, przeszkloną elewacją i został oddany do użytku jako drugi duży obiekt?", listOf("Budynek A", "Budynek B", "Budynek C", "Budynek D"), 1),
        Question("Z jakiej popularnej platformy e-learningowej korzystają studenci WSB Merito Gdańsk do pobierania materiałów z wykładów?", listOf("Moodle", "Slack", "Discord", "Trello"), 0),
        Question("Czy na Uniwersytecie WSB Merito w Gdańsku można studiować kierunki techniczne kończące się tytułem inżyniera?", listOf("Nie, tylko licencjat", "Tak, np. na Informatyce lub Logistyce", "Tylko na studiach podyplomowych", "Tylko w trybie zaocznym"), 1),
        Question("Jak nazywa się program umożliwiający studentom wyjazd na semestr studiów lub praktyki do innego kraju europejskiego?", listOf("Work and Travel", "Erasmus+", "Socrates", "Euro-Student"), 1),
        Question("W jakim mieście znajduje się siedziba główna Federacji Naukowej WSB-DSW, której częścią jest gdańska uczelnia?", listOf("Wrocław", "Warszawa", "Poznań", "Gdańsk"), 2),
        Question("Który z poniższych kierunków studiów II stopnia (magisterskich) jest dostępny w ofercie WSB Merito Gdańsk?", listOf("Lekarski", "Zarządzanie", "Prawo Kanoniczne", "Weterynaria"), 1),
        Question("Z jakiego oprogramowania do komunikacji i zajęć online (część pakietu Office 365) korzystają studenci uczelni?", listOf("Zoom", "Skype", "Microsoft Teams", "Google Meet"), 2),
        Question("W którym roku Wyższa Szkoła Bankowa w Gdańsku rozpoczęła swoją działalność?", listOf("1991", "2005", "1998", "2012"), 2),
    )

    private var currentQuestionIndex = 0
    private var questionCounter = 1
    private var selectedOptionIndex = -1
    private var badAnswersInCurrentQuestion = 0
    private var badAnswers = 0
    private lateinit var shuffledQuestions: List<Question>

    private lateinit var questionTextView: TextView
    private lateinit var progressTextView: TextView
    private lateinit var statusImageView: ImageView
    private lateinit var confirmButton: Button
    private lateinit var optionButtons: List<MaterialButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        shuffledQuestions = questions.shuffled().take(10)

        questionTextView = findViewById(R.id.questionTextView)
        progressTextView = findViewById(R.id.progressTextView)
        statusImageView = findViewById(R.id.statusImageView)
        confirmButton = findViewById(R.id.confirmButton)
        
        optionButtons = listOf(
            findViewById(R.id.option1Button),
            findViewById(R.id.option2Button),
            findViewById(R.id.option3Button),
            findViewById(R.id.option4Button)
        )

        setupOptionButtons()
        
        confirmButton.setOnClickListener {
            handleConfirmClick()
        }

        findViewById<View>(R.id.backButton).setOnClickListener { finish() }

        displayQuestion()
    }

    private fun setupOptionButtons() {
        for (i in optionButtons.indices) {
            optionButtons[i].setOnClickListener {
                selectedOptionIndex = i
                updateButtonsAppearance()
            }
        }
    }

    private fun updateButtonsAppearance() {
        for (i in optionButtons.indices) {
            if (i == selectedOptionIndex) {
                optionButtons[i].setIconResource(android.R.drawable.radiobutton_on_background)
                optionButtons[i].iconTint = ColorStateList.valueOf(Color.parseColor("#1E88E5"))
            } else {
                optionButtons[i].setIconResource(android.R.drawable.radiobutton_off_background)
                optionButtons[i].iconTint = ColorStateList.valueOf(Color.WHITE)
            }
        }
    }

    private fun displayQuestion() {
        val currentQuestion = shuffledQuestions[currentQuestionIndex]
        questionTextView.text = currentQuestion.text
        progressTextView.text = "Pytanie $questionCounter/10"
        
        selectedOptionIndex = -1
        badAnswersInCurrentQuestion = 0
        statusImageView.visibility = View.INVISIBLE
        
        for (i in optionButtons.indices) {
            optionButtons[i].text = currentQuestion.options[i]
            optionButtons[i].setIconResource(android.R.drawable.radiobutton_off_background)
            optionButtons[i].iconTint = ColorStateList.valueOf(Color.WHITE)
            optionButtons[i].isEnabled = true
        }
        confirmButton.text = "Zatwierdź"
        confirmButton.setOnClickListener { handleConfirmClick() }
    }

    private fun handleConfirmClick() {
        if (selectedOptionIndex == -1) return

        val currentQuestion = shuffledQuestions[currentQuestionIndex]
        
        if (selectedOptionIndex == currentQuestion.correctAnswerIndex) {
            // POPRAWNA ODPOWIEDŹ
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.pass)
            statusImageView.setColorFilter(null)
            
            confirmButton.text = "Następne pytanie"
            confirmButton.setOnClickListener {
                moveToNextQuestion()
            }
            optionButtons.forEach { it.isEnabled = false }
        } else {
            // BŁĘDNA ODPOWIEDŹ
            badAnswersInCurrentQuestion++
            badAnswers++
            statusImageView.visibility = View.VISIBLE
            statusImageView.setColorFilter(null)
            
            if (badAnswersInCurrentQuestion == 1) {
                statusImageView.setImageResource(R.drawable.fail)
            } else {
                statusImageView.setImageResource(R.drawable.fail2)
            }
        }
    }

    private fun moveToNextQuestion() {
        currentQuestionIndex++
        questionCounter++

        if (currentQuestionIndex < shuffledQuestions.size && questionCounter <= 10) {
            displayQuestion()
        } else {
            val intent = Intent(this, QuizSummary::class.java)
            intent.putExtra("BAD_ANSWERS", badAnswers)
            startActivity(intent)
            finish()
        }
    }
}
