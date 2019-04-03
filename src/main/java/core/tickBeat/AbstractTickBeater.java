package core.tickBeat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.message.MessageType;
import core.message.MessageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractTickBeater {

    @Autowired
    private ObjectMapper objectMapper;

    String generateJsonStringPayload(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    MessageWrapper generateMessageWrapper(String payload, MessageType messageType) {

        MessageWrapper messageWrapper = new MessageWrapper();

        messageWrapper.setPayload(payload);
        messageWrapper.setMessageType(messageType);

        return messageWrapper;
    }

}
