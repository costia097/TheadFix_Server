package builder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroundBoxElement {

    @JsonProperty("Id")
    private int id;

    @JsonProperty("X")
    private float x;

    @JsonProperty("Y")
    private float y;

    @JsonProperty("IsVisible")
    private boolean isVisible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
