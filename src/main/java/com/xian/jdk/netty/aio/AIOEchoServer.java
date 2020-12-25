package com.xian.jdk.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

/**
 * @Description: 进行异步编程。但是当客户端退出的时候，会一直输出Thread-5持续接收消息中
 * @Author: Summer
 * @DateTime: 2020/12/24 8:35 下午
 * @Version: 0.0.1-SNAPSHOT
 */
public class AIOEchoServer {
    public static void main(String[] args) throws IOException {
        // 启动服务端
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8003));

        System.out.println("server start");

        // 监听accept事件，完全异步，不会阻塞
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Object attachment) {
                try {
                    System.out.println(Thread.currentThread().getName() + "accept new conn: " + socketChannel.getRemoteAddress());
                    // 再次监听accept事件
                    serverSocketChannel.accept(null, this);
                    System.out.println(Thread.currentThread().getName() + "消息处理");
                    // 消息的处理
                    while (true) {
                        System.out.println(Thread.currentThread().getName() + "持续接收消息中");
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        // 将数据读入到buffer中
                        Future<Integer> future = socketChannel.read(buffer);
                        if (future.get() > 0) {
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            // 将数据读入到byte数组中
                            buffer.get(bytes);

                            String content = new String(bytes, "UTF-8");
                            // 换行符会当成另一条消息传过来
                            if (content.equals("\r\n")) {
                                continue;
                            }
                            System.out.println(Thread.currentThread().getName() + "receive msg: " + content);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("failed");
            }
        });

        // 阻塞住主线程
        System.in.read();
    }
}
