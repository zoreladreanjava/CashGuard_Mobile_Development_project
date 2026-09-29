package com.example.cashguard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cashguard.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HistoryActivity : AppCompatActivity() {
    
    private lateinit var historyContainer: LinearLayout
    private val currentUser = UserManager.currentUser?.username ?: "demo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        
        TransactionManager.init(this)
        historyContainer = findViewById(R.id.historyContainer)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAddTransaction)
        
        loadTransactionHistory()

        fabAdd.setOnClickListener {
            showAddTransactionDialog()
        }

        setupBottomNav()
    }

    private fun loadTransactionHistory() {
        val transactions = TransactionManager.getTransactions(currentUser)
        
        // Remove old dynamic items (keep title at index 0)
        while (historyContainer.childCount > 1) {
            historyContainer.removeViewAt(1)
        }

        if (transactions.isEmpty()) {
            // Add default items if first time
            val now = System.currentTimeMillis()
            val default1 = Transaction("Food & Dining", "Lunch at Restaurant", "15.50", now - 1000)
            val default2 = Transaction("Electronics", "New Headphones", "120.00", now - 2000)
            
            TransactionManager.saveTransaction(currentUser, default1)
            TransactionManager.saveTransaction(currentUser, default2)

            
            // Re-load to get sorted list
            loadTransactionHistory()
        } else {
            // Display stored transactions (Manager already sorts them by timestamp)
            transactions.forEach { displayTransaction(it) }
        }
    }

    private fun showAddTransactionDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_transaction, null)
        val etCategory = dialogView.findViewById<EditText>(R.id.etCategory)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val etAmount = dialogView.findViewById<EditText>(R.id.etAmount)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val category = etCategory.text.toString().trim()
                val description = etDescription.text.toString().trim()
                val amount = etAmount.text.toString().trim()
                val now = System.currentTimeMillis()

                if (category.isNotEmpty() && amount.isNotEmpty()) {
                    val newTransaction = Transaction(category, description, amount, now)
                    TransactionManager.saveTransaction(currentUser, newTransaction)
                    
                    // Refresh the whole list to maintain order
                    loadTransactionHistory()
                    
                    Toast.makeText(this, "Transaction Added!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun displayTransaction(transaction: Transaction) {
        val itemView = LayoutInflater.from(this).inflate(R.layout.item_history, historyContainer, false)
        
        itemView.findViewById<TextView>(R.id.tvItemCategory).text = transaction.category
        itemView.findViewById<TextView>(R.id.tvItemDate).text = transaction.formattedDate
        itemView.findViewById<TextView>(R.id.tvItemDescription).text = if (transaction.description.isEmpty()) "No description" else transaction.description
        itemView.findViewById<TextView>(R.id.tvItemAmount).text = "-₱${transaction.amount}"

        historyContainer.addView(itemView) // Add at bottom because list is already sorted descending
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
        findViewById<Button>(R.id.btnNavTransactions).setOnClickListener {
            startActivity(Intent(this, TransactionsActivity::class.java))
            finish()
        }
        findViewById<Button>(R.id.btnNavHistory).setTextColor(getColor(R.color.white))
        
        findViewById<Button>(R.id.btnNavSettings).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}