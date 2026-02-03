package com.example.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(product: Product, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Plato") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            // Imagen Grande
            PicassoImage(
                url = product.imageUrl,
                modifier = Modifier.fillMaxWidth().height(250.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(product.name, style = MaterialTheme.typography.headlineMedium)
            Text(product.price, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Descripción corta", style = MaterialTheme.typography.titleMedium)
            Text(product.shortDescription, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Descripción larga", style = MaterialTheme.typography.titleMedium)
            Text(product.longDescription, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* Aquí iría lógica de compra */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Añadir al carrito")
            }
        }
    }
}