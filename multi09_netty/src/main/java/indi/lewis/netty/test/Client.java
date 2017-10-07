
package indi.lewis.netty.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.SocketChannel;

public class Client {

    public static void main(String[] agrs) throws InterruptedException {
        EventLoopGroup group  = new NioEventLoopGroup();//
        Bootstrap boot = new Bootstrap();//配置工具类
        boot.group(group).//绑定线程组
        channel(NioSocketChannel.class).//指定通信模式：客户端 NIO 模式
        handler(new ChannelInitializer<SocketChannel>() {//
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new ClientHandler());
            }
        });
        ChannelFuture cf = boot.connect("127.0.0.1",8000).sync();//
        Thread.sleep(1000);//
        cf.channel().writeAndFlush(Unpooled.copiedBuffer("777".getBytes()));//通过通道读写数据，从管道冲刷到服务端
        cf.channel().writeAndFlush(Unpooled.copiedBuffer("888".getBytes()));//
        Thread.sleep(1000);//
        cf.channel().writeAndFlush(Unpooled.copiedBuffer("999".getBytes()));//
        cf.channel().closeFuture().sync();//阻塞
        group.shutdownGracefully();//
    }
}
