package com.sshmanager;

import com.jcraft.jsch.JSchException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SSHManagerTest {
    @Test
    void testConnectInvalidHost() {
        SSHManager manager = new SSHManager();
        Exception ex = assertThrows(JSchException.class, () ->
            manager.connect("invalid.host", 22, "user", "pass", null, null, 1000)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("unknownhost") || ex.getMessage().toLowerCase().contains("auth fail") || ex.getMessage().toLowerCase().contains("timeout"));
    }

    @Test
    void testDisconnectWithoutConnect() {
        SSHManager manager = new SSHManager();
        assertDoesNotThrow(manager::disconnect);
    }
}
