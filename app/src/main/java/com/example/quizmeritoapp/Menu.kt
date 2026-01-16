package com.example.quizmeritoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Menu : AppCompatActivity() {

    private var selectedContainerId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val container1 = findViewById<LinearLayout>(R.id.container1)
        val container2 = findViewById<LinearLayout>(R.id.container2)
        val container3 = findViewById<LinearLayout>(R.id.container3)
        val startButton = findViewById<Button>(R.id.startButton)

        val containers = listOf(container1, container2, container3)

        val clickListener = View.OnClickListener { view ->
            // Reset background for all containers
            for (container in containers) {
                container.setBackgroundResource(R.drawable.rounded_container)
            }

            // Set selected background for the clicked container
            view.setBackgroundResource(R.drawable.rounded_container_selected)
            selectedContainerId = view.id
        }

        container1.setOnClickListener(clickListener)
        container2.setOnClickListener(clickListener)
        container3.setOnClickListener(clickListener)

        startButton.setOnClickListener {
            val intent = when (selectedContainerId) {
                R.id.container1 -> Intent(this, QuizActivity::class.java)
                R.id.container2 -> Intent(this, InfoActivity::class.java)
                R.id.container3 -> Intent(this, AuthorActivity::class.java)
                else -> null
            }

            if (intent != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Proszę wybrać opcję", Toast.LENGTH_SHORT).show()
            }
        }
    }
}