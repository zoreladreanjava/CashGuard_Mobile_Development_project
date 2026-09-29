package com.example.cashguard

import android.content.Context
import android.content.SharedPreferences

object FinanceManager {
    private const val PREFS_NAME = "CashGuardFinance"
    private lateinit var sharedPrefs: SharedPreferences

    fun init(context: Context) {
        if (::sharedPrefs.isInitialized) return
        sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        // Set default values if first time
        if (!sharedPrefs.contains("initial_balance")) {
            sharedPrefs.edit()
                .putFloat("initial_balance", 100000f)
                .putFloat("total_savings", 45230f)
                .putFloat("savings_goal", 100000f)
                .apply()
        }
    }

    fun getWalletBalance(username: String): Double {
        val currentBalance = sharedPrefs.getFloat("initial_balance", 100000f).toDouble()
        val totalSpending = TransactionManager.getTransactions(username).sumOf { it.amount.toDoubleOrNull() ?: 0.0 }
        return currentBalance - totalSpending
    }

    fun addBalance(amount: Double) {
        val current = sharedPrefs.getFloat("initial_balance", 100000f)
        val currentDeposits = sharedPrefs.getFloat("total_deposits_added", 0f)
        sharedPrefs.edit()
            .putFloat("initial_balance", current + amount.toFloat())
            .putFloat("total_deposits_added", currentDeposits + amount.toFloat())
            .apply()
    }

    fun getTotalDeposits(): Double {
        return sharedPrefs.getFloat("total_deposits_added", 0f).toDouble()
    }

    fun getTotalWithdrawals(username: String): Double {
        return TransactionManager.getTransactions(username).sumOf { it.amount.toDoubleOrNull() ?: 0.0 }
    }

    fun getTotalSavings(): Double {
        return sharedPrefs.getFloat("total_savings", 45230f).toDouble()
    }

    fun getSavingsGoal(): Double {
        return sharedPrefs.getFloat("savings_goal", 100000f).toDouble()
    }

    fun getMonthlySpending(username: String): Double {
        // Simple logic for demo: sum transactions from the current month
        // In a real app, we'd check the timestamp. For now, let's sum all.
        return TransactionManager.getTransactions(username).sumOf { it.amount.toDoubleOrNull() ?: 0.0 }
    }
}