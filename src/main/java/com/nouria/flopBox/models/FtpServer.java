package com.nouria.flopBox.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class FtpServer {

    @Id
    @GeneratedValue
    private Long id;

    private String server;
    private int port;
    private String user;
    private String password;

    public FtpServer(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public FtpServer() {
    }
}
