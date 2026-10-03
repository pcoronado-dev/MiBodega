package com.tecsup.mibodega.ui.cliente.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.R
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.BotonSecundario
import com.tecsup.mibodega.ui.theme.AzulEnlace
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 1 de 7: Login.
 * No sabe navegar: recibe onIniciarSesion y onCrearCuenta.
 */
@Composable
fun PantallaLogin(
    onIniciarSesion: () -> Unit,
    onCrearCuenta: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        Image(
            painter = painterResource(R.drawable.ilustracion_bodega),
            contentDescription = "Ilustracion de la bodega",
            modifier = Modifier.size(190.dp)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = buildAnnotatedString {
                append("Mi ")
                withStyle(SpanStyle(color = VerdeBodega)) { append("Bodega") }
            },
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Tu bodega de siempre, a un toque de tu casa",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(36.dp))

        BotonPrimario(
            texto = "Entrar con mi numero",
            subtexto = "Te avisamos cuando llegue tu pedido",
            icono = rememberVectorPainter(Icons.Default.Phone),
            onClick = onIniciarSesion
        )

        Spacer(Modifier.height(12.dp))

        BotonSecundario(
            texto = "Crear cuenta nueva",
            onClick = onCrearCuenta
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Al continuar aceptas nuestros",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Terminos y Condiciones",
            style = MaterialTheme.typography.bodySmall,
            color = AzulEnlace
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginPreview() {
    BodegaTheme {
        PantallaLogin(onIniciarSesion = {}, onCrearCuenta = {})
    }
}