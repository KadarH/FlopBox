package com.nouria.flopBox.services;

import com.nouria.flopBox.models.FtpServer;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.List;

public interface FtpClientService {

    boolean connect(FtpServer ftpServer) throws IOException ;
    void close(FtpServer ftpServer) throws IOException;
    List<FTPFile> listFiles(FtpServer ftpServer)throws IOException;
}
