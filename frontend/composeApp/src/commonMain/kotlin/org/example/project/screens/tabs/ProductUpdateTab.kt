package org.example.project.screens.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.launch
import org.example.project.network.ProductApiService
import org.example.project.network.model.Product
import org.example.project.network.model.ProductRequest
import androidx.compose.foundation.lazy.items

object ProductUpdateTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon: VectorPainter = rememberVectorPainter(Icons.Default.Edit)

            return remember {
                TabOptions(
                    index = 2u,
                    title = "Update",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val apiService = remember { ProductApiService() }
        var productList by remember { mutableStateOf<List<Product>>(emptyList()) }
        var selectedProduct by remember { mutableStateOf<Product?>(null) }
        var description by remember { mutableStateOf("") }
        var price by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf("") }

        fun loadProducts() {
            scope.launch {
                try {
                    productList = apiService.getAllProducts()
                } catch (e: Exception) {
                    message = "Error loading products"
                }
            }
        }

        LaunchedEffect(Unit) {
            loadProducts()
        }

        fun selectProduct(product: Product) {
            selectedProduct = product
            description = product.description
            price = product.price.toString()
        }

        fun updateProduct() {
            val product = selectedProduct ?: return
            val priceValue = price.toDoubleOrNull()
            if (description.isBlank() || priceValue == null || priceValue <= 0) {
                message = "Please enter valid description and price > 0"
                return
            }

            scope.launch {
                isLoading = true
                try {
                    val request = ProductRequest(description, priceValue)
                    val updatedProduct = apiService.updateProduct(product.id, request)
                    if (updatedProduct != null) {
                        message = "Product updated successfully"
                        selectedProduct = null
                        description = ""
                        price = ""
                        loadProducts()
                    } else {
                        message = "Product not found"
                    }
                } catch (e: Exception) {
                    message = "Error updating product: ${e.message}"
                } finally {
                    isLoading = false
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Update Product",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (selectedProduct == null) {
                // Show product list to select
                Text(
                    text = "Select a product to update:",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productList) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectProduct(product) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = product.description,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "$${product.price}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                Text(
                                    text = "Tap to edit",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            } else {
                // Show edit form
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { updateProduct() },
                        enabled = !isLoading,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isLoading) "Updating..." else "Update")
                    }

                    Button(
                        onClick = {
                            selectedProduct = null
                            description = ""
                            price = ""
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                }
            }

            if (message.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    color = if (message.startsWith("Error") || message.contains("not found")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}