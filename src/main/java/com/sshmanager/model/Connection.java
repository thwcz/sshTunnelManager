package com.sshmanager.model;

import java.util.Objects;

public class Connection {
    private String name;
    private String connectionType;
    private String datacenter;
    private Host firstHost;
    private Host secondHost;
    private boolean isFavorite = false;
    private boolean passwordOverride = false;

    public Connection() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getConnectionType() { return connectionType; }
    public void setConnectionType(String connectionType) { this.connectionType = connectionType; }
    public String getDatacenter() { return datacenter; }
    public void setDatacenter(String datacenter) { this.datacenter = datacenter; }
    public Host getFirstHost() { return firstHost; }
    public void setFirstHost(Host firstHost) { this.firstHost = firstHost; }
    public Host getSecondHost() { return secondHost; }
    public void setSecondHost(Host secondHost) { this.secondHost = secondHost; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public boolean isPasswordOverride() { return passwordOverride; }
    public void setPasswordOverride(boolean passwordOverride) { this.passwordOverride = passwordOverride; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(name, that.name);
    }
    @Override
    public int hashCode() { return Objects.hash(name); }

    public static class Host {
        private String address;
        private int hostPort;
        private int mappedPort;
        private String password; // encrypted, optional
        public Host() {}
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public int getHostPort() { return hostPort; }
        public void setHostPort(int hostPort) { this.hostPort = hostPort; }
        public int getMappedPort() { return mappedPort; }
        public void setMappedPort(int mappedPort) { this.mappedPort = mappedPort; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}