
package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler implements Runnable{

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket  = socket;
    }

    @Override
    public void run() {
        BufferedReader in  = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            String body ;
            while(true){
                body = in.readLine();
                if(body==null)break;
                System.out.println("Server:"+body);
                out.println("服务器端回送响应的数据");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
