# Mobile Data Logger (AI-Ready)

A full-stack mobile application that allows users to log health, activity, and sleep data, and generate AI-powered insights using a backend API.

---

## 🚀 Features

* Log daily health, activity, and sleep data
* Persistent local storage using SharedPreferences
* Export data as JSON
* Generate AI-ready insights via backend API
* Clean UI built with Jetpack Compose

---

## 🧱 Tech Stack

### Frontend (Android)

* Kotlin
* Jetpack Compose
* Retrofit (API communication)

### Backend

* Node.js
* Express.js

### AI (Planned / Ready)

* OpenAI API (integration-ready)

---

## 🧠 Architecture

```text
Android App (Kotlin)
        ↓
Node.js Backend (Express API)
        ↓
AI Processing (OpenAI API)
        ↓
Response → App UI
```

---

## ⚙️ How It Works

1. User logs data in the mobile app
2. Data is stored locally on the device
3. User clicks "Get AI Insights"
4. App sends data to backend via Retrofit
5. Backend processes data and returns insights
6. App displays results to the user

---

## 🛠️ Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/mobile-data-logger-ai.git
cd mobile-data-logger-ai
```

---

### 2. Run Backend

```bash
cd fitness-ai-backend
npm install
node server.js
```

Server will run on:

```text
http://localhost:3000
```

---

### 3. Run Android App

* Open project in Android Studio
* Start emulator
* Click Run

---

## 🔌 API Endpoint

```text
POST /ai-summary
```

### Request Body

```json
{
  "sleep": "6 hours",
  "activity": "30 minute run",
  "mood": "tired",
  "notes": "User log data"
}
```

### Response

```json
{
  "suggestion": "AI-generated insight appears here"
}
```

---

## 🔐 Notes

* `.env` file is excluded from Git for security
* `node_modules` is ignored
* Uses `10.0.2.2` to connect Android emulator to localhost backend

---

## 🚧 Future Improvements

* Real OpenAI API integration
* Structured data parsing (auto-detect sleep/activity)
* Trend analysis over time
* Weekly AI summaries
* UI improvements and charts

---

## 👨‍💻 Author

Abdallah El Hamawi

##
![screen1.png](screenshots/screen1.png)
![screen2.png](screenshots/screen2.png)
