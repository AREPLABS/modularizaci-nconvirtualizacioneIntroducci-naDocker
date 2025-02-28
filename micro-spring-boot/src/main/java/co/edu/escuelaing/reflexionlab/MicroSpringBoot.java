package co.edu.escuelaing.reflexionlab;

import java.net.ServerSocket;
import java.net.Socket;

public class MicroSpringBoot {

  public static void main(String[] args) throws Exception {
    Object[] controllers = new Object[] { new GreetingController() };

    ServerSocket serverSocket = new ServerSocket(35000);
    System.out.println("Servidor iniciado en el puerto 35000...");

    while (true) {
      Socket clientSocket = serverSocket.accept();
      new Thread(new RequestHandler(clientSocket, controllers)).start();
    }
  }
}
