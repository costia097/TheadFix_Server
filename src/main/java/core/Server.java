package core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Server {
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static Queue<String> queueToRead = new LinkedBlockingQueue<>();

    private final static String DELIMETER = "\r\n";

    private static void eventChecker() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("SERVER EVENT CHECKER IS WORKING");
                    if (inputStream.available() > 0) {
                        System.out.println("SERVER RECEIVED MESSAGE");
                        queueToRead.add(takeMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private static String takeMessage() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (inputStream.available() != 0) {
            byte read = (byte) inputStream.read();
            byteArrayOutputStream.write(read);
        }
        return new String(byteArrayOutputStream.toByteArray());
    }

    private static void sendMessage() throws IOException {
        String message = "HELLo from SERVER\n";
        outputStream.write(message.getBytes());
        outputStream.flush();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(27015);
        Socket socket = serverSocket.accept();
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
        eventChecker();

        while (true) {
            System.out.println("SERVER SLEEP");
            Thread.sleep(1000);

            if (queueToRead.size() > 0) {
                prepareQueue();
                String poll = queueToRead.poll();
                System.out.println("PROCESSED MESSAGE: " + poll);
            }
            sendMessage();
        }
    }

    private static void prepareQueue() {
        queueToRead = queueToRead.stream()
                .map(s -> s.split(DELIMETER))
                .flatMap(Stream::of)
                .collect(Collectors.toCollection(LinkedBlockingQueue::new));
    }
}
