package com.sshmanager.model;

import java.util.Date;

public class Credential {
    private String user;
    private String password; // encrypted
    private String keyfile;
    private String privkeyPassphrase; // encrypted
    private Date lastModified;

    public Credential() {}
    public Credential(String user, String password, String keyfile, String privkeyPassphrase, Date lastModified) {
        this.user = user;
        this.password = password;
        this.keyfile = keyfile;
        this.privkeyPassphrase = privkeyPassphrase;
        this.lastModified = lastModified;
    }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getKeyfile() { return keyfile; }
    public void setKeyfile(String keyfile) { this.keyfile = keyfile; }
    public String getPrivkeyPassphrase() { return privkeyPassphrase; }
    public void setPrivkeyPassphrase(String privkeyPassphrase) { this.privkeyPassphrase = privkeyPassphrase; }
    public Date getLastModified() { return lastModified; }
    public void setLastModified(Date lastModified) { this.lastModified = lastModified; }
}