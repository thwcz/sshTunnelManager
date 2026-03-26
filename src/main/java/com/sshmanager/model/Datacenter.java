package com.sshmanager.model;

import java.util.Objects;

public class Datacenter {
    private String name;
    private String address;
    private int port;

    public Datacenter() {}
    public Datacenter(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Datacenter that = (Datacenter) o;
        return Objects.equals(name, that.name);
    }
    @Override
    public int hashCode() { return Objects.hash(name); }
}