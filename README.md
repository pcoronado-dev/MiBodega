PROMPT1: Trabaja en el proyecto Android Studio "MiBodega" (Kotlin + Jetpack Compose + Material 3,
paquete com.tecsup.mibodega). NO crees archivos nuevos: solo edita los que ya existen.

CONTEXTO
La app tiene 7 pantallas conectadas en AppNavegacion.kt (navegacion/).
La Pantalla 3 es ui/cliente/screens/inicio/PantallaInicio.kt.

TAREA
Revisa el campo de búsqueda de PantallaInicio.kt y MEJÓRALO para que filtre la lista
de productos EN TIEMPO REAL mientras el usuario escribe, y que se combine correctamente
con el filtro de categoría que ya existe.

REQUISITOS OBLIGATORIOS
1. Ambos filtros deben funcionar A LA VEZ (combinados con Y lógica). Al elegir la
   categoría "Bebidas" y escribir "coca", el resultado debe ser la intersección: solo
   productos que SON de Bebidas Y ADEMÁS su nombre contiene "coca".
2. La búsqueda debe ser case-insensitive (ignorar mayúsculas/minúsculas) y no debe
   importar si el usuario escribe espacios al principio o al final.
3. El filtro debe recalcularse automáticamente en cada tecla, SIN botón de "buscar"
   y SIN calling a ninguna función desde un botón. Nada de LaunchedEffect.
4. Si el usuario selecciona otra categoría, el texto de búsqueda debe CONSERVARSE.
5. Si no hay coincidencias, mostrar un estado vacío con un mensaje claro (por ejemplo
   "No encontramos productos") y un ícono o texto de ayuda. No mostrar un grid vacío
   sin explicación.
6. Mantén el diseño actual: no cambies colores, tipografías ni la estructura visual
   de la pantalla. Solo la lógica y el estado vacío.

RESTRICCIONES IMPORTANTES
- Usa SOLO el estado de Compose (remember / mutableStateOf). NO ViewModel, NO Room,
  NO Flow, NO corrutinas, NO dependencias nuevas.
- La lista de productos sigue viniendo de listaProductosFake en
  ui/cliente/modelo/DatosFake.kt. No la modifiques ni agregues productos.
- El resto de la pantalla debe seguir funcionando igual: LazyRow de categorías,
  LazyColumn de productos, navegación al detalle con el id del producto,
  badge del carrito y el botón "+" que agrega al carrito.
- Respeta la convención del proyecto: los textos en español, los colores de
  ui/theme/Color.kt (VerdeBodega, GrisClaro, etc.), y los sub-composables privados
  al final del archivo.
- No toques AppNavegacion.kt, Rutas.kt ni ninguna otra pantalla.

AL TERMINAR
1. Muéstrame el bloque completo de PantallaInicio.kt con los cambios,Ownership y tareas extras, es decir, solo el código final del archivo.
2. Explica en 3-4 líneas cómo se combinan los dos filtros y por qué es reactivo.
3. Lista los archivos que modificaste.
4. No hagas commit todavía.

PROMPT2: Trabaja en el proyecto Android Studio "MiBodega" (Kotlin + Jetpack Compose + Material 3,
paquete com.tecsup.mibodega).

CONTEXTO
Hoy, cuando el usuario crea su cuenta en PantallaCrearCuenta.kt, los datos (nombre,
direccion) se guardan como estado en AppNavegacion.kt y se descartan al cerrar la app.
El item de menú "Perfil" (definido en navegacion/Rutas.kt) navega a CREAR_CUENTA, lo cual
es incorrecto: el perfil debe mostrar los datos del usuario, no un formulario.

TAREA
Crea la Pantalla 8: Perfil del usuario, y haz que el item "Perfil" de la NavigationBar
abra esta pantalla en lugar de "Crear cuenta".

REQUISITOS OBLIGATORIOS
1. Crea el archivo ui/cliente/screens/perfil/PantallaPerfil.kt con una función
   @Composable llamada PantallaPerfil.
2. La función NO debe leer datos de ninguna base de datos ni pedir nada al usuario.
   Recibir los datos como parámetros:
   - nombre: String
   - direccion: String
   - referencia: String
   - cantidadDePedidos: Int
   - onCerrarSesion: () -> Unit
3. Contenido de la pantalla:
   - Un ícono grande de persona (Icons.Default.Person o similar) como avatar.
   - El nombre del usuario en título grande y en negrita.
   - Un bloque "Mi dirección" mostrando la dirección y la referencia.
   - Un bloque "Pedidos" mostrando la cantidad de pedidos realizados.
   - Un botón secundario "Cerrar sesión" que invoque onCerrarSesion().
4. Conecta los datos en AppNavegacion.kt:
   - Renombra la constante CREAR_CUENTA a PERFIL y ponla en valor "perfil".
   - Registra la nueva ruta en el NavHost, dentro del bloque composable().
   - Pasa las variables de estado (nombreCliente, direccionCliente, referenciaCliente).
   - Para "Cerrar sesión": limpia el carrito y el estado del usuario, y navega de vuelta
     a LOGIN usando popUpTo con inclusive = true para que no se pueda volver atrás.
5. El ícono del menú "Perfil" en Rutas.kt debe seguir siendo Person.
6. La pantalla debe respetar el tema: usa los colores de ui/theme/Color.kt y los
   componentes existentes de ui/componentes/ (BotonSecundario, CampoTexto, etc.)
   en lugar de recrearlos.
7. Agrega un @Preview al final del archivo que muestre datos de ejemplo, para poder
   ver la pantalla sin ejecutar la app.

RESTRICCIONES
- Usa SOLO remember/mutableStateOf. NO ViewModel, NO Room, NO DataStore, NO red.
- NO crees un archivo de modelo nuevo ni modifiques Producto.kt o DatosFake.kt.
- Para contar pedidos, usa una variable de estado simple en AppNavegacion.kt que
  se incremente cuando el usuario completa una compra (en PantallaConfirmacion).
- No rompas el flujo actual: Login, Crear Cuenta, Inicio, Detalle, Carrito,
  Datos de Entrega y Confirmación deben seguir funcionando exactamente igual.
- Todos los textos visibles en español.

AL TERMINAR
1. Muéstrame el contenido completo de PantallaPerfil.kt.
2. Muéstrame el fragmento exacto de AppNavegacion.kt y de Rutas.kt que cambió,
   indicando qué borrar y qué agregar.
3. Lista los archivos creados y modificados.
4. No hagas commit todavía.

PROMPT3: Trabaja en el proyecto Android Studio "MiBodega" (Kotlin + Jetpack Compose + Material 3,
paquete com.tecsup.mibodega).

CONTEXTO
En PantallaInicio.kt y en el menú inferior de AppNavegacion.kt, el badge del carrito
muestra el número de artículos con un Text("$cantidad"). Actualmente no distingue entre
un artículo y varios, no avisa cuando el carrito está vacío, y el texto "Continuar
pedido" del carrito se muestra incluso con el carrito vacío.

TAREA
Mejora la experiencia del carrito en tres puntos concretos.

REQUISITOS OBLIGATORIOS
1. PLURAL CORRECTO. Crea un helper (puede ser una función top-level privada en el archivo,
   o un archivo nuevo ui/utils/Formatos.kt si prefieres reutilizarlo) que formatee
   cantidades con el sustantivo correcto:
   - 1 artículo
   - 0 artículos
   - 2 artículos
   - 5 artículos
   Aplícalo en el subtítulo del carrito y en cualquier texto donde aparezca la cantidad.

2. BADGE CON ESTADO VACÍO. En PantallaInicio.kt:
   - Si cantidadCarrito es 0, el ícono del carrito NO debe mostrar ningún badge.
   - Si es mayor que 0, debe mostrar el badge con la cantidad.
   Mantén el uso de BadgedBox y Badge que ya existe, solo cambia la lógica.

3. CARRITO VACÍO MEJORADO. En PantallaCarrito.kt:
   - Cuando la lista esté vacía, NO debe mostrarse el bloque de subtotal, el costo de
     delivery, el total ni el botón "Continuar pedido".
   - Debe mostrarse un mensaje claro con una acción útil, por ejemplo
     "Tu carrito está vacío" y un botón "Ver productos" que reciba un callback
     onIrAInicio: () -> Unit.
   - Cuando SÍ hay productos, el comportamiento actual se mantiene exactamente igual
     (LazyColumn de filas, SelectorCantidad, eliminar, subtotal, total y botón).

4. En AppNavegacion.kt, pasa onIrAInicio al PantallaCarrito, conectado con
   popBackStack() para volver al inicio.

RESTRICCIONES
- Usa SOLO remember/mutableStateOf. NO ViewModel, NO Room, NO dependencias nuevas.
- No cambies la estructura visual existente más allá de lo descrito arriba.
- No agregues recursos de texto nuevos a strings.xml: los textos van directamente
  en el código, como está el resto del proyecto.
- No toques PantallaInicio.kt más allá del punto 2, ni Rutas.kt, ni Producto.kt.
- Todos los textos en español y con tildes correctas.

AL TERMINAR
1. Muéstrame el código final de los archivos que modificaste.
2. Muéstrame el helper de plural completo y explica cómo decides entre singular
   y plural.
3. Lista los archivos modificados.
4. No hagas commit todavía.
