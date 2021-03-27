package com.nouria.flopBox.exceptions;

public class FtpServerNotFoundException extends RuntimeException {

    public FtpServerNotFoundException(Long id) {
        super("Aucun serveur avec id = " + id);
    }
}
