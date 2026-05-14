# scolombo-Geno-Insights-Challenge

El contexto
Imaginá una planta industrial. Todos los días llegan personas al ingreso peatonal de la fábrica.
En la entrada hay un guardia cuyo trabajo es saber exactamente quién ingresó y salió en cada
momento del día, su nombre, empresa, sector, hora de ingreso. Si pasa algo adentro, ese
registro es lo único que dice quién estaba ahí y cuándo.
El problema es que no tenían ningún sistema para hacerlo bien. Dependían de planillas en
papel para registrar los ingresos. El guardia vivía ese dolor todos los días.El guardia decidió
resolverlo. Usando ChatGPT como único compañero de programación, construyó una app
web que digitaliza todo el proceso: el guardia registra a cada visitante con nombre, DNI, sector
y foto desde la webcam y luego el sistema genera una credencial con código QR único para
esa persona.
Con esta solución, cuando la persona vuelve a ingresar muestra su QR en la puerta y el sistema
la registra sin necesidad de volver a cargar sus datos.
El problema: todos los datos viven en el navegador de esa única PC. Si alguien limpia el caché,
si se necesita consultar desde otra computadora, si dos guardias trabajan en simultáneo, todo
se pierde. No hay backend, no hay base de datos, no hay respaldo.
Punto de partida
Para que no empieces desde cero, te dejamos el estado actual del sistema funcionando en
vivo. Es un archivo HTML único, sin dependencias locales, que podés abrir desde cualquier
browser y usar de inmediato (puede tener limitaciones o funcionalidades que no funcionan del
todo bien).
APP EN PRODUCCION
https://hiring-challenge-gestion-de-ingresos.netlify.app/
Abrila en el browser, explorala y tene en cuenta lo que funciona y lo que no antes de arrancar.

Lo que ya tiene:
● Stack: HTML / CSS / JS puro, sin frameworks ni bundlers
● Almacenamiento: localStorage del navegador (esto es lo que hay que migrar)
● Librerías vía CDN: qrcode.min.js, html5-qrcode, xlsx.full.min.js
● Deploy actual: Netlify, como archivo estático

geno insights · Hiring Challenge

Confidencial · Geno Insights

Consigna
La idea es simple: tomar lo que el equipo ya construyó y convertirlo en algo que pueda vivir en
producción de verdad. Eso implica dos cosas:
● Backend. Un backend real con persistencia en la nube. Los registros tienen que sobrevivir
a cierres de pestaña, cambios de dispositivo y limpiezas de caché.
● Frontend. Un frontend que siga siendo igual de usable. No hace falta rediseñar nada, pero
tiene que conectar con el nuevo backend y funcionar desde cualquier PC.
Acá va el detalle de lo que esperamos ver en la versión productivizada:
FUNCIONALIDADES REQUERIDAS
1. Registro de visitante: nombre, DNI, empresa, sector, foto capturada desde la webcam
2. Generación y visualización de credencial con código QR único por visita
3. Búsqueda de visitante por DNI
4. Los datos deben persistir en un backend, no en localStorage
STRETCH GOAL: SI TE SOBRA TIEMPO
Escáner QR para validar reingresos desde la misma interfaz (requiere acceso a cámara desde el
browser + validación contra el backend)
Requisitos técnicos
Más allá del stack que elijas, hay algunas cosas que no son opcionales:
1. La app tiene que estar deployada y accesible por URL pública. Sin deploy, no hay entrega.
2. El código tiene que estar en un repositorio GitHub con commits que cuenten la historia de
cómo llegaste hasta acá.
3. El backend tiene que correr en un servicio en la nube (el que mejor conozcas).
4. La foto del visitante tiene que almacenarse, no solo mostrarse durante la sesión.
5. Tiene que haber al menos un mecanismo básico de autenticación, aunque sea un PIN o una
cuenta de guardia, para evitar que cualquiera con la URL pueda acceder.
El resto es tuyo. Podés cambiar el stack del frontend, simplificar partes del flujo, agregar
validaciones, lo que creas que tiene sentido. No te pedimos que te ciñas al código original línea
por línea.
Cómo entregar
Cuando estés listo, mandá:
1. URL de la aplicación deployada.
2. Link al repositorio en GitHub (público o con acceso compartido).
3. Las preguntas que te harías antes de escalar esto a producción real: qué suponés, qué
validarías, qué cambiarías.

Cualquier duda antes o durante, escribinos.
