package com.nouria.flopBox.services;

import com.nouria.flopBox.models.FtpServer;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.List;

public interface FtpClientService {

    boolean connect(FtpServer ftpServer) throws IOException;
    boolean enterPassiveMode(FtpServer ftpServer) throws IOException;
    boolean enterActiveMode(FtpServer ftpServer) throws  IOException;
    boolean isConnected(Long id);
    List<String> listFiles(FtpServer ftpServer,String directoryPath)throws IOException;
    List<FTPFile> listDirectories(FtpServer ftpServer,String path)throws IOException;
    List<String> listAllFromDirectory(FtpServer ftpServer,String path)throws IOException;
    List<FTPFile> addNewDirectory(FtpServer ftpServer,String path) throws IOException;
    boolean deleteDirectory(FtpServer ftpServer,String path) throws IOException;
    boolean renameDirectory(FtpServer ftpServer,String path, String newName) throws IOException;

}
