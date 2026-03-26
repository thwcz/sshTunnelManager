package com.sshmanager;

import com.jcraft.jsch.*;
import java.util.Properties;

public class SSHManager {
    private Session session;
    private final JSch jsch = new JSch();

    public void connect(String host, int port, String user, String password, String keyfile, String passphrase, int connectTimeout) throws JSchException {
        if (keyfile != null && !keyfile.isBlank()) {
            if (passphrase != null && !passphrase.isBlank()) {
                jsch.addIdentity(keyfile, passphrase);
            } else {
                jsch.addIdentity(keyfile);
            }
        }
        session = jsch.getSession(user, host, port);
        if (password != null && !password.isBlank()) {
            session.setPassword(password);
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect(connectTimeout);
    }

    public boolean isConnected() {
        return session != null && session.isConnected();
    }

    public void disconnect() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
