package com.xiaopy.example.dns.tcp;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.dns.*;
import io.netty.util.NetUtil;

/**
 * @author xiaopeiyu
 * @since 2020/11/21
 */
public class DnsHandler extends SimpleChannelInboundHandler<DefaultDnsResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DefaultDnsResponse defaultDnsResponse) throws Exception {
        if (defaultDnsResponse.count(DnsSection.QUESTION) > 0) {
            DnsQuestion question = defaultDnsResponse.recordAt(DnsSection.QUESTION, 0);
            System.out.printf("name: %s%n", question.name());
        }
        for (int i = 0, count = defaultDnsResponse.count(DnsSection.ANSWER); i < count; i++) {
            DnsRecord record = defaultDnsResponse.recordAt(DnsSection.ANSWER, i);
            if (record.type() == DnsRecordType.A) {
                //just print the IP after query
                DnsRawRecord raw = (DnsRawRecord) record;
                System.out.println(NetUtil.bytesToIpAddress(ByteBufUtil.getBytes(raw.content())));
            }
        }

    }
}
