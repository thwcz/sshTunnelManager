package com.sshmanager.ui;

import javax.swing.*;
import java.awt.*;

public class FolderDialog extends JDialog {
    private JTextField nameField;
    private JButton okButton;
    private JButton cancelButton;
    private boolean confirmed = false;

    public FolderDialog(Frame owner) {
        super(owner, "Add/Edit Folder", true);
        setLayout(new GridLayout(2, 2, 5, 5));
        setSize(300, 120);
        setLocationRelativeTo(owner);

        add(new JLabel("Folder Name:"));
        nameField = new JTextField();
        add(nameField);

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

    public String getFolderName() {
        return nameField.getText();
    }
}
