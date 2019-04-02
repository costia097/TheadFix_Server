package core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStateMessage {

    @JsonProperty("PlayerId")
    private String playerId;

    @JsonProperty("IsWatchToRightDirection")
    private boolean isWatchToRightDirection;

    @JsonProperty("IsRunning")
    private boolean isRunning;

    @JsonProperty("IsSlashing")
    private boolean isSlashing;

    @JsonProperty("X")
    private float x;

    @JsonProperty("Y")
    private float y;

    @JsonProperty("Z")
    private float z;

    @JsonProperty("RotationX")
    private float rotationX;

    @JsonProperty("RotationY")
    private float rotationY;

    @JsonProperty("RotationZ")
    private float rotationZ;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public boolean isWatchToRightDirection() {
        return isWatchToRightDirection;
    }

    public void setWatchToRightDirection(boolean watchToRightDirection) {
        isWatchToRightDirection = watchToRightDirection;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isSlashing() {
        return isSlashing;
    }

    public void setSlashing(boolean slashing) {
        isSlashing = slashing;
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

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getRotationX() {
        return rotationX;
    }

    public void setRotationX(float rotationX) {
        this.rotationX = rotationX;
    }

    public float getRotationY() {
        return rotationY;
    }

    public void setRotationY(float rotationY) {
        this.rotationY = rotationY;
    }

    public float getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(float rotationZ) {
        this.rotationZ = rotationZ;
    }
}
