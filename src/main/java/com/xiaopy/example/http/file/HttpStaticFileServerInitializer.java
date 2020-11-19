package com.xiaopy.example.http.file;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 初始化器
 *
 * @author xiaopeiyu
 * @since 2020/11/19
 */
public class HttpStaticFileServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // http 解码器
        pipeline.addLast(new HttpServerCodec());
        // 聚合内容最大长度
        pipeline.addLast(new HttpObjectAggregator(65536));
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 自定义处理器
        pipeline.addLast(new HttpStaticFileServerHandler());
    }
}
