package com.nouria.flopBox.models;

import lombok.Data;
import org.apache.commons.net.ftp.FTPClient;

import java.time.LocalDateTime;

@Data
public class FtpConnection {

    FTPClient ftpClient;
    LocalDateTime dateOfLastConnection;
}
