package com.xiaopy.example.dns.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.dns.TcpDnsQueryEncoder;
import io.netty.handler.codec.dns.TcpDnsResponseDecoder;

/**
 * @author xiaopeiyu
 * @since 2020/11/21
 */
public class DnsInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new TcpDnsQueryEncoder())
                .addLast(new TcpDnsResponseDecoder())
                .addLast(new DnsHandler());
    }
}
