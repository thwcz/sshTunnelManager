package com.sshmanager;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class CredentialManager {
    private static final String CREDENTIALS_FILE = "conf/credentials.json";
    private static final String SALT_FILE = "conf/salt.bin";
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    public static boolean firstStartRequired() {
        return !Files.exists(Path.of(CREDENTIALS_FILE));
    }

    public static boolean createMasterPasswordAndCredentials(Component parent) {
        String pw1 = promptPassword(parent, "Create Master Password:");
        if (pw1 == null) return false;
        String pw2 = promptPassword(parent, "Repeat Master Password:");
        if (pw2 == null || !pw1.equals(pw2)) {
            showError(parent, "Passwords do not match.");
            return false;
        }
        if (!isPasswordComplex(pw1)) {
            showError(parent, "Password does not meet complexity requirements.");
            return false;
        }
        // Prompt for credentials
        String user = JOptionPane.showInputDialog(parent, "Linux User Name:");
        if (user == null || user.isBlank()) return false;
        String password = promptPassword(parent, "Linux Password:");
        if (password == null) return false;
        // Save salt
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        try (FileOutputStream fos = new FileOutputStream(SALT_FILE)) {
            fos.write(salt);
        } catch (IOException e) {
            showError(parent, "Failed to save salt.");
            return false;
        }
        // Encrypt and save credentials
        try {
            String encUser = encrypt(user, pw1, salt);
            String encPassword = encrypt(password, pw1, salt);
            String json = "{\"user\":\"" + encUser + "\",\"password\":\"" + encPassword + "\"}";
            Files.writeString(Path.of(CREDENTIALS_FILE), json, StandardCharsets.UTF_8);
            return true;
        } catch (Exception e) {
            showError(parent, "Failed to save credentials.");
            return false;
        }
    }

    public static boolean validateMasterPassword(Component parent) {
        int retries = 3;
        byte[] salt;
        try {
            salt = Files.readAllBytes(Path.of(SALT_FILE));
        } catch (IOException e) {
            showError(parent, "Salt file missing.");
            return false;
        }
        while (retries-- > 0) {
            String pw = promptPassword(parent, "Enter Master Password:");
            if (pw == null) return false;
            try {
                String json = Files.readString(Path.of(CREDENTIALS_FILE), StandardCharsets.UTF_8);
                String encUser = json.split("\"user\":\"")[1].split("\"")[0];
                String encPassword = json.split("\"password\":\"")[1].split("\"")[0];
                decrypt(encUser, pw, salt); // try decrypt
                decrypt(encPassword, pw, salt);
                return true;
            } catch (Exception e) {
                showError(parent, "Invalid password. Attempts left: " + retries);
            }
        }
        showError(parent, "Maximum retries exceeded.");
        return false;
    }

    private static String promptPassword(Component parent, String message) {
        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(parent, pf, message, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (okCxl == JOptionPane.OK_OPTION) {
            return new String(pf.getPassword());
        }
        return null;
    }

    private static void showError(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean isPasswordComplex(String pw) {
        return pw.length() >= 8 && pw.matches(".*[A-Z].*") && pw.matches(".*[a-z].*") && pw.matches(".*[0-9].*") && pw.matches(".*[^A-Za-z0-9].*");
    }

    private static String encrypt(String data, String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeySpec secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] enc = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(enc);
    }

    private static String decrypt(String encData, String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeySpec secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        byte[] dec = cipher.doFinal(Base64.getDecoder().decode(encData));
        return new String(dec, StandardCharsets.UTF_8);
    }
}
