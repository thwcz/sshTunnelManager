package com.sshmanager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Folder {
    private String name;
    private boolean expanded = true;
    private List<Connection> connections = new ArrayList<>();

    public Folder() {}
    public Folder(String name) {
        this.name = name;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isExpanded() { return expanded; }
    public void setExpanded(boolean expanded) { this.expanded = expanded; }
    public List<Connection> getConnections() { return connections; }
    public void setConnections(List<Connection> connections) { this.connections = connections; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return Objects.equals(name, folder.name);
    }
    @Override
    public int hashCode() { return Objects.hash(name); }
}