
package bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final  static int PORT = 8765;
    public static void main(String[] args){
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server start...");

            Socket socket = serverSocket.accept();
            new Thread(new ServerHandler(socket)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
