package benchmark.json;

import benchmark.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Message> messages = new ArrayList<>();

        long start = System.currentTimeMillis();

        for (int i = 0; i < 1_000_000; i++) {
            messages.add(new Message(i));
        }

        for (Message message : messages) {
            String s = objectMapper.writeValueAsString(message);
        }

        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start));
    }
}
