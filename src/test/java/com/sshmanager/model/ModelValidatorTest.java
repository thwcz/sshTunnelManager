package com.sshmanager.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelValidatorTest {
    @Test
    void testValidDatacenter() {
        List<Datacenter> dcs = new ArrayList<>();
        dcs.add(new Datacenter("dc1", "example.com", 2222));
        List<String> errors = new ArrayList<>();
        assertTrue(ModelValidator.validateDatacenters(dcs, errors));
        assertTrue(errors.isEmpty());
    }

    @Test
    void testInvalidDatacenterName() {
        List<Datacenter> dcs = new ArrayList<>();
        dcs.add(new Datacenter("bad name!", "example.com", 2222));
        List<String> errors = new ArrayList<>();
        assertFalse(ModelValidator.validateDatacenters(dcs, errors));
        assertFalse(errors.isEmpty());
    }

    @Test
    void testDuplicateDatacenterName() {
        List<Datacenter> dcs = new ArrayList<>();
        dcs.add(new Datacenter("dc1", "example.com", 2222));
        dcs.add(new Datacenter("dc1", "example2.com", 2223));
        List<String> errors = new ArrayList<>();
        assertFalse(ModelValidator.validateDatacenters(dcs, errors));
        assertTrue(errors.stream().anyMatch(e -> e.contains("Duplicate")));
    }

    @Test
    void testValidFolderAndConnection() {
        Connection.Host host1 = new Connection.Host();
        host1.setAddress("host1.example.com");
        host1.setHostPort(2222);
        host1.setMappedPort(10001);
        Connection conn = new Connection();
        conn.setName("conn1");
        conn.setFirstHost(host1);
        List<Connection> conns = new ArrayList<>();
        conns.add(conn);
        Folder folder = new Folder("folder1");
        folder.setConnections(conns);
        List<Folder> folders = new ArrayList<>();
        folders.add(folder);
        List<String> errors = new ArrayList<>();
        assertTrue(ModelValidator.validateFolders(folders, errors));
        assertTrue(errors.isEmpty());
    }

    @Test
    void testInvalidConnectionMappedPort() {
        Connection.Host host1 = new Connection.Host();
        host1.setAddress("host1.example.com");
        host1.setHostPort(2222);
        host1.setMappedPort(9999); // invalid
        Connection conn = new Connection();
        conn.setName("conn1");
        conn.setFirstHost(host1);
        List<Connection> conns = new ArrayList<>();
        conns.add(conn);
        Folder folder = new Folder("folder1");
        folder.setConnections(conns);
        List<Folder> folders = new ArrayList<>();
        folders.add(folder);
        List<String> errors = new ArrayList<>();
        assertFalse(ModelValidator.validateFolders(folders, errors));
        assertTrue(errors.stream().anyMatch(e -> e.contains("mappedPort")));
    }
}
