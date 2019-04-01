package core.entities.enemy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Enemy {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("X")
    private float x;

    @JsonProperty("Y")
    private float y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Enemy{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
