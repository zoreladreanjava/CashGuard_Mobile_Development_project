package com.example.cashguard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cashguard.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserManager.init(this)

        // Check if already logged in
        val savedUsername = UserManager.getSavedUsername()
        if (savedUsername != null) {
            val user = UserManager.findUser(savedUsername)
            if (user != null) {
                UserManager.currentUser = user
                goToDashboard()
                return
            }
        }

        showLoginScreen()
    }

    private fun showLoginScreen() {
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etLoginUsername)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnDoLogin)
        val btnGoRegister = findViewById<Button>(R.id.btnGoToRegister) // Fixed ID mismatch

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            val user = UserManager.authenticate(username, password)
            if (user != null) {
                UserManager.saveLoginState(username)
                UserManager.currentUser = user
                Toast.makeText(this, "✅ Welcome, ${user.name}!", Toast.LENGTH_LONG).show()
                goToDashboard()
            } else {
                Toast.makeText(this, "❌ Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("username", UserManager.currentUser?.username)
        intent.putExtra("name", UserManager.currentUser?.name)
        startActivity(intent)
        finish()
    }
}