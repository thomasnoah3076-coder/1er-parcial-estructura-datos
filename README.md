# 1er Parcial – Estructura de Datos (Sistema de Cine)

Este proyecto implementa un pequeño sistema de gestión de funciones de cine en Java, usando una **lista simplemente enlazada** como estructura principal de almacenamiento.

## Estructura del proyecto

- `Funcion.java`: representa una función de cine (código, película, hora de inicio y un arreglo `puestos[]` de 20 posiciones con estado `D` (disponible) u `O` (ocupado)).
- `NodoFuncion.java`: nodo de la lista enlazada; guarda un dato (`Funcion`) y una referencia al **siguiente** nodo (`siguiente`), no hay referencia al anterior.
- `ListaFunciones.java`: implementa la lista simplemente enlazada (`head`, `tamano`) con operaciones `insertar`, `existeCodigo`, `obtenerPorPosicion`.
- `Cine.java`: lógica de negocio (registrar función, comprar entrada, iniciar labores/reproducir funciones).
- `Main.java`: punto de entrada del programa.

---

## Preguntas de análisis

### 1. Si se necesita buscar una función por su código, ¿cómo recorrería la estructura seleccionada?

Como se trata de una lista simplemente enlazada, el único punto de entrada es el `head`, así que la búsqueda debe ser **secuencial** (recorrido lineal):

1. Se inicia un puntero auxiliar (`actual`) en `head`.
2. Mientras `actual` no sea `null`:
   - Se compara el código de la función del nodo actual (`actual.getDato().getCodigo()`) con el código buscado.
   - Si coincide, se retorna esa `Funcion` (o el nodo).
   - Si no coincide, se avanza con `actual = actual.getSiguiente()`.
3. Si se llega a `null` sin encontrar coincidencia, la función con ese código no existe.

Esto es exactamente lo que ya hace `existeCodigo(String codigo)` en `ListaFunciones`, solo que en lugar de devolver `true/false` debería devolver la `Funcion` (o el `NodoFuncion`) encontrado. La complejidad en el peor caso es **O(n)**, porque no existe forma de "saltar" nodos ni de indexar directamente como en un arreglo.

### 2. ¿Qué dificultades tendría el sistema si se usa una lista simplemente enlazada para almacenar las funciones y se necesita recorrerla en sentido contrario, o buscar qué función se realizó antes de la actual?

Con una lista **simplemente** enlazada (como `NodoFuncion`, que solo tiene `siguiente`), surgen varias dificultades:

- **No hay referencia hacia atrás**: cada nodo solo conoce a su sucesor, nunca a su predecesor. Para saber "qué función va antes de la actual" hay que recorrer la lista **desde el `head`** hasta encontrar el nodo cuyo `siguiente` sea el nodo actual, en vez de simplemente leer un puntero `anterior`.
- **Recorrer en sentido inverso no es directo**: no se puede simplemente ir de "cola a cabeza" como en una lista doblemente enlazada. Para recorrerla al revés hay opciones costosas:
  - Recorrer la lista completa guardando los nodos en una pila (o arreglo) y luego desapilarlos, lo que implica memoria adicional O(n).
  - Usar recursividad (la recursión "sube" al retornar), lo que también consume memoria de pila.
  - Reconstruir/invertir la lista (cambiar todos los `siguiente`), lo cual es riesgoso porque destruye el orden original si no se maneja con cuidado.
- **Complejidad y rendimiento**: cualquier operación que necesite "el anterior" pasa de O(1) (si hubiera doble enlace) a O(n), porque siempre hay que partir del `head`.
- **Mayor riesgo de errores**: al no tener un enlace directo hacia atrás, es fácil perder la referencia al nodo anterior si no se guarda explícitamente durante el recorrido (por ejemplo, al eliminar un nodo, como se explica en la siguiente pregunta).

La solución de fondo sería convertir `NodoFuncion` en una **lista doblemente enlazada**, agregando un atributo `anterior` (o `NodoFuncion prev`) además de `siguiente`, lo que permitiría recorrer en ambos sentidos en O(1) por paso.

### 3. Si se elimina una función de la estructura, ¿qué debe tener en cuenta el programa para evitar errores al recorrer las funciones o consultar sus puestos?

Al eliminar un nodo de la lista simplemente enlazada hay que cuidar varios puntos:

- **Actualizar correctamente los enlaces**: antes de eliminar, se debe ubicar el nodo `anterior` al que se quiere borrar (recorriendo desde `head`), y hacer que `anterior.setSiguiente(actual.getSiguiente())` para "saltar" el nodo eliminado. Si no se actualiza bien este enlace, la lista puede quedar **cortada** (se pierde el resto de la lista) o puede quedar un **ciclo/enlace inválido**.
- **Caso especial: eliminar el `head`**: si el nodo a eliminar es el primero, no hay un `anterior`; hay que reasignar `head = head.getSiguiente()` directamente, o el programa fallará con un `NullPointerException` al intentar acceder a un "anterior" que no existe.
- **Caso especial: lista vacía o función no encontrada**: siempre validar `estaVacia()` y verificar que el código exista (`existeCodigo`) antes de intentar eliminar, para no recorrer una lista nula ni eliminar algo inexistente.
- **Actualizar el contador `tamano`**: al eliminar, hay que decrementar `tamano` para que `obtenerPorPosicion` y otras operaciones que dependen del tamaño sigan siendo consistentes.
- **Referencias externas / índices "colgantes"**: si en algún momento se guardó la `Funcion` eliminada en otra parte del programa (por ejemplo, seleccionada por posición en `comprarEntrada`), esa referencia queda apuntando a un objeto que ya no forma parte de la lista. Cualquier operación posterior sobre esa función (por ejemplo, consultar o vender puestos) seguirá "funcionando" sobre el objeto en memoria, pero **ya no reflejará el estado real de la lista**, lo cual puede generar inconsistencias.
- **Recorridos en curso**: si se está iterando la lista (por ejemplo, en `iniciarLabores` o `comprarEntrada`) y en paralelo se elimina un nodo, el puntero `actual` podría quedar apuntando a un nodo que ya no está enlazado a la lista, produciendo comportamiento inesperado. Por eso, eliminar y recorrer no deberían hacerse al mismo tiempo sobre la misma estructura sin control.
- **Liberar referencias**: después de desconectar el nodo, es buena práctica poner `actual.setSiguiente(null)` para evitar que el nodo eliminado siga referenciando accidentalmente al resto de la lista.

En resumen, la clave para eliminar de forma segura es: **ubicar bien el nodo anterior, manejar el caso del `head`, actualizar el tamaño, y evitar que queden referencias "sueltas" o recorridos inconsistentes** con el resto del programa.
