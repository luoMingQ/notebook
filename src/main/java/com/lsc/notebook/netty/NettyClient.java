package com.lsc.notebook.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty 客户端
 * @Author: luosc
 * @Description:
 * @Date:created in 11:31 2020/5/8
 */
public class NettyClient {

    private static Logger logger = LoggerFactory.getLogger(NettyClient.class);
    /** 主机 */
    private String host;

    /** 端口号 */
    private int port;

    private Date endTime;
    /**
     * 构造函数
     * @param host
     * @param port
     */
    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    Channel channel;

    /**
     * 连接方法
     */
    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            // bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new NettyClientInitializer());
            channel = bootstrap.connect(host, port).sync().channel();
            // 发送json字符串
            String msg = "{\"name\":\"admin\",\"age\":27}\n";
            channel.writeAndFlush(msg);
            //channel.closeFuture().sync();
            closeChannel(channel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    /**
     * 单独发送消息
     * return
     * Author luosc
     * param
     * Date 2020/5/8 15:04
     */
    public void sendMsgToServer() {
        String msg = "{\"name\":\"admin1111\",\"age\":27111}\n";
        channel.writeAndFlush(msg);
    }

    /**
     * 关闭通道
     * return
     * Author luosc
     * param
     * Date 2020/5/8 15:30
     */
    public void closeChannel(Channel channel) {
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("关闭通道异常："+e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 测试入口
     *
     * @param args
     */
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8088;
        NettyClient nettyClient = new NettyClient(host, port);
        nettyClient.connect();
        //nettyClient.sendMsgToServer();
    }
}
