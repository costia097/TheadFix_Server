package benchmark.serialization;

import benchmark.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Message> messages = new ArrayList<>();

        long start = System.currentTimeMillis();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput = new ObjectOutputStream(byteArrayOutputStream);

        for (int i = 0; i < 1_000_000; i++) {
            messages.add(new Message(i));
        }

        for (Message message : messages) {
            objectOutput.writeObject(message);
        }

        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start));
    }
}
