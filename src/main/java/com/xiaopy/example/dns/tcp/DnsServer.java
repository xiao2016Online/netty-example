package com.xiaopy.example.dns.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.dns.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaopeiyu
 * @since 2020/11/21
 */
public class DnsServer {

    private static final String QUERY_DOMAIN = "www.baidu.com";
    private static final int DNS_SERVER_PORT = 53;
    private static final String DNS_SERVER_HOST = "223.5.5.5";

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new DnsInitializer());
            Channel ch = bootstrap.connect(DNS_SERVER_HOST, DNS_SERVER_PORT).sync().channel();

            int randomId = new Random().nextInt(60000 - 1000) + 1000;
            DnsQuery query = new DefaultDnsQuery(randomId, DnsOpCode.QUERY)
                    .setRecord(DnsSection.QUESTION, new DefaultDnsQuestion(QUERY_DOMAIN, DnsRecordType.A));
            ch.writeAndFlush(query).sync();
            boolean success = ch.closeFuture().await(10, TimeUnit.SECONDS);
            if (!success) {
                System.err.println("dns query timeout!");
                ch.close().sync();
            }
        } finally {
            workGroup.shutdownGracefully();
        }

    }
}
