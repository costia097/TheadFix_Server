package core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class ServerPlayer {

    @JsonProperty("Name")
    private String name;

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

    private float rotationX;

    private float rotationY;

    private float rotationZ;

    @JsonIgnore
    private BufferedReader bufferedReader;

    @JsonIgnore
    private BufferedWriter bufferedWriter;

    @JsonIgnore
    private Socket socket;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String toString() {
        return "ServerPlayer{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", rotationZ=" + rotationZ +
                '}';
    }
}
