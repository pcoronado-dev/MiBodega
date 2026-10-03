package com.tecsup.mibodega.ui.cliente.screens.carrito

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.navegacion.Rutas
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.SelectorCantidad
import com.tecsup.mibodega.ui.theme.AzulTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.GrisTexto
import com.tecsup.mibodega.ui.theme.VerdeBodega
import com.tecsup.mibodega.ui.utils.formatearCantidadArticulos

/**
 * Pantalla 5 de 7: Mi carrito.
 * No guarda nada: recibe la lista y avisa los cambios con callbacks.
 * El subtotal y el total se calculan aquí, y se recalculan solos
 * cada vez que la lista que llega cambia.
 */
@Composable
fun PantallaCarrito(
    carrito: List<Pair<Producto, Int>>,
    onVolver: () -> Unit,
    onIrAInicio: () -> Unit,
    onIncrementar: (Producto) -> Unit,
    onDecrementar: (Producto) -> Unit,
    onEliminar: (Producto) -> Unit,
    onContinuarPedido: () -> Unit
) {
    val subtotal = carrito.sumOf { it.first.precio * it.second }
    val total = subtotal + Rutas.COSTO_DELIVERY
    val cantidadTotalArticulos = carrito.sumOf { it.second }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Column {
                Text(
                    text = "Mi carrito",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AzulTexto
                )
                Text(
                    text = formatearCantidadArticulos(cantidadTotalArticulos),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (carrito.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingBasket,
                    contentDescription = null,
                    tint = GrisTexto,
                    modifier = Modifier.size(72.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Tu carrito está vacío",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AzulTexto
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Agrega productos desde el inicio para comenzar tu compra.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
                BotonPrimario(
                    texto = "Ver productos",
                    onClick = onIrAInicio
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
            ) {
                items(carrito, key = { it.first.id }) { (producto, cantidad) ->
                    FilaCarrito(
                        producto = producto,
                        cantidad = cantidad,
                        onIncrementar = { onIncrementar(producto) },
                        onDecrementar = { onDecrementar(producto) },
                        onEliminar = { onEliminar(producto) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }

            ResumenYBoton(
                subtotal = subtotal,
                total = total,
                onContinuarPedido = onContinuarPedido
            )
        }
    }
}

@Composable
private fun FilaCarrito(
    producto: Producto,
    cantidad: Int,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(GrisClaro, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.ShoppingBasket,
                contentDescription = producto.nombre,
                tint = VerdeBodega,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "S/ %.2f c/u".format(producto.precio),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        SelectorCantidad(
            cantidad = cantidad,
            onIncrementar = onIncrementar,
            onDecrementar = onDecrementar
        )

        IconButton(onClick = onEliminar) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Eliminar ${producto.nombre}",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ResumenYBoton(
    subtotal: Double,
    total: Double,
    onContinuarPedido: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
        FilaResumen("Subtotal", subtotal)
        FilaResumen("Costo de delivery", Rutas.COSTO_DELIVERY)

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total", style = MaterialTheme.typography.titleMedium)
            Text(
                text = "S/ %.2f".format(total),
                style = MaterialTheme.typography.titleMedium,
                color = VerdeBodega
            )
        }

        Spacer(Modifier.height(16.dp))

        BotonPrimario(
            texto = "Continuar pedido",
            onClick = onContinuarPedido
        )
    }
}

@Composable
private fun FilaResumen(etiqueta: String, valor: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(etiqueta, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            "S/ %.2f".format(valor),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CarritoPreview() {
    BodegaTheme {
        PantallaCarrito(
            carrito = listOf(
                listaProductosFake[4] to 1,
                listaProductosFake[0] to 2,
                listaProductosFake[2] to 1
            ),
            onVolver = {},
            onIrAInicio = {},
            onIncrementar = {},
            onDecrementar = {},
            onEliminar = {},
            onContinuarPedido = {}
        )
    }
}
