package com.example.cashguard

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cashguard.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserManager.init(this)
        setContentView(R.layout.activity_register)

        val etUsername = findViewById<EditText>(R.id.etRegUsername)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)
        val etName = findViewById<EditText>(R.id.etRegName)
        val btnRegister = findViewById<Button>(R.id.btnDoRegister)
        val btnGoLogin = findViewById<Button>(R.id.btnGoToLogin)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val name = etName.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "❌ Please fill username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (UserManager.findUser(username) != null) {
                Toast.makeText(this, "❌ Username already exists!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val finalName = if (name.isEmpty()) username else name
            val newUser = User(username, password, finalName)
            UserManager.addUser(newUser)
            Toast.makeText(this, "✅ Registration successful! Please login.", Toast.LENGTH_LONG).show()
            finish() // Go back to login
        }

        btnGoLogin.setOnClickListener {
            finish()
        }
    }
}