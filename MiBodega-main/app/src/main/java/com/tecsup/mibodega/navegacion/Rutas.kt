package com.tecsup.mibodega.navegacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

object Rutas {

    // ---- Rutas simples ----
    const val LOGIN = "login"
    const val CREAR_CUENTA = "crearCuenta"
    const val PERFIL = "perfil"
    const val INICIO = "inicio"
    const val CARRITO = "carrito"
    const val DATOS_ENTREGA = "datosEntrega"
    const val CONFIRMACION = "confirmacion"

    // ---- Ruta con parametro ----
    const val ARG_ID_PRODUCTO = "idProducto"
    const val DETALLE = "detalle/{$ARG_ID_PRODUCTO}"

    /** Construye la ruta real del detalle pasandole el id del producto. */
    fun detalle(idProducto: Int) = "detalle/$idProducto"

    /**
     * Pantallas que muestran la NavigationBar inferior.
     * Login y CrearCuenta NO entran: son pantallas previas al menu.
     */
    val rutasConMenu = setOf(INICIO, DETALLE, CARRITO, DATOS_ENTREGA, CONFIRMACION, PERFIL)

    /** Cada ficha del menu: a donde lleva, como se llama y con que icono. */
    data class DestinoMenu(val ruta: String, val etiqueta: String, val icono: ImageVector)

    val menuPrincipal = listOf(
        DestinoMenu(INICIO, "Inicio", Icons.Default.Home),
        DestinoMenu(CARRITO, "Carrito", Icons.Default.ShoppingCart),
        DestinoMenu(CONFIRMACION, "Pedidos", Icons.Default.Receipt),
        DestinoMenu(PERFIL, "Perfil", Icons.Default.Person)
    )

    const val COSTO_DELIVERY = 4.00
}
