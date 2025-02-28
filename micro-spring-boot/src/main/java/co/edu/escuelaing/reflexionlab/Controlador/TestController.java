package co.edu.escuelaing.reflexionlab;

@RestController
public class TestController {

  @GetMapping("/hello")
  public String hello(
    @RequestParam(value = "name", defaultValue = "World") String name
  ) {
    return "Hello, " + name + "!";
  }
}
