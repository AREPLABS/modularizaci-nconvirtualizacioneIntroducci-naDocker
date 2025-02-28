package co.edu.escuelaing.reflexionlab.Controlador;

import co.edu.escuelaing.reflexionlab.Anotaciones.GetMapping;
import co.edu.escuelaing.reflexionlab.Anotaciones.RequestParam;
import co.edu.escuelaing.reflexionlab.Anotaciones.RestController;

@RestController
public class TestController {

  @GetMapping("/hello")
  public String hello(
    @RequestParam(value = "name", defaultValue = "World") String name
  ) {
    return "Hello, " + name + "!";
  }
}
