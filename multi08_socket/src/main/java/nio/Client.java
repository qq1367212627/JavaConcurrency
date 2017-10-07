
package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) {
        //创建连接的地址
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",8000);

        //声明连接通道
        SocketChannel sc = null;

        //建立缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        try{
            //打开通道
            sc = SocketChannel.open();
            //建立连接
            sc.connect(address);

            for(;;){
                byte[] bytes = new byte[1024];
                System.in.read(bytes);
                //读取放入数据
                buf.put(bytes);
                //对缓冲器进行复位
                buf.flip();
                //写入数据
                sc.write(buf);
                buf.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
