package com.sshmanager.model;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    // Connection Parameters
    private int connectTimeout = 60000;
    private int aliveTestTimeout = 20000;
    private int aliveTestFrequency = 5000;
    private int retryDelay = 3000;
    // UI
    private int width = 800;
    private int height = 600;
    private boolean connectionList = true;
    private boolean favoritesOnly = false;
    private boolean closeToTray = false;
    private Map<String, String> connectionColors = new HashMap<>();

    public Configuration() {}
    public int getConnectTimeout() { return connectTimeout; }
    public void setConnectTimeout(int connectTimeout) { this.connectTimeout = connectTimeout; }
    public int getAliveTestTimeout() { return aliveTestTimeout; }
    public void setAliveTestTimeout(int aliveTestTimeout) { this.aliveTestTimeout = aliveTestTimeout; }
    public int getAliveTestFrequency() { return aliveTestFrequency; }
    public void setAliveTestFrequency(int aliveTestFrequency) { this.aliveTestFrequency = aliveTestFrequency; }
    public int getRetryDelay() { return retryDelay; }
    public void setRetryDelay(int retryDelay) { this.retryDelay = retryDelay; }
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public boolean isConnectionList() { return connectionList; }
    public void setConnectionList(boolean connectionList) { this.connectionList = connectionList; }
    public boolean isFavoritesOnly() { return favoritesOnly; }
    public void setFavoritesOnly(boolean favoritesOnly) { this.favoritesOnly = favoritesOnly; }
    public boolean isCloseToTray() { return closeToTray; }
    public void setCloseToTray(boolean closeToTray) { this.closeToTray = closeToTray; }
    public Map<String, String> getConnectionColors() { return connectionColors; }
    public void setConnectionColors(Map<String, String> connectionColors) { this.connectionColors = connectionColors; }
}