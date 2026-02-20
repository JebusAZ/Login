package com.example.login

import android.app.Application
import com.google.firebase.FirebaseApp

class LoginApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
