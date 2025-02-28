console.log("Hello from main.js");

const annotations = [
    {
        name: "@RestController",
        description: "Esta anotación se utiliza para marcar una clase como controlador RESTful. Combina @Controller y @ResponseBody.",
        example: `@RestController
@RequestMapping("/api")
public class UserController {
    // Métodos del controlador
}`,
        creation: `Para crear esta anotación, utiliza las siguientes definiciones:
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestController {}`,
        keyPoints: [
            "Indica que la clase manejará solicitudes HTTP",
            "Automáticamente serializa las respuestas a JSON/XML",
            "Elimina la necesidad de usar @ResponseBody en cada método"
        ],
        tags: ["Controller", "REST", "Spring MVC"],
        image: "yaya.jpg" // URL de la imagen
    },
    {
        name: "@GetMapping",
        description: "Esta anotación se utiliza para mapear solicitudes HTTP GET a métodos específicos en un controlador.",
        example: `@GetMapping("/users")
public List<User> getUsers() {
    // Lógica para obtener usuarios
}`,
        creation: `Para crear esta anotación, utiliza las siguientes definiciones:
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GetMapping {
    String[] value();
}`,
        keyPoints: [
            "Simplifica la definición de rutas para solicitudes GET",
            "Se puede usar con parámetros de consulta",
            "Facilita la creación de APIs RESTful"
        ],
        tags: ["Mapping", "HTTP", "Spring MVC"],
        image: "url_de_la_imagen_get_mapping.jpg" // URL de la imagen
    },
    {
        name: "@RequestParam",
        description: "Esta anotación se utiliza para extraer parámetros de consulta de la URL en métodos de controlador.",
        example: `@GetMapping("/users")
public List<User> getUsers(@RequestParam("age") int age) {
    // Lógica para obtener usuarios por edad
}`,
        creation: `Para crear esta anotación, utiliza las siguientes definiciones:
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
    String value();
    String defaultValue() default ""; // Asegúrate de que tenga un valor por defecto
}`,
        keyPoints: [
            "Permite la validación de parámetros de entrada",
            "Se puede establecer un valor por defecto",
            "Facilita la manipulación de parámetros de consulta"
        ],
        tags: ["Parameter", "HTTP", "Spring MVC"],
        image: "url_de_la_imagen_request_param.jpg" // URL de la imagen
    }
];

function createAnnotationCard(annotation) {
    return `
        <div class="annotation-card">
            <h2 class="annotation-name">${annotation.name}</h2>
            <img src="${annotation.image}" alt="${annotation.name} image" class="annotation-image" />
            <p class="annotation-description">${annotation.description}</p>
            <pre class="code-example"><code>${annotation.example}</code></pre>
            <h3>Cómo crear:</h3>
            <pre class="code-creation"><code>${annotation.creation}</code></pre>
            <ul class="key-points">
                ${annotation.keyPoints.map(point => `<li>${point}</li>`).join('')}
            </ul>
            <div class="tags">
                ${annotation.tags.map(tag => `<span class="tag">${tag}</span>`).join('')}
            </div>
        </div>
    `;
}

function displayAnnotations(annotationsToShow) {
    const container = document.getElementById('annotationsContainer');
    container.innerHTML = annotationsToShow.map(annotation => createAnnotationCard(annotation)).join('');
}

function setupSearch() {
    const searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('input', (e) => {
        const searchTerm = e.target.value.toLowerCase();
        const filteredAnnotations = annotations.filter(annotation => 
            annotation.name.toLowerCase().includes(searchTerm) ||
            annotation.description.toLowerCase().includes(searchTerm) ||
            annotation.tags.some(tag => tag.toLowerCase().includes(searchTerm))
        );
        displayAnnotations(filteredAnnotations);
    });
}

document.addEventListener('DOMContentLoaded', () => {
    displayAnnotations(annotations);
    setupSearch();
});