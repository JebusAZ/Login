package com.example.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeliveryScreen(onProductClick: (Product) -> Unit) {
    val menu = listOf(
        Product(1, "Pizza Pepperoni", "$12.00", "Clásica italiana", "Masa artesanal fermentada por 24 horas, salsa de tomate San Marzano, mozzarella premium y pepperoni picante.", "https://images.unsplash.com/photo-1628840042765-356cda07504e?q=80&w=500"),
        Product(2, "Hamburguesa Doble", "$9.50", "Con queso cheddar", "Doble carne de res premium (200g), queso cheddar fundido, tocino crujiente, cebolla caramelizada y pan brioche.", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=500"),
        Product(3, "Sushi Roll x10", "$15.00", "Salmón y aguacate", "Rollos de sushi rellenos de salmón fresco, queso crema de la casa y aguacate, cubiertos con sésamo tostado.", "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?q=80&w=500"),
        Product(4, "Tacos", "$18.00", "Tacos Mexicanos", "Deliciosos tacos de pastor con tortilla de maiz y bañados en una rica salsa mexicana.", "https://images.unsplash.com/photo-1599974579688-8dbdd335c77f?q=80&w=1194&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
        Product(5, "Hamburguesa Doble", "$9.50", "Con queso cheddar", "Doble carne de res premium (200g), queso cheddar fundido, tocino crujiente, cebolla caramelizada y pan brioche.", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=500"),
        Product(6, "Sushi Roll x10", "$15.00", "Salmón y aguacate", "Rollos de sushi rellenos de salmón fresco, queso crema de la casa y aguacate, cubiertos con sésamo tostado.", "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?q=80&w=500")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Nuestro Menú", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(menu) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onProductClick(producto) }
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        PicassoImage(url = producto.imageUrl, modifier = Modifier.size(80.dp))
                        Column(modifier = Modifier.padding(start = 16.dp)) {
                            Text(producto.name, style = MaterialTheme.typography.titleMedium)
                            Text(producto.shortDescription, style = MaterialTheme.typography.bodySmall)
                            Text(producto.price, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}