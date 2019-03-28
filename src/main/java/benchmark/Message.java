package benchmark;

import java.io.Serializable;

public class Message implements Serializable {
    private int value;

    public Message() {
    }

    public Message(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
