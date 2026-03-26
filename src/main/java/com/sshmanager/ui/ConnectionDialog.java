package com.sshmanager.ui;

import javax.swing.*;
import java.awt.*;
import com.sshmanager.model.Connection;

public class ConnectionDialog extends JDialog {
    private JTextField nameField;
    private JTextField hostField;
    private JTextField portField;
    private JCheckBox favoriteBox;
    private JButton okButton;
    private JButton cancelButton;
    private boolean confirmed = false;

    public ConnectionDialog(Frame owner) {
        super(owner, "Add/Edit Connection", true);
        setLayout(new GridLayout(5, 2, 5, 5));
        setSize(400, 250);
        setLocationRelativeTo(owner);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Host:"));
        hostField = new JTextField();
        add(hostField);

        add(new JLabel("Port:"));
        portField = new JTextField();
        add(portField);

        add(new JLabel("Favorite:"));
        favoriteBox = new JCheckBox();
        add(favoriteBox);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        add(okButton);
        add(cancelButton);

        okButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        cancelButton.addActionListener(e -> setVisible(false));
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getConnectionName() {
        return nameField.getText();
    }

    public String getHost() {
        return hostField.getText();
    }

    public int getPort() {
        try {
            return Integer.parseInt(portField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean isFavorite() {
        return favoriteBox.isSelected();
    }
}
