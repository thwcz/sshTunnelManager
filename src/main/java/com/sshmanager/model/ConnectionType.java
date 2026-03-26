package com.sshmanager.model;

public class ConnectionType {
    private String name;
    private int port;
    private boolean isDoubleTunnel = true;

    public ConnectionType() {}
    public ConnectionType(String name, int port, boolean isDoubleTunnel) {
        this.name = name;
        this.port = port;
        this.isDoubleTunnel = isDoubleTunnel;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    public boolean isDoubleTunnel() { return isDoubleTunnel; }
    public void setDoubleTunnel(boolean doubleTunnel) { isDoubleTunnel = doubleTunnel; }
}