package com.sshmanager.ui;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private JLabel statusLabel;
    private JProgressBar progressBar;

    public StatusPanel() {
        setLayout(new BorderLayout());
        statusLabel = new JLabel("Ready");
        progressBar = new JProgressBar();
        progressBar.setVisible(false);
        add(statusLabel, BorderLayout.CENTER);
        add(progressBar, BorderLayout.EAST);
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    public void showProgress(boolean show) {
        progressBar.setVisible(show);
    }

    public void setProgress(int value) {
        progressBar.setValue(value);
    }
}
