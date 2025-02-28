package co.edu.escuelaing.reflexionlab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {

  private final Socket clientSocket;
  private final Object[] controllerInstances;

  public RequestHandler(Socket clientSocket, Object[] controllerInstances) {
    this.clientSocket = clientSocket;
    this.controllerInstances = controllerInstances;
  }

  @Override
  public void run() {
    try (
      BufferedReader in = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream())
      );
      OutputStream out = clientSocket.getOutputStream()
    ) {
      String requestLine = in.readLine();
      if (requestLine != null && requestLine.startsWith("GET")) {
        String uri = requestLine.split(" ")[1];
        String response = handleGetRequest(uri);
        out.write(response.getBytes(StandardCharsets.UTF_8));
        out.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String handleGetRequest(String uri) {
    System.out.println("Handling GET request for URI: " + uri);
    if (uri.equals("/")) {
      uri = "/index.html";
    }

    // Intentar servir un archivo estático
    File file = new File("src/main/resources/static" + uri);
    if (file.exists()) {
      return serveFile(file);
    }

    // Si no se encuentra el archivo, buscar en los controladores
    try {
      for (Object controllerInstance : controllerInstances) {
        Method[] methods = controllerInstance.getClass().getDeclaredMethods();
        for (Method method : methods) {
          if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping mapping = method.getAnnotation(GetMapping.class);
            for (String path : mapping.value()) {
              if (uri.equals(path)) {
                // Aquí se invoca el método del controlador
                Map<String, String> params = extractParams(uri);
                Object[] args = new Object[method.getParameterCount()];

                // Rellenar los parámetros según el tipo de datos esperado
                for (int i = 0; i < method.getParameterCount(); i++) {
                  Class<?> paramType = method.getParameterTypes()[i];
                  String paramName = method.getParameters()[i].getName();
                  String paramValue = params.getOrDefault(paramName, "");

                  // Convertir tipos básicos si es necesario
                  if (paramType == int.class || paramType == Integer.class) {
                    args[i] =
                      paramValue.isEmpty() ? 0 : Integer.parseInt(paramValue);
                  } else if (
                    paramType == double.class || paramType == Double.class
                  ) {
                    args[i] =
                      paramValue.isEmpty()
                        ? 0.0
                        : Double.parseDouble(paramValue);
                  } else {
                    args[i] = paramValue; // String por defecto
                  }
                }

                // Invocar el método y obtener la respuesta
                String responseBody = (String) method.invoke(
                  controllerInstance,
                  args
                );

                // Enviar la respuesta con las cabeceras adecuadas
                return (
                  "HTTP/1.1 200 OK\r\n" +
                  "Content-Type: text/html; charset=UTF-8\r\n" +
                  "Content-Length: " +
                  responseBody.length() +
                  "\r\n" +
                  "\r\n" +
                  responseBody
                );
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "HTTP/1.1 500 Internal Server Error\r\n\r\n";
    }

    return "HTTP/1.1 404 Not Found\r\n\r\n";
  }

  private String serveFile(File file) {
    try (FileInputStream fis = new FileInputStream(file)) {
      byte[] fileData = new byte[(int) file.length()];
      fis.read(fileData);

      // Determinar el tipo de contenido basado en la extensión del archivo
      String contentType = "text/html"; // Por defecto
      if (file.getName().endsWith(".css")) {
        contentType = "text/css";
      } else if (file.getName().endsWith(".js")) {
        contentType = "application/javascript";
      } else if (file.getName().endsWith(".png")) {
        contentType = "image/png";
      } else if (
        file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")
      ) {
        contentType = "image/jpeg";
      } else if (file.getName().endsWith(".gif")) {
        contentType = "image/gif";
      }

      return (
        "HTTP/1.1 200 OK\r\n" +
        "Content-Type: " +
        contentType +
        "; charset=UTF-8\r\n" +
        "Content-Length: " +
        fileData.length +
        "\r\n" +
        "\r\n" +
        new String(fileData, StandardCharsets.UTF_8)
      );
    } catch (IOException e) {
      e.printStackTrace();
      return "HTTP/1.1 500 Internal Server Error\r\n\r\n";
    }
  }

  private Map<String, String> extractParams(String uri) {
    Map<String, String> params = new HashMap<>();
    if (uri.contains("?")) {
      String[] parts = uri.split("\\?");
      if (parts.length > 1) {
        String queryString = parts[1];
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
          String[] keyValue = pair.split("=");
          if (keyValue.length == 2) {
            try {
              String key = URLDecoder.decode(
                keyValue[0],
                StandardCharsets.UTF_8.name()
              );
              String value = URLDecoder.decode(
                keyValue[1],
                StandardCharsets.UTF_8.name()
              );
              params.put(key, value);
            } catch (UnsupportedEncodingException e) {
              e.printStackTrace(); // Manejo de excepciones
            }
          }
        }
      }
    }
    return params;
  }
}
