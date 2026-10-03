package com.tecsup.mibodega.ui.cliente.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonSecundario
import com.tecsup.mibodega.ui.theme.AzulTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.FondoClaro
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.GrisTexto
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 8: Perfil del usuario.
 * Muestra los datos del cliente (nombre, dirección, pedidos)
 * y permite cerrar sesión.
 */
@Composable
fun PantallaPerfil(
    nombre: String,
    direccion: String,
    referencia: String,
    cantidadDePedidos: Int,
    onCerrarSesion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        Text(
            text = "Perfil",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = AzulTexto,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        // Avatar con icono grande de persona
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(FondoClaro, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Foto de perfil",
                tint = VerdeBodega,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Nombre del usuario en título grande y en negrita
        Text(
            text = nombre.ifBlank { "Usuario" },
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AzulTexto,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        // Bloque "Mi dirección"
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(GrisClaro, RoundedCornerShape(14.dp))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = VerdeBodega,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Mi dirección",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AzulTexto
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = direccion.ifBlank { "No especificada" },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (referencia.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Referencia: $referencia",
                    style = MaterialTheme.typography.bodySmall,
                    color = GrisTexto
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Bloque "Pedidos"
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(GrisClaro, RoundedCornerShape(14.dp))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Receipt,
                    contentDescription = null,
                    tint = VerdeBodega,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Pedidos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AzulTexto
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = if (cantidadDePedidos == 1) "1 pedido realizado" else "$cantidadDePedidos pedidos realizados",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(Modifier.height(32.dp))

        // Botón secundario "Cerrar sesión"
        BotonSecundario(
            texto = "Cerrar sesión",
            onClick = onCerrarSesion
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PerfilPreview() {
    BodegaTheme {
        PantallaPerfil(
            nombre = "Juan Pérez",
            direccion = "Av. Los Olivos 123",
            referencia = "Frente al parque, portón azul",
            cantidadDePedidos = 3,
            onCerrarSesion = {}
        )
    }
}
