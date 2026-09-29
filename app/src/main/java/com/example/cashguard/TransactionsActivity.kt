package com.example.cashguard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cashguard.R

class TransactionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)
        
        FinanceManager.init(this)
        TransactionManager.init(this)
        val currentUser = UserManager.currentUser?.username ?: "demo"

        findViewById<TextView>(R.id.tvTransactionUser).text = UserManager.currentUser?.name ?: "User"
        
        // Dynamic Balance
        updateBalanceDisplay()
        
        findViewById<Button>(R.id.btnAddBalance).setOnClickListener {
            showAddBalanceDialog()
        }
        
        setupBottomNav()
    }

    private fun updateBalanceDisplay() {
        val currentUser = UserManager.currentUser?.username ?: "demo"
        val balance = FinanceManager.getWalletBalance(currentUser)
        findViewById<TextView>(R.id.tvWalletBalance).text = "₱%,.2f".format(balance)
        
        // Update Summaries
        val totalDeposits = FinanceManager.getTotalDeposits()
        val totalWithdrawals = FinanceManager.getTotalWithdrawals(currentUser)
        findViewById<TextView>(R.id.tvTotalDeposits).text = "₱%,.2f".format(totalDeposits)
        findViewById<TextView>(R.id.tvTotalWithdrawals).text = "₱%,.2f".format(totalWithdrawals)
    }

    private fun showAddBalanceDialog() {
        val etAmount = EditText(this)
        etAmount.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        etAmount.hint = "Enter amount"
        
        AlertDialog.Builder(this)
            .setTitle("Add Balance")
            .setView(etAmount)
            .setPositiveButton("Add") { _, _ ->
                val amount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
                if (amount > 0) {
                    FinanceManager.addBalance(amount)
                    updateBalanceDisplay()
                    Toast.makeText(this, "Balance Updated!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setupBottomNav() {
        findViewById<Button>(R.id.btnNavHome).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        findViewById<Button>(R.id.btnNavReports).setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
            finish()
        }
        findViewById<Button>(R.id.btnNavTransactions).setTextColor(getColor(R.color.white))
        
        findViewById<Button>(R.id.btnNavHistory).setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            finish()
        }
        findViewById<Button>(R.id.btnNavSettings).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}