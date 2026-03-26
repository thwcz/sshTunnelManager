package com.sshmanager.ui;

import javax.swing.*;
import java.awt.*;

public class CredentialDialog extends JDialog {
    private JTextField userField;
    private JPasswordField passwordField;
    private JTextField keyfileField;
    private JPasswordField passphraseField;
    private JButton okButton;
    private JButton cancelButton;
    private boolean confirmed = false;

    public CredentialDialog(Frame owner) {
        super(owner, "Manage Credentials", true);
        setLayout(new GridLayout(5, 2, 5, 5));
        setSize(400, 220);
        setLocationRelativeTo(owner);

        add(new JLabel("User:"));
        userField = new JTextField();
        add(userField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Keyfile (optional):"));
        keyfileField = new JTextField();
        add(keyfileField);

        add(new JLabel("Key Passphrase (optional):"));
        passphraseField = new JPasswordField();
        add(passphraseField);

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

    public String getUser() {
        return userField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getKeyfile() {
        return keyfileField.getText();
    }

    public String getPassphrase() {
        return new String(passphraseField.getPassword());
    }
}
