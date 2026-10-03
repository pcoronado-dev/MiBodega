package com.tecsup.mibodega.navegacion

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.screens.carrito.PantallaCarrito
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.PantallaConfirmacion
import com.tecsup.mibodega.ui.cliente.screens.cuenta.PantallaCrearCuenta
import com.tecsup.mibodega.ui.cliente.screens.detalle.PantallaDetalleProducto
import com.tecsup.mibodega.ui.cliente.screens.entrega.PantallaDatosEntrega
import com.tecsup.mibodega.ui.cliente.screens.inicio.PantallaInicio
import com.tecsup.mibodega.ui.cliente.screens.login.PantallaLogin

/**
 * "Director de orquesta" de la app.
 *
 * Aqui viven las 3 cosas globales:
 *  1. El NavHost con las 7 rutas.
 *  2. La NavigationBar (bottomBar) compartida.
 *  3. El estado del carrito, que baja hacia las pantallas como parametro.
 *
 * Ninguna pantalla navega sola ni toca el carrito: solo avisa con callbacks.
 */
@Composable
fun AppNavegacion() {
    val navController = rememberNavController()

    // Sirve para saber en que pantalla estamos y cuál tab del menu marcar.
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    // ---------------- ESTADO GLOBAL (state hoisting) ----------------
    // Cada par es (producto, cantidad).
    var carrito by remember { mutableStateOf<List<Pair<Producto, Int>>>(emptyList()) }

    // Datos del pedido que se piden en PantallaDatosEntrega
    // y se muestran despues en PantallaConfirmacion.
    var nombreCliente by remember { mutableStateOf("") }
    var direccionCliente by remember { mutableStateOf("") }
    var referenciaCliente by remember { mutableStateOf("") }
    var metodoPago by remember { mutableStateOf("Yape") }

    Scaffold(
        bottomBar = {
            // El menu solo aparece en las pantallas de Rutas.rutasConMenu.
            if (rutaActual != null && rutaActual in Rutas.rutasConMenu) {
                NavigationBarPrincipal(
                    rutaActual = rutaActual,
                    cantidadCarrito = cantidadTotal(carrito),
                    onNavegar = { destino ->
                        if (destino != rutaActual) {
                            navController.navigate(destino) {
                                // Volvemos a Inicio, guardando el estado de lo que
                                // estaba arriba. Asi el menu no apila pantallas.
                                popUpTo(Rutas.INICIO) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Rutas.LOGIN,
            modifier = Modifier.padding(padding)
        ) {

            // 1. LOGIN
            composable(Rutas.LOGIN) {
                PantallaLogin(
                    onIniciarSesion = {
                        // popUpTo inclusive = elimina el login del historial:
                        // el usuario ya no puede volver atras con el boton del celu.
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.LOGIN) { inclusive = true }
                        }
                    },
                    onCrearCuenta = { navController.navigate(Rutas.CREAR_CUENTA) }
                )
            }

            // 2. CREAR CUENTA
            composable(Rutas.CREAR_CUENTA) {
                PantallaCrearCuenta(
                    onVolver = { navController.popBackStack() },
                    onCrearCuenta = { nombre, _, direccion ->
                        nombreCliente = nombre
                        direccionCliente = direccion
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.LOGIN) { inclusive = true }
                        }
                    }
                )
            }

            // 3. INICIO
            composable(Rutas.INICIO) {
                PantallaInicio(
                    cantidadCarrito = cantidadTotal(carrito),
                    onVerCarrito = { navController.navigate(Rutas.CARRITO) },
                    onProductoClick = { producto ->
                        // Navegacion con parametro: el id viaja en la URL.
                        navController.navigate(Rutas.detalle(producto.id))
                    },
                    onAgregarProducto = { producto ->
                        carrito = agregarProducto(carrito, producto, 1)
                    }
                )
            }

            // 4. DETALLE (ruta con parametro)
            composable(
                route = Rutas.DETALLE,
                arguments = listOf(navArgument(Rutas.ARG_ID_PRODUCTO) { type = NavType.IntType })
            ) { entry ->
                val idProducto = entry.arguments?.getInt(Rutas.ARG_ID_PRODUCTO) ?: -1
                val producto = listaProductosFake.firstOrNull { it.id == idProducto }

                // firstOrNull + if: si el id no existe, no se cae la app.
                if (producto != null) {
                    PantallaDetalleProducto(
                        producto = producto,
                        onVolver = { navController.popBackStack() },
                        onAgregarAlCarrito = { cantidad ->
                            carrito = agregarProducto(carrito, producto, cantidad)
                            navController.popBackStack()
                        }
                    )
                }
            }

            // 5. CARRITO
            composable(Rutas.CARRITO) {
                PantallaCarrito(
                    carrito = carrito,
                    onVolver = { navController.popBackStack() },
                    onIncrementar = { producto -> carrito = cambiarCantidad(carrito, producto.id, 1) },
                    onDecrementar = { producto -> carrito = cambiarCantidad(carrito, producto.id, -1) },
                    onEliminar = { producto -> carrito = eliminarProducto(carrito, producto.id) },
                    onContinuarPedido = {
                        if (carrito.isNotEmpty()) {
                            navController.navigate(Rutas.DATOS_ENTREGA)
                        }
                    }
                )
            }

            // 6. DATOS DE ENTREGA
            composable(Rutas.DATOS_ENTREGA) {
                PantallaDatosEntrega(
                    onVolver = { navController.popBackStack() },
                    onConfirmar = { nombre, direccion, referencia, pago ->
                        nombreCliente = nombre
                        direccionCliente = direccion
                        referenciaCliente = referencia
                        metodoPago = pago
                        // Saco Carrito y Entrega del historial: desde Confirmacion
                        // el boton atras debe llevar a Inicio, no al carrito.
                        navController.navigate(Rutas.CONFIRMACION) {
                            popUpTo(Rutas.INICIO)
                        }
                    }
                )
            }

            // 7. CONFIRMACION
            composable(Rutas.CONFIRMACION) {
                PantallaConfirmacion(
                    total = total(carrito),
                    direccion = direccionCliente,
                    referencia = referenciaCliente,
                    metodoPago = metodoPago,
                    onVolverAlInicio = {
                        carrito = emptyList()
                        // limpio total: dejo solo Inicio en el historial
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.INICIO) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

/** La NavigationBar del menu principal. Solo la usa AppNavegacion. */
@Composable
private fun NavigationBarPrincipal(
    rutaActual: String?,
    cantidadCarrito: Int,
    onNavegar: (String) -> Unit
) {
    // El Detalle no es un tab, asi que sigue marcando Inicio como activo.
    val tabActiva = if (rutaActual == Rutas.DETALLE) Rutas.INICIO else rutaActual

    NavigationBar {
        Rutas.menuPrincipal.forEach { destino ->
            NavigationBarItem(
                selected = tabActiva == destino.ruta,
                onClick = { onNavegar(destino.ruta) },
                icon = {
                    if (destino.ruta == Rutas.CARRITO && cantidadCarrito > 0) {
                        BadgedBox(badge = { Badge { Text("$cantidadCarrito") } }) {
                            Icon(destino.icono, contentDescription = destino.etiqueta)
                        }
                    } else {
                        Icon(destino.icono, contentDescription = destino.etiqueta)
                    }
                },
                label = { Text(destino.etiqueta) }
            )
        }
    }
}

// ================= FUNCIONES PURAS DEL CARRITO =================
// Viven aqui y no dentro de las pantallas: asi son faciles de probar
// y de reusar. Cada una devuelve una lista NUEVA (inmutabilidad).

/** Si el producto ya existe le suma la cantidad; si no, lo agrega. */
private fun agregarProducto(
    carrito: List<Pair<Producto, Int>>,
    producto: Producto,
    cantidad: Int
): List<Pair<Producto, Int>> {
    val yaEsta = carrito.any { it.first.id == producto.id }
    return if (yaEsta) {
        carrito.map { (prod, cant) ->
            if (prod.id == producto.id) prod to (cant + cantidad) else prod to cant
        }
    } else {
        carrito + (producto to cantidad)
    }
}

/** delta = +1 suma, -1 resta. Si la cantidad llega a 0, saca el producto. */
private fun cambiarCantidad(
    carrito: List<Pair<Producto, Int>>,
    idProducto: Int,
    delta: Int
): List<Pair<Producto, Int>> = carrito.mapNotNull { (prod, cant) ->
    when {
        prod.id != idProducto -> prod to cant
        cant + delta < 1 -> null
        else -> prod to (cant + delta)
    }
}

private fun eliminarProducto(
    carrito: List<Pair<Producto, Int>>,
    idProducto: Int
): List<Pair<Producto, Int>> = carrito.filterNot { it.first.id == idProducto }

/** Cantidad total de articulos: es lo que va en el badge del carrito. */
private fun cantidadTotal(carrito: List<Pair<Producto, Int>>): Int =
    carrito.sumOf { it.second }

/** Suma de precios. Se recalcula solo porque el carrito es un State. */
private fun subtotal(carrito: List<Pair<Producto, Int>>): Double =
    carrito.sumOf { it.first.precio * it.second }

private fun total(carrito: List<Pair<Producto, Int>>): Double =
    subtotal(carrito) + Rutas.COSTO_DELIVERY