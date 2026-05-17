# 🛡️ CashGuard - Smart Budgeting System

**CashGuard** is a professional, user-friendly budgeting system designed to help users manage their finances effortlessly. Built with a focus on security and offline accessibility, it allows you to plan out expenses, set saving goals, and track your budget in real-time.

---

## 📸 Screenshots

| Login & Register | Dashboard | Financial Reports |
| :---: | :---: | :---: |
| <img src="docs/login.png" width="200" /> | <img src="docs/dashboard.png" width="200" /> | <img src="docs/reports.png" width="200" /> |

| Transactions | History | Settings |
| :---: | :---: | :---: |
| <img src="docs/transactions.png" width="200" /> | <img src="docs/history.png" width="200" /> | <img src="docs/settings.png" width="200" /> |

---

## ✨ Key Features

- **📊 Expense Tracking**: Record, categorize, and monitor your spending habits with ease.
- **🛡️ Data Security**: Ensures your financial information remains private and secure using local persistence.
- **🎯 Saving Goals**: Set and track personalized saving goals, such as Emergency Funds or Future Purchases.
- **📈 Financial Insights**: Visualize your progress through Saving Goal and Progress graphs on your dashboard.
- **⚙️ Secure Settings**: Manage Two-Factor Authentication (2FA), Biometric Login, and detailed activity logs.
- **🇵🇭 Localized for PH**: Fully integrated with Philippine Peso (₱) currency formatting.

---

## 🎨 Design System

The app follows a professional, modern aesthetic with a consistent theme:

- **Primary Background**: `#214457` (Dark Teal)
- **Primary Action Color**: `#26AC65` (Accent Green)
- **Typography**: `#FFFFFF` (Pure White) for maximum contrast
- **UI Components**:
    - **12dp Rounded Corners** on all cards and input fields.
    - **Semi-transparent overlays** for a modern, depth-focused feel.
    - **Floating Bottom Navigation** for seamless multi-screen access.

---

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: XML (Activities & Layouts)
- **Architecture**: Modular Activity-based design
- **Local Storage**: SharedPreferences with custom `UserManager` singleton
- **Design Components**: Material Design 3

---

## 📂 Project Structure

```text
CashGuard/
├── app/
│   ├── src/main/java/com/example/cashguard/
│   │   ├── LoginActivity.kt          # Auth entry point
│   │   ├── RegisterActivity.kt       # New user creation
│   │   ├── DashboardActivity.kt      # Main overview & Graphs
│   │   ├── ReportsActivity.kt        # Financial summaries
│   │   ├── TransactionsActivity.kt   # Card & Wallet details
│   │   ├── HistoryActivity.kt        # Itemized activity list
│   │   ├── ProfileActivity.kt        # Settings & Preferences
│   │   ├── UserManager.kt            # Central Logic & Data Controller
│   │   └── User.kt                   # Data Model
│   └── src/main/res/layout/          # Custom XML UI definitions
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (Hedgehog or newer recommended)
- Android SDK 34+
- Java 17

### Installation
1. **Clone the repository**
   ```bash
   git clone https://github.com/YourUsername/CashGuard.git
   ```
2. **Open in Android Studio**
   - Click `File > Open` and select the `CashGuard` folder.
3. **Sync & Build**
   - Click the **Elephant Icon** 🐘 (Sync Project with Gradle Files).
   - Go to `Build > Rebuild Project`.
4. **Run**
   - Select an emulator or physical device and click the **Play** ▶️ button.

---

## 📝 Credentials (Demo)
To quickly test the app, you can use:
- **Username**: `demo`
- **Password**: `1234`

---

## 🎓 School Project Credits
Developed as part of the Mobile Development course requirement.

---
© 2026 CashGuard Team
