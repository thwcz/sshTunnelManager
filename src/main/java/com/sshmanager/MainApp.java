package com.sshmanager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;

public class MainApp {
    private static ServerSocket lockSocket; // Used for single instance enforcement
    private static final int LOCK_PORT = 45678;
    private static TrayIcon trayIcon;

    public static void main(String[] args) {
        if (!acquireLock()) {
            JOptionPane.showMessageDialog(null, "Another instance is already running.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        // Master password and credential flow
        boolean proceed = false;
        if (CredentialManager.firstStartRequired()) {
            proceed = CredentialManager.createMasterPasswordAndCredentials(null);
        } else {
            proceed = CredentialManager.validateMasterPassword(null);
        }
        if (!proceed) {
            System.exit(1);
        }
        SwingUtilities.invokeLater(MainApp::createAndShowGUI);
    }

    private static boolean acquireLock() {
        try {
            lockSocket = new ServerSocket(LOCK_PORT);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void createAndShowGUI() {
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "System tray not supported.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        PopupMenu popup = new PopupMenu();

        MenuItem showItem = new MenuItem("Show");
        MenuItem aboutItem = new MenuItem("About");
        MenuItem exitItem = new MenuItem("Exit");

        // Show main window
        showItem.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            showMainWindow();
        }));
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "SSH Tunnel Manager\nVersion 1.0.0", "About", JOptionPane.INFORMATION_MESSAGE));
        exitItem.addActionListener(e -> System.exit(0));

        popup.add(showItem);
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon = new TrayIcon(image, "SSH Tunnel Manager", popup);
        trayIcon.setImageAutoSize(true);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(null, "Unable to add tray icon.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        // Show main window on startup
        showMainWindow();
    }

    private static void showMainWindow() {
        // TODO: Load folders from config or create default
        java.util.List<com.sshmanager.model.Folder> folders = new java.util.ArrayList<>();
        com.sshmanager.ui.MainWindow mainWindow = new com.sshmanager.ui.MainWindow(folders);
        mainWindow.setVisible(true);
    }
}
