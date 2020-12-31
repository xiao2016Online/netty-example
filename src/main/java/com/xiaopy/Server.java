package com.xiaopy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author xiaopeiyu
 * @since 2020/11/17
 */
public class Server {
    public static void main(String[] args) {

        // 主线程
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                // 开启tcp keepalive
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(NioChannelOption.SO_KEEPALIVE,true)
                .channel(NioServerSocketChannel.class)
                // 开启日志
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new MineServerInitializer());
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(18088).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
