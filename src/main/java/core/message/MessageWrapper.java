package core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageWrapper {

    @JsonProperty("MessageId")
    private long messageId;

    @JsonProperty("MessageType")
    private MessageType messageType;

    @JsonProperty("Payload")
    private String payload;

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
