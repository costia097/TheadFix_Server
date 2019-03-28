package v2.tcp.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerTest {

    private static AtomicInteger counterReaded = new AtomicInteger();
    private static AtomicInteger counterSended = new AtomicInteger();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(27015);

        Thread thread1 = new Thread(() -> {
            while (true) {
                System.out.println("READED: " + counterReaded.get());
                System.out.println("SENDED: " + counterSended.get());
                System.out.println("================================");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();

        while (true) {
            Socket client = serverSocket.accept();

            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = client.getInputStream();
                    OutputStream outputStream = client.getOutputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                    while (true) {
                        if (inputStream.available() > 0) {
                            String s = bufferedReader.readLine();
                            if (s != null) {
                                counterReaded.incrementAndGet();
                            }
                        }
                        bufferedWriter.write("Hello from server");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        counterSended.incrementAndGet();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();

        }

    }
}
