package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.message.ClientJoinMessage;
import core.message.MessageType;
import core.message.MessageWrapper;
import core.message.PlayerMoveMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TcpServer {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static List<ServerPlayer> players = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });

            ChannelFuture localhost = serverBootstrap.bind("localhost", 27015)
                    .sync().
                            channel()
                    .closeFuture()
                    .sync();

            System.out.println(localhost.isSuccess());

        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

    private static class ChatServerHandler extends SimpleChannelInboundHandler<String> {
        static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) {

            players.forEach(serverPlayer -> System.out.println("PLAYER NAME: " + serverPlayer.getName() + "position x: " + serverPlayer.getX() + " y: " + serverPlayer.getY()));

            Channel incomingChannel = ctx.channel();

            try {
                MessageWrapper inputMessageWrapper = objectMapper.readValue(msg, MessageWrapper.class);

                switch (inputMessageWrapper.getMessageType()) {
                    case PLAYER_JOIN:
                        System.out.println("RETRIEVE PLAYER_JOIN message" + msg);

                        ClientJoinMessage clientJoinMessage = objectMapper.readValue(inputMessageWrapper.getPayload(), ClientJoinMessage.class);
                        ServerPlayer serverPlayer = new ServerPlayer();

                        serverPlayer.setName(clientJoinMessage.getPlayerId());

                        serverPlayer.setX(0);
                        serverPlayer.setY(0);
                        serverPlayer.setZ(0);

                        String playersMessageJson = objectMapper.writeValueAsString(players);

                        MessageWrapper messageWrapper = new MessageWrapper();
                        messageWrapper.setMessageType(MessageType.FIRST_SYNC);
                        messageWrapper.setPayload(playersMessageJson);

                        String messageWrapperJson = objectMapper.writeValueAsString(messageWrapper);

                        incomingChannel.writeAndFlush(messageWrapperJson + "\n");

                        players.add(serverPlayer);

                        break;
                    case PLAYER_MOVE:
                        System.out.println("RETRIEVE PLAYER_MOVE message" + msg);

                        PlayerMoveMessage playerMoveMessage = objectMapper.readValue(inputMessageWrapper.getPayload(), PlayerMoveMessage.class);

                        String playerId = playerMoveMessage.getPlayerId();

                        for (ServerPlayer player : players) {
                            if (player.getName().equals(playerId)) {

                                player.setX(playerMoveMessage.getX());
                                player.setY(playerMoveMessage.getY());
                            }
                        }

                        break;
                    default:
                        throw new RuntimeException();
                }

                for (Channel channel : channels) {
                    if (channel != incomingChannel) {
                        channel.writeAndFlush(msg + "\n");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            Channel incoming = ctx.channel();
//            for (Channel channel : channels) {
//                channel.writeAndFlush("[SERVER] " + incoming.remoteAddress() + "has joined!!" );
//            }

            String s = incoming.remoteAddress().toString();
            System.out.println("Client with remoteAddress " + s + " connected!!!");
            channels.add(incoming);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) {
            Channel incoming = ctx.channel();
//            for (Channel channel : channels) {
//                channel.writeAndFlush("[SERVER] " + incoming.remoteAddress() + "has left!!");
//            }
            channels.remove(incoming);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            super.channelRegistered(ctx);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            super.channelUnregistered(ctx);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            super.channelReadComplete(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            super.userEventTriggered(ctx, evt);
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            super.channelWritabilityChanged(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }
    }
}
