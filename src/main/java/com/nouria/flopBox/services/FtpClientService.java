package com.nouria.flopBox.services;

import com.nouria.flopBox.models.FtpServer;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
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

    boolean downloadBinary(FtpServer ftpServer, String pathInServer, String pathToSave) throws IOException;
    boolean downloadText(FtpServer ftpServer, String pathInServer, String pathToSave) throws IOException;
    boolean downloadAll(FtpServer ftpServer,String parentDir,String currentDir, String saveDir) throws IOException;

    boolean storeFile(FtpServer ftpServer, String localFilePath, String remoteFilePath) throws IOException;
    boolean storeDirectoryContent(FtpServer ftpServer, String remoteDirPath, String localParentDir, String remoteParentDir) throws IOException;
    boolean uploadDirectory(FtpServer ftpServer, String remoteDirPath, String localParentDir, String remoteParentDir) throws IOException ;
}
