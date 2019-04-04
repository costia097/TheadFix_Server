package core.service;

import builder.GroundBoxElement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.message.GroundBoxElementDestructMessage;
import core.message.MessageType;
import core.message.MessageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// 0.54 per x offset
@Service
public class MapService {

    @Autowired
    private NetworkService networkService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<GroundBoxElement> map = new ArrayList<>();

    public void generateMap() {

        float lastX = 1.0f;
        float lastY = -4.69f;

        for (int i = 0; i < 30; i++) {
            /*
            x = 10.801 y = -4.702
             */

            GroundBoxElement groundBoxElement = new GroundBoxElement();

            groundBoxElement.setId(i);
            groundBoxElement.setVisible(true);
            groundBoxElement.setX(lastX);
            groundBoxElement.setY(lastY);

            map.add(groundBoxElement);

            lastX += 0.54f;
        }

        int lastElementId = getLastElementId();
        float lastYValue = lastY;

        //TODO not working

        for (int i = 0; i < 5; i++) {
            GroundBoxElement groundBoxElement = new GroundBoxElement();
            groundBoxElement.setId(lastElementId);
            groundBoxElement.setY(lastYValue);
            groundBoxElement.setX(15);
            lastElementId++;
            lastYValue += 0.54f;
        }

        test();
    }

    private void test() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 20; i++) {

                map.stream().findFirst().ifPresent(groundBoxElement -> {
                    GroundBoxElementDestructMessage groundBoxElementDestructMessage = new GroundBoxElementDestructMessage();
                    groundBoxElementDestructMessage.setId(String.valueOf(groundBoxElement.getId()));

                    String s = generateJsonStringPayload(groundBoxElementDestructMessage);

                    MessageWrapper messageWrapper = generateMessageWrapper(s, MessageType.MapChanged);

                    String s1 = generateJsonStringPayload(messageWrapper);

                    networkService.sendMessageForAllPlayers(s1);

                    map.remove(groundBoxElement);

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        thread.start();
    }

    private String generateJsonStringPayload(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private MessageWrapper generateMessageWrapper(String payload, MessageType messageType) {

        MessageWrapper messageWrapper = new MessageWrapper();

        messageWrapper.setPayload(payload);
        messageWrapper.setMessageType(messageType);

        return messageWrapper;
    }

    public int getLastElementId() {
        return map.stream().max(Comparator.comparing(GroundBoxElement::getId))
                .map(GroundBoxElement::getId)
                .orElse(0);
    }

    public List<GroundBoxElement> getMap() {
        return map;
    }
}
