
package indi.lewis.netty.ende2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Server {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup pgroup = new NioEventLoopGroup();//处理客户端建立连接
        EventLoopGroup cGroup = new NioEventLoopGroup();//处理客户端网络通信
        //工具类，用于服务器通道的配置
        ServerBootstrap bootstrap = new ServerBootstrap();//配置辅助类
        bootstrap.group(pgroup,cGroup).//绑定线程组
        channel(NioServerSocketChannel.class).//指定模式 ：服务器端 NIO模式
        option(ChannelOption.SO_BACKLOG,1<<10).//设置tcp缓冲区（tcp握手，tcp内核）
        option(ChannelOption.SO_SNDBUF,1<<15).//发送缓冲区大小
        option(ChannelOption.SO_RCVBUF,1<<15).//接受缓冲区大小
        option(ChannelOption.SO_KEEPALIVE,true).//是否保持连接
        childHandler(new ChannelInitializer<SocketChannel>() {//
           @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(5));//设置定长字符串接收
                socketChannel.pipeline().addLast(new StringDecoder());
                socketChannel.pipeline().addLast(new ServerHandler());//配置数据接受方法的处理
            }
        });
        ChannelFuture cf = bootstrap.bind(8000).sync();//端口绑定
        cf.channel().closeFuture().sync();// 阻塞
        pgroup.shutdownGracefully();//释放资源
        cGroup.shutdownGracefully();//
    }

}
