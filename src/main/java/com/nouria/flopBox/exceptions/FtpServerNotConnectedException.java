package com.nouria.flopBox.exceptions;

public class FtpServerNotConnectedException extends RuntimeException {

    public FtpServerNotConnectedException(Long id) {
        super("Verifier la Connection au serveur FTP [ id = " + id + " ]");
    }
}
