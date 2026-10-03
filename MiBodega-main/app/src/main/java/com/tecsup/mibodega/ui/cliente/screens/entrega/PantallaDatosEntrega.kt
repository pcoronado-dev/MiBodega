package com.tecsup.mibodega.ui.cliente.screens.entrega

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 6 de 7: Datos de entrega.
 * Continua la cuenta con direccion, referencia y metodo de pago.
 * Al confirmar, entrega los 4 datos hacia arriba.
 */
private val METODOS_PAGO = listOf("Yape", "Tarjeta", "Efectivo")

@Composable
fun PantallaDatosEntrega(
    onVolver: () -> Unit,
    onConfirmar: (nombre: String, direccion: String, referencia: String, metodoPago: String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    var metodoPago by remember { mutableStateOf(METODOS_PAGO.first()) }

    val completo = nombre.isNotBlank() && direccion.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Datos de entrega",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(Modifier.height(20.dp))

        CampoTexto(
            etiqueta = "Nombre de quien recibe",
            valor = nombre,
            onValorCambia = { nombre = it },
            placeholder = "Juan Perez"
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Direccion",
            valor = direccion,
            onValorCambia = { direccion = it },
            placeholder = "Av. Los Olivos 123"
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Referencia",
            valor = referencia,
            onValorCambia = { referencia = it },
            placeholder = "Frente al parque, porton azul"
        )

        Spacer(Modifier.height(28.dp))

        Text(
            text = "Metodo de pago",
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(Modifier.height(8.dp))

        // Grupo de opciones single-choice.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(GrisClaro, RoundedCornerShape(12.dp))
                .padding(vertical = 4.dp)
        ) {
            METODOS_PAGO.forEach { metodo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = metodoPago == metodo,
                            onClick = { metodoPago = metodo }
                        )
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = metodoPago == metodo,
                        onClick = null
                    )
                    Text(
                        text = metodo,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        BotonPrimario(
            texto = "Confirmar pedido",
            habilitado = completo,
            onClick = { onConfirmar(nombre, direccion, referencia, metodoPago) }
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DatosEntregaPreview() {
    BodegaTheme {
        PantallaDatosEntrega(onVolver = {}, onConfirmar = { _, _, _, _ -> })
    }
}