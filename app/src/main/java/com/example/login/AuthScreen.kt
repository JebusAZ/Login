package com.example.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.login.auth.GoogleAuthUiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    googleAuthUiClient: GoogleAuthUiClient,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val coroutineScope = rememberCoroutineScope()

    // Launcher para el resultado de Google Sign-In
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                coroutineScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(intent = result.data ?: return@launch)
                    if (signInResult.errorMessage == null) {
                        Toast.makeText(context, "Login con Google exitoso", Toast.LENGTH_SHORT).show()
                        onLoginSuccess()
                    } else {
                        Toast.makeText(context, signInResult.errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login/Registro", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        // --- Formulario de Email/Contraseña ---
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { onLoginSuccess() }
                        .addOnFailureListener { Toast.makeText(context, "Fallo en el registro: ${it.message}", Toast.LENGTH_LONG).show() }
                }
            }) {
                Text("Registrarse")
            }
            Button(onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener { onLoginSuccess() }
                        .addOnFailureListener { Toast.makeText(context, "Fallo en el login: ${it.message}", Toast.LENGTH_LONG).show() }
                }
            }) {
                Text("Iniciar Sesión")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Botón de Google Sign-In ---
        Button(onClick = {
            coroutineScope.launch {
                val signInIntentSender = googleAuthUiClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
            }
        }) {
            Text("Iniciar sesión con Google")
        }
    }
}