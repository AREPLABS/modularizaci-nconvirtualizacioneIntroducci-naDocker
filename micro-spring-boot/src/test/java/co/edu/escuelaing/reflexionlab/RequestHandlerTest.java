package co.edu.escuelaing.reflexionlab;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import co.edu.escuelaing.reflexionlab.Controlador.TestController;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RequestHandlerTest {

  @Mock
  private Socket mockSocket;

  private RequestHandler requestHandler;
  private Object[] controllerInstances;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    controllerInstances = new Object[] { new TestController() };
    requestHandler = new RequestHandler(mockSocket, controllerInstances);
  }

  @Test
  void testHandleNotFoundRequest() throws Exception {
    String request = "GET /notfound HTTP/1.1\r\n\r\n";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(
      request.getBytes()
    );
    when(mockSocket.getInputStream()).thenReturn(inputStream);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    when(mockSocket.getOutputStream()).thenReturn(outputStream);

    requestHandler.run();

    String response = outputStream.toString();
    assertTrue(response.contains("404 Not Found"));
  }
}
