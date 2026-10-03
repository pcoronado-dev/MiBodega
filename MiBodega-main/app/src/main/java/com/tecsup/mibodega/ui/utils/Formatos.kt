package com.tecsup.mibodega.ui.utils

/**
 * Formatea una cantidad entera agregando el sustantivo "artículo" o "artículos"
 * según corresponda el número.
 *
 * @param cantidad Número de artículos a formatear.
 * @return "1 artículo" si cantidad es 1, o "$cantidad artículos" para 0 o más de 1.
 */
fun formatearCantidadArticulos(cantidad: Int): String {
    return if (cantidad == 1) {
        "1 artículo"
    } else {
        "$cantidad artículos"
    }
}
