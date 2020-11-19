package com.xiaopy;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author xiaopeiyu
 * @since 2020/11/17
 */
public class MineServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 通过channel 获取通道
        ChannelPipeline pipeline = channel.pipeline();

        // HttpServerCodec是由netty自己提供的助手类，可以理解为拦截器
        // 当请求到服务端，需要解码，响应到客户端做编码
        pipeline.addLast("codec",new HttpServerCodec());

        // 添加自定义拦截器？
        pipeline.addLast("customHandler1",new MineHandler());

    }
}
