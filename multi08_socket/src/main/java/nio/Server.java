
package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Server implements Runnable{
    //缓冲区大小
    private static final int SIZE = 1<<10;

    private Selector selector;
    private ByteBuffer readbuf = ByteBuffer.allocate(SIZE);
    private ByteBuffer writeBuf = ByteBuffer.allocate(SIZE);

    public Server(int port) {
        try {
            selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(port));
            //把服务注册到多路复用器上，并且监听阻塞事件
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server start , port : "+ port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {


    }
}
