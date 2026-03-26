package com.sshmanager.model;

import java.util.List;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.Set;

public class ModelValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\w.\\-/]+$");
    private static final Pattern FQDN_PATTERN = Pattern.compile("^(?=.{1,253}$)(?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.(?:[A-Za-z0-9-]{1,63}\\.)*[A-Za-z]{2,6}$|^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
    private static final int PORT_MIN = 1025;
    private static final int PORT_MAX = 65534;
    private static final int MAPPED_PORT_FIRSTHOST_MIN = 10000;
    private static final int MAPPED_PORT_FIRSTHOST_MAX = 19999;
    private static final int MAPPED_PORT_SECONDHOST_MIN = 20000;
    private static final int MAPPED_PORT_SECONDHOST_MAX = 29999;

    public static boolean validateDatacenters(List<Datacenter> datacenters, List<String> errors) {
        Set<String> names = new HashSet<>();
        for (Datacenter dc : datacenters) {
            if (!NAME_PATTERN.matcher(dc.getName()).matches()) {
                errors.add("Invalid datacenter name: " + dc.getName());
            }
            if (!FQDN_PATTERN.matcher(dc.getAddress()).matches()) {
                errors.add("Invalid datacenter address: " + dc.getAddress());
            }
            if (dc.getPort() < PORT_MIN || dc.getPort() > PORT_MAX) {
                errors.add("Datacenter port out of range: " + dc.getPort());
            }
            if (!names.add(dc.getName())) {
                errors.add("Duplicate datacenter name: " + dc.getName());
            }
        }
        return errors.isEmpty();
    }

    public static boolean validateFolders(List<Folder> folders, List<String> errors) {
        Set<String> folderNames = new HashSet<>();
        for (Folder folder : folders) {
            if (!NAME_PATTERN.matcher(folder.getName()).matches()) {
                errors.add("Invalid folder name: " + folder.getName());
            }
            if (!folderNames.add(folder.getName())) {
                errors.add("Duplicate folder name: " + folder.getName());
            }
            validateConnections(folder.getConnections(), errors);
        }
        return errors.isEmpty();
    }

    public static boolean validateConnections(List<Connection> connections, List<String> errors) {
        Set<String> connectionNames = new HashSet<>();
        Set<Integer> mappedPorts = new HashSet<>();
        for (Connection conn : connections) {
            if (!NAME_PATTERN.matcher(conn.getName()).matches()) {
                errors.add("Invalid connection name: " + conn.getName());
            }
            if (!connectionNames.add(conn.getName())) {
                errors.add("Duplicate connection name: " + conn.getName());
            }
            // Validate hosts
            if (conn.getFirstHost() != null) {
                validateHost(conn.getFirstHost(), true, mappedPorts, errors);
            }
            if (conn.getSecondHost() != null) {
                validateHost(conn.getSecondHost(), false, mappedPorts, errors);
            }
        }
        return errors.isEmpty();
    }

    private static void validateHost(Connection.Host host, boolean isFirst, Set<Integer> mappedPorts, List<String> errors) {
        if (!FQDN_PATTERN.matcher(host.getAddress()).matches()) {
            errors.add("Invalid host address: " + host.getAddress());
        }
        if (host.getHostPort() != 0 && (host.getHostPort() < PORT_MIN || host.getHostPort() > PORT_MAX)) {
            errors.add("Host port out of range: " + host.getHostPort());
        }
        int mappedPort = host.getMappedPort();
        if (isFirst) {
            if (mappedPort < MAPPED_PORT_FIRSTHOST_MIN || mappedPort > MAPPED_PORT_FIRSTHOST_MAX) {
                errors.add("FirstHost mappedPort out of range: " + mappedPort);
            }
        } else {
            if (mappedPort < MAPPED_PORT_SECONDHOST_MIN || mappedPort > MAPPED_PORT_SECONDHOST_MAX) {
                errors.add("SecondHost mappedPort out of range: " + mappedPort);
            }
        }
        if (!mappedPorts.add(mappedPort)) {
            errors.add("Duplicate mappedPort: " + mappedPort);
        }
    }
}