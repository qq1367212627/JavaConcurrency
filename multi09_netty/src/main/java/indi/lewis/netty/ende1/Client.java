
package indi.lewis.netty.ende1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Client {

    public static void main(String[] agrs) throws InterruptedException {
        EventLoopGroup group  = new NioEventLoopGroup();//
        Bootstrap boot = new Bootstrap();//配置工具类
        boot.group(group).//绑定线程组
        channel(NioSocketChannel.class).//指定通信模式：客户端 NIO 模式
        handler(new ChannelInitializer<SocketChannel>() {//
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());//特殊标识字符，表示一个消息包的结束
                socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1<<10,buf));
                socketChannel.pipeline().addLast(new StringDecoder());//字符解码器
                socketChannel.pipeline().addLast(new ClientHandler());

            }
        });
        ChannelFuture cf = boot.connect("127.0.0.1",8000).sync();//
        cf.channel().writeAndFlush(Unpooled.copiedBuffer("777777$_".getBytes()));//通过通道读写数据，从管道冲刷到服务端
        cf.channel().writeAndFlush(Unpooled.copiedBuffer("8885555$_".getBytes()));//
        cf.channel().closeFuture().sync();//阻塞
        group.shutdownGracefully();//
    }
}
