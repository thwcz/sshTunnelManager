package com.sshmanager;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

class MainAppTest {
    @Test
    void testSingleInstanceLock() {
        try (ServerSocket socket = new ServerSocket(45678)) {
            assertThrows(IOException.class, () -> new ServerSocket(45678));
        } catch (IOException e) {
            fail("Port 45678 should be available for the first instance");
        }
    }
}
