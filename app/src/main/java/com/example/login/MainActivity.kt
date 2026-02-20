package com.example.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.login.auth.GoogleAuthUiClient
import com.example.login.ui.theme.LoginTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginTheme {
                val auth = FirebaseAuth.getInstance()
                var isLoggedIn by remember { mutableStateOf(auth.currentUser != null) }

                DisposableEffect(auth) {
                    val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                        isLoggedIn = firebaseAuth.currentUser != null
                    }
                    auth.addAuthStateListener(authStateListener)
                    onDispose {
                        auth.removeAuthStateListener(authStateListener)
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isLoggedIn) {
                        StorageScreen()
                    } else {
                        AuthScreen(
                            googleAuthUiClient = googleAuthUiClient,
                            onLoginSuccess = {  }
                        )
                    }
                }
            }
        }
    }
}