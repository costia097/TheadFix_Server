package core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.Random;

public class ServerPlayer {
    private String name;
    private float x;
    private float y;
    private float z;
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
}
