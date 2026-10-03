package com.tecsup.mibodega.ui.cliente.screens.confirmacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonSecundario
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 7 de 7: Confirmacion.
 * Ultima pantalla del flujo. Muestra el resumen y, al volver al inicio,
 * AppNavegacion limpia el carrito y el historial (popUpTo).
 */
@Composable
fun PantallaConfirmacion(
    total: Double,
    direccion: String,
    referencia: String,
    metodoPago: String,
    onVolverAlInicio: () -> Unit
) {
    // Codigo de pedido generado una sola vez al entrar a la pantalla.
    val codigoPedido = remember { (1000..9999).random() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = VerdeBodega,
            modifier = Modifier.size(96.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Pedido confirmado",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Gracias por comprar en Mi Bodega. Te avisaremos cuando el pedido salga.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(GrisClaro, RoundedCornerShape(14.dp))
                .padding(20.dp)
        ) {
            DatoConfirmacion("Codigo de pedido", "MB-$codigoPedido")
            DatoConfirmacion("Metodo de pago", metodoPago)
            DatoConfirmacion("Direccion", direccion.ifBlank { "Av. Los Olivos 123" })
            if (referencia.isNotBlank()) {
                DatoConfirmacion("Referencia", referencia)
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total a pagar",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "S/ %.2f".format(total),
                    style = MaterialTheme.typography.titleMedium,
                    color = VerdeBodega
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        BotonSecundario(
            texto = "Volver al inicio",
            onClick = onVolverAlInicio
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun DatoConfirmacion(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ConfirmacionPreview() {
    BodegaTheme {
        PantallaConfirmacion(
            total = 24.70,
            direccion = "Av. Los Olivos 123",
            referencia = "Porton azul",
            metodoPago = "Yape",
            onVolverAlInicio = {}
        )
    }
}