# Micro Spring Boot Framework

Este proyecto es un microframework inspirado en Spring Boot, diseñado para simplificar la creación de aplicaciones web en Java. Proporciona una estructura básica para manejar solicitudes HTTP, definir controladores y gestionar rutas de manera eficiente.

## ✨ Características

- **Controladores anotados**: Usa anotaciones personalizadas para definir controladores y manejar solicitudes HTTP.
- **Gestión de rutas**: Permite mapear rutas a métodos de controlador con anotaciones.
- **Parámetros en solicitudes**: Soporta la extracción de parámetros en las peticiones HTTP.
- **Archivos estáticos**: Capacidad para servir HTML, CSS y JavaScript sin configuración adicional.

## 📂 Estructura del Proyecto

```
src
└── main
    └── java
        └── co
            └── edu
                └── escuelaing
                    └── reflexionlab
                        ├── anotaciones
                        │   ├── GetMapping.java
                        │   ├── RequestParam.java
                        │   └── RestController.java
                        ├── controlador
                        │   ├── GreetingController.java
                        │   ├── RequestHandler.java
                        │   └── TestController.java
                        └── MicroSpringBoot.java
```

## 🚀 Instalación y Ejecución

1. Clona el repositorio:

   ```bash
   git clone https://github.com/AREPLABS/Complete_Framework
   ```

2. Accede al directorio del proyecto:

   ```bash
   cd micro-spring-boot
   ```

3. Compila el proyecto con Maven:

   ```bash
   mvn clean install
   ```

4. Ejecuta la aplicación:

   ```bash
   java -cp target/micro-spring-boot-1.0-SNAPSHOT.jar co.edu.escuelaing.reflexionlab.MicroSpringBoot
   ```

## 🌍 Uso de la Aplicación

Una vez en ejecución, puedes probar los siguientes endpoints:

- **Obtener un saludo**  
  `GET http://localhost:35000/greeting`  
  Respuesta: `"Hello, World!"`

- **Saludo personalizado**  
  `GET http://localhost:35000/greeting?name=TuNombre`  
  Respuesta: `"Hello, TuNombre!"`

## 🔖 Anotaciones Personalizadas

Este microframework incorpora anotaciones para facilitar la creación de controladores y la gestión de solicitudes HTTP.

### `@RestController`
Define una clase como un controlador REST, indicando que manejará solicitudes HTTP y devolverá datos en formato JSON o texto.

```java
@RestController
public class GreetingController {
    // Métodos del controlador
}
```

### `@GetMapping`
Asocia un método con una ruta específica para solicitudes GET.

```java
@GetMapping("/greeting")
public String greeting() {
    return "Hello, World!";
}
```

### `@RequestParam`
Permite extraer parámetros de la solicitud HTTP y asignarlos a variables del método.

```java
@GetMapping("/greeting")
public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("Hello, %s!", name);
}
```

📌 *Si no se proporciona el parámetro `name`, el valor predeterminado será `"World"`*.

## 📌 Ejemplo Completo de Controlador

```java
@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello, %s!", name);
    }
}
```

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! Para colaborar, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama:  
   ```bash
   git checkout -b feature/nueva-caracteristica
   ```
3. Realiza tus cambios y haz commit:  
   ```bash
   git commit -m "Agrega nueva característica"
   ```
4. Envía un pull request.

## 📜 Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## 📬 Contacto

Si tienes preguntas o sugerencias, puedes contactarme en:  
📧 **imbox**

### ✅ Mejoras realizadas:
- **Mayor claridad**: Se simplificaron frases y se mejoró la legibilidad.
- **Formato más atractivo**: Se agregaron emojis y se reorganizó la información para facilitar la lectura.
- **Corrección de nombres de paquetes**: Ahora siguen una convención estándar (`anotaciones`, `controlador` en minúscula).
- **Explicaciones más concisas**: Se hicieron más directas las explicaciones de cada anotación.
