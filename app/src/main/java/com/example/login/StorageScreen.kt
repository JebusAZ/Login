package com.example.login

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

@Composable
fun StorageScreen() {
    val context = LocalContext.current
    val storageRef = FirebaseStorage.getInstance().reference
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Firebase Storage", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Seleccionar Imagen")
        }

        Spacer(modifier = Modifier.height(20.dp))

        imageUri?.let {
            Button(onClick = {
                val filename = "${UUID.randomUUID()}"
                val uploadRef = storageRef.child("image/$filename")
                uploadRef.putFile(it)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Imagen subida con éxito", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
                    }
            }) {
                Text("Subir Imagen")
            }
        }
    }
}