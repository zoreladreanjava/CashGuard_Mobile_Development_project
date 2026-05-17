package com.example.cashguard

import android.content.Context
import android.content.SharedPreferences

object UserManager {
    private const val PREFS_NAME = "CashGuardPrefs"
    private lateinit var sharedPrefs: SharedPreferences
    private val _users = mutableListOf<User>()
    val users: List<User> get() = _users

    var currentUser: User? = null

    fun init(context: Context) {
        if (::sharedPrefs.isInitialized) return
        sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadUsers()
    }

    private fun loadUsers() {
        _users.clear()
        val usersSet = sharedPrefs.getStringSet("users", emptySet<String>())
        usersSet?.forEach { userData ->
            val parts = userData.split("||")
            if (parts.size == 3) {
                _users.add(User(parts[0], parts[1], parts[2]))
            }
        }

        if (_users.isEmpty()) {
            _users.add(User("demo", "1234", "Demo User"))
            saveUsers()
        }
    }

    fun saveUsers() {
        val usersSet = _users.map { "${it.username}||${it.password}||${it.name}" }.toSet()
        sharedPrefs.edit().putStringSet("users", usersSet).apply()
    }

    fun addUser(user: User) {
        _users.add(user)
        saveUsers()
    }

    fun updateUser(updatedUser: User) {
        val index = _users.indexOfFirst { it.username == updatedUser.username }
        if (index != -1) {
            _users[index] = updatedUser
            saveUsers()
            if (currentUser?.username == updatedUser.username) {
                currentUser = updatedUser
            }
        }
    }

    fun saveLoginState(username: String) {
        sharedPrefs.edit().putString("loggedInUsername", username).apply()
    }

    fun getSavedUsername(): String? {
        return sharedPrefs.getString("loggedInUsername", null)
    }

    fun clearLoginState() {
        sharedPrefs.edit().remove("loggedInUsername").apply()
        currentUser = null
    }

    fun findUser(username: String): User? {
        return _users.find { it.username == username }
    }

    fun authenticate(username: String, password: String): User? {
        return _users.find { it.username == username && it.password == password }
    }
}