package v2.tcp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TcpClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder", new StringDecoder());
                        pipeline.addLast("encoder", new StringEncoder());
                        pipeline.addLast(new ChatClientHandler());
                    }
                });

        Channel localhost = bootstrap.connect("localhost", 27015).sync().channel();


        InputStream in = System.in;

        while (true) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String s = bufferedReader.readLine();
            localhost.writeAndFlush(s);
        }
    }

    private static class ChatClientHandler extends SimpleChannelInboundHandler {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
            System.out.println("[CLIENT] " + ctx.channel().remoteAddress() + " " + msg);
        }

    }
}
