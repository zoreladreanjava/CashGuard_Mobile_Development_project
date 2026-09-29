package com.example.cashguard

import android.content.Context
import android.content.SharedPreferences

object TransactionManager {
    private const val PREFS_NAME = "CashGuardTransactions"
    private lateinit var sharedPrefs: SharedPreferences

    fun init(context: Context) {
        if (::sharedPrefs.isInitialized) return
        sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveTransaction(username: String, transaction: Transaction) {
        val transactions = getTransactions(username).toMutableList()
        transactions.add(transaction)
        val transactionSet = transactions.map { "${it.category}||${it.description}||${it.amount}||${it.timestamp}" }.toSet()
        sharedPrefs.edit().putStringSet(username, transactionSet).apply()
    }

    fun getTransactions(username: String): List<Transaction> {
        val transactionSet = sharedPrefs.getStringSet(username, emptySet<String>()) ?: emptySet()
        return transactionSet.map {
            val parts = it.split("||")
            if (parts.size == 4) {
                Transaction(parts[0], parts[1], parts[2], parts[3].toLong())
            } else {
                null
            }
        }.filterNotNull().sortedByDescending { it.timestamp }
    }
}