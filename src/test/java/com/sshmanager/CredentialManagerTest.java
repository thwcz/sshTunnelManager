package com.sshmanager;

import org.junit.jupiter.api.*;
import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CredentialManagerTest {
    @BeforeEach
    void cleanUp() throws Exception {
        Files.deleteIfExists(Path.of("conf/credentials.json"));
        Files.deleteIfExists(Path.of("conf/salt.bin"));
    }

    @Test
    void testCreateMasterPasswordAndCredentials() {
        // Simulate user input via dialog mocks (not implemented here)
        // For real tests, use a UI testing framework or refactor for testability
        assertTrue(CredentialManager.isPasswordComplex("Abcdef1!"));
        assertFalse(CredentialManager.isPasswordComplex("abc"));
    }

    @Test
    void testFirstStartRequired() throws Exception {
        Files.deleteIfExists(Path.of("conf/credentials.json"));
        assertTrue(CredentialManager.firstStartRequired());
        Files.createFile(Path.of("conf/credentials.json"));
        assertFalse(CredentialManager.firstStartRequired());
    }
}
