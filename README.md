# CashGuard

  

CashGuard is an Android personal-finance and budgeting app built with Kotlin and Android Views. It demonstrates account registration and sign-in, a dashboard, local expense tracking, wallet and savings summaries, reports, and basic profile/settings actions. The app keeps its data on the device using Android `SharedPreferences` and app-private files; it does not use a remote service or synchronize data between devices.

  

> **Project status:** CashGuard is a course/demo project. Some balances are seeded sample values and some chart bars are illustrative mock data. Read [Current behavior and limitations](#current-behavior-and-limitations) before treating the figures or account handling as real financial records.

  

## Contents

  

-  [Features](#features)

-  [Screens and navigation](#screens-and-navigation)

-  [How data is stored](#how-data-is-stored)

-  [Technology](#technology)

-  [Project structure](#project-structure)

-  [Requirements](#requirements)

-  [Build and run](#build-and-run)

-  [Using the app](#using-the-app)

-  [Current behavior and limitations](#current-behavior-and-limitations)

-  [Privacy and security](#privacy-and-security)

-  [Contributing](#contributing)

-  [License and credits](#license-and-credits)

  

## Features

  

-  **Sign in and registration:** Sign in with a locally stored username and password, register a new local account, and retain a signed-in username between launches.

-  **Dashboard:** See progress against a sample savings goal and a spending budget, with progress bars calculated from locally stored values.

-  **Transaction history:** Add an expense with a category, optional description, and amount. Entries are ordered newest first and displayed with a formatted local date and time.

-  **Wallet summary:** View the wallet balance, deposits, and withdrawals. The Add Balance action increases the stored starting balance and deposit total.

-  **Reports:** View savings and spending totals and open separate savings and spending summary screens.

-  **Profile and settings:** View the signed-in user's display name, change the local password, inspect or clear the app activity log, and log out.

-  **Philippine peso presentation:** Financial amounts are presented with a peso symbol in the app UI.

-  **Offline operation:** The implemented account and finance data paths use local device storage and do not require a network connection.

  

## Screens and navigation

  

The app launches at the login screen. Its bottom navigation connects the primary sections:

  

| Screen | Purpose |
|  ---  |  ---  |
|  **Login**  | Authenticate a locally stored account or open registration. A saved login is restored on launch when its account is still available. |
|  **Register**  | Create a local account with a username, password, and optional display name. |
|  **Dashboard**  | Show savings-goal and spending-budget progress. |
|  **Reports**  | Show total savings and spending, with links to their detail screens. |
|  **Transactions**  | Show the wallet balance, deposit and withdrawal totals, and add balance. |
|  **History**  | Add and review categorized expense transactions. |
|  **Settings**  | Open profile details, change password, view/clear activity logs, or log out. |
|  **Savings summary**  | Show the savings total and an illustrative chart. |
|  **Spending summary**  | Show the spending total and an illustrative chart. |

  

## How data is stored

  

The app uses three small Kotlin singleton managers to keep storage and domain operations separate from the screen activities:

  

| Component | Responsibility | Storage |
|  ---  |  ---  |  ---  |
|  `UserManager`  | Loads accounts, registers users, authenticates credentials, updates passwords, and retains the signed-in username. |  `CashGuardPrefs` SharedPreferences |
|  `TransactionManager`  | Stores and retrieves a user's transactions, sorted by timestamp descending. |  `CashGuardTransactions` SharedPreferences |
|  `FinanceManager`  | Reads and updates starting wallet balance, deposits, savings, goal, and spending summaries. |  `CashGuardFinance` SharedPreferences |

  

`User` holds a username, password, and display name. `Transaction` holds a category, description, string amount, and timestamp; it derives a display date from that timestamp. Dashboard and settings activity entries are appended to the app-private `app_logs.txt` file.

  

## Technology

  

-  **Language:** Kotlin

-  **Platform:** Native Android application

-  **UI:** XML layouts with Android Views and AndroidX AppCompat

-  **UI components:** Material Components and ConstraintLayout

-  **Persistence:** Android SharedPreferences and app-private internal storage

-  **Build system:** Gradle Kotlin DSL and Android Gradle Plugin

-  **Namespace / application ID:**  `com.example.cashguard`

  

The app module uses compile SDK 34, target SDK 34, and minimum SDK 24. The project configures Java and Kotlin bytecode target 1.8. The root build declares Android Gradle Plugin 8.5.0 and Kotlin Android plugin 1.9.0.

  

## Project structure

  
CashGuard/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   │
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   │
│       │   ├── java/com/example/cashguard/
│       │   │   ├── LoginActivity.kt
│       │   │   ├── RegisterActivity.kt
│       │   │   ├── DashboardActivity.kt
│       │   │   ├── ReportsActivity.kt
│       │   │   ├── TransactionsActivity.kt
│       │   │   ├── HistoryActivity.kt
│       │   │   ├── TotalSavingsActivity.kt
│       │   │   ├── TotalSpendingActivity.kt
│       │   │   ├── ProfileActivity.kt
│       │   │   ├── UserProfileActivity.kt
│       │   │   │
│       │   │   ├── UserManager.kt
│       │   │   ├── FinanceManager.kt
│       │   │   ├── TransactionManager.kt
│       │   │   │
│       │   │   ├── User.kt
│       │   │   └── Transaction.kt
│       │   │
│       │   └── res/
│       │       ├── layout/
│       │       ├── drawable/
│       │       ├── mipmap-anydpi-v26/
│       │       ├── values/
│       │       ├── values-night/
│       │       └── xml/
│       │
│       ├── test/
│       └── androidTest/
│
├── gradle/
├── build.gradle.kts
├── gradle.properties
├── settings.gradle.kts
├── gradlew
└── README.md

  

## Requirements

  

- Android Studio with support for Android Gradle Plugin 8.5.0

- JDK 17 to run the configured Android Gradle Plugin and Gradle build; the app bytecode target remains Java 8

- Android SDK Platform 34 and Android Build Tools installed through SDK Manager

- An Android emulator or device running Android 7.0 (API 24) or newer

  

The Gradle wrapper is included, so a separate Gradle installation is not required. The first build may need internet access to download Gradle and declared dependencies.

  

## Build and run

  

1. Clone the repository and open its root directory in Android Studio:

  

```bash

git clone <repository-url>

cd CashGuard

```

  

2. Allow Android Studio to sync the Gradle project. Install Android SDK Platform 34 if prompted.

3. Select an API 24+ emulator or connect an Android device with USB debugging enabled.

4. Select the `app` run configuration and click **Run**.

  

To build a debug APK from a terminal in the project root:

  

```bash

./gradlew  assembleDebug

```

  

On Windows PowerShell, use:

  

```powershell

.\gradlew.bat assembleDebug

```

  

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

  

## Using the app

  

On a fresh installation, `UserManager` seeds a demo account if the local account list is empty:

  

| Username | Password |
|  ---  |  ---  |
|  `demo`  |  `1234`  |

  

You can also register another account from the login screen. The username and password are required; if the display name is blank, the username is used as the name. To record an expense, open **History**, tap the add button, and enter a category and amount. Description is optional. To add funds to the wallet, open **Transactions** and choose **Add Balance**. Use **Settings** to change the current password or log out.

  

## Current behavior and limitations

  

-  **Demo values:** The finance store initializes with a starting balance of PHP 100,000, savings of PHP 45,230, and a savings goal of PHP 100,000. The dashboard uses a fixed PHP 50,000 spending limit.

-  **Spending period:** Despite labels such as monthly, the current spending calculation sums all saved transactions; it does not filter timestamps to the current month.

-  **Illustrative charts:** Report and detail-screen bar heights use hard-coded sample series. They are not historical data derived from actual monthly transactions or savings.

-  **Initial history examples:** When a user has no transactions, opening History inserts two example transactions (dining and electronics) into that user's local history.

-  **Shared finance totals:** Finance values are stored in one app-wide preference file, not keyed by username. Account records and transaction lists are stored separately; switching accounts does not create separate balances or savings.

-  **Amounts:** Transaction amounts are stored as strings and summed by parsing them as numbers. The entry flow checks that category and amount are nonempty, but does not provide robust validation for currency format or transaction type.

-  **Local account model:** Registration and login are intended for demonstrating app flows. They are not connected to a server and do not provide production-grade identity management.

  

## Privacy and security

  

Data remains in app-local storage; no backend, cloud sync, or network API is configured in the current project. Local persistence alone does not make the app suitable for sensitive financial data. In particular, account passwords are stored as ordinary strings in SharedPreferences, and the app does not implement biometric authentication or two-factor authentication. Do not reuse real account passwords or enter confidential financial data into this demo build.

  

## Contributing

  

Contributions should keep the README and UI behavior in sync. For a change, create a branch, make a focused commit, and open a pull request with a summary and screenshots for visible UI changes. There is a local JVM test source set and an instrumented test source set; expand them when adding behavior that can be tested automatically.

  

## License and credits

  

CashGuard was developed as a mobile development course project. No license file is currently included in the repository; contact the project maintainers before redistributing it or using its assets outside the project.