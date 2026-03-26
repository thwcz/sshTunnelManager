package com.sshmanager.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.sshmanager.model.Connection;
import com.sshmanager.model.Folder;

public class MainWindow extends JFrame {
    private JList<Folder> folderList;
    private JList<Connection> connectionList;
    private JButton addConnectionBtn;
    private JButton editConnectionBtn;
    private JButton removeConnectionBtn;
    private JButton addFolderBtn;
    private JButton removeFolderBtn;
    private JButton manageCredentialsBtn;
    private JLabel statusLabel;

    public MainWindow(List<Folder> folders) {
        super("SSH Tunnel Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Folder list
        folderList = new JList<>(folders.toArray(new Folder[0]));
        folderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane folderScroll = new JScrollPane(folderList);
        folderScroll.setPreferredSize(new Dimension(200, 0));
        add(folderScroll, BorderLayout.WEST);

        // Connection list
        connectionList = new JList<>();
        JScrollPane connScroll = new JScrollPane(connectionList);
        add(connScroll, BorderLayout.CENTER);

        // Buttons panel
        JPanel btnPanel = new JPanel();
        addConnectionBtn = new JButton("Add Connection");
        editConnectionBtn = new JButton("Edit Connection");
        removeConnectionBtn = new JButton("Remove Connection");
        addFolderBtn = new JButton("Add Folder");
        removeFolderBtn = new JButton("Remove Folder");
        manageCredentialsBtn = new JButton("Manage Credentials");
        btnPanel.add(addConnectionBtn);
        btnPanel.add(editConnectionBtn);
        btnPanel.add(removeConnectionBtn);
        btnPanel.add(addFolderBtn);
        btnPanel.add(removeFolderBtn);
        btnPanel.add(manageCredentialsBtn);
        add(btnPanel, BorderLayout.NORTH);

        // Status bar
        statusLabel = new JLabel("Ready");
        add(statusLabel, BorderLayout.SOUTH);
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }
}
