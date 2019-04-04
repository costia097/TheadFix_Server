package core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroundBoxElementDestructMessage {

    @JsonProperty("Id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
