package com.nouria.flopBox.services.impl;

import com.nouria.flopBox.exceptions.FtpServerNotConnectedException;
import com.nouria.flopBox.models.FtpConnection;
import com.nouria.flopBox.models.FtpServer;
import com.nouria.flopBox.services.FtpClientService;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FtpClientServiceImpl implements FtpClientService {

    Map<Long, FtpConnection> connexions = new HashMap<>();

    @Override
    public boolean connect(FtpServer ftpServer) throws IOException {
        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            ftp.connect(ftpServer.getServer(), ftpServer.getPort());
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new IOException("Exception in connecting to FTP Server");
            }
            if(ftp.login(ftpServer.getUser(), ftpServer.getPassword())) {
                FtpConnection ftpConnection = new FtpConnection();
                ftpConnection.setFtpClient(ftp);
                ftpConnection.setDateOfLastConnection(LocalDateTime.now());
                connexions.put(ftpServer.getId(),ftpConnection);
                return true;
            } else throw new RuntimeException("Problème de la connexion au serveur ftp, user ou mot de passe incorrect");
    }

    @Override
    public boolean enterPassiveMode(FtpServer ftpServer) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalPassiveMode();
            return true;
        }
        return false;
    }

    @Override
    public boolean enterActiveMode(FtpServer ftpServer) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalActiveMode();
            return true;
        }
        return false;
    }

    @Scheduled(fixedDelay = 2000000)
    private void closeConnexions(){
        for (Map.Entry<Long,FtpConnection> entry : this.connexions.entrySet())
            if(entry.getValue().getDateOfLastConnection().plusMinutes(30).isAfter(LocalDateTime.now()))
                this.connexions.remove(entry.getKey());
    }

    @Override
    public boolean isConnected(Long id) {
        return this.connexions.containsKey(id);
    }

    @Override
    public List<String> listFiles(FtpServer ftpServer,String directoryPath) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalPassiveMode();
            return Arrays.stream(ftp.listFiles(directoryPath)).map(FTPFile::getName).collect(Collectors.toList());
        }
        else throw new FtpServerNotConnectedException(ftpServer.getId());
    }

    @Override
    public List<FTPFile> listDirectories(FtpServer ftpServer, String path) throws IOException {

        // TODO : check if reply code = 550 => throw new Exception ( i think to create my own FileOrDirectoryNotFoundException exception )

        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalPassiveMode();
            return Arrays.asList(ftp.listDirectories(path));
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
    }

    @Override
    public List<String> listAllFromDirectory(FtpServer ftpServer,String path) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalPassiveMode();
            return Arrays.asList(ftp.listNames(path));
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
    }

    @Override
    public List<FTPFile> addNewDirectory(FtpServer ftpServer,String path) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalPassiveMode();
            ftp.makeDirectory(path);
            return Arrays.asList(ftp.listFiles());
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
    }

    @Override
    public boolean deleteDirectory(FtpServer ftpServer,String path) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalPassiveMode();
            return ftp.removeDirectory(path);
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
    }

    @Override
    public boolean renameDirectory(FtpServer ftpServer, String path, String newName) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalPassiveMode();
            return ftp.rename(path, newName);
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");

    }

    @Override
    public boolean downloadBinary(FtpServer ftpServer, String pathInServer, String pathToSave) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: using retrieveFile(String, OutputStream)
            File downloadFile1 = new File(pathToSave);

            File parentDir = downloadFile1.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdir();
            }

            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftp.retrieveFile(pathInServer, outputStream1);
            outputStream1.close();

            if (success) {
                System.out.println("File #1 has been downloaded successfully.");
                return true;
            }else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");

        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
    }


    @Override
    public boolean downloadText(FtpServer ftpServer, String pathInServer, String pathToSave) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.ASCII_FILE_TYPE);

            // APPROACH #1: using retrieveFile(String, OutputStream)
            File downloadFile1 = new File(pathToSave);

            File parentDir = downloadFile1.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdir();
            }

            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftp.retrieveFile(pathInServer, outputStream1);
            outputStream1.close();

            if (success) {
                System.out.println("File #1 text has been downloaded successfully.");
                return true;
            } else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
    }

    @Override
    public boolean downloadAll(FtpServer ftpServer,String parentDir,String currentDir, String saveDir) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.ASCII_FILE_TYPE);

            String dirToList = parentDir;
            if (!currentDir.equals("")) {
                dirToList += "/" + currentDir;
            }

            FTPFile[] subFiles = ftp.listFiles(dirToList);
            if (subFiles != null && subFiles.length > 0) {
                for (FTPFile aFile : subFiles) {
                    String currentFileName = aFile.getName();
                    if (currentFileName.equals(".") || currentFileName.equals("..")) {
                        // skip parent directory and the directory itself
                        continue;
                    }
                    String filePath = parentDir + "/" + currentDir + "/"
                            + currentFileName;
                    if (currentDir.equals("")) {
                        filePath = parentDir + "/" + currentFileName;
                    }

                    String newDirPath = saveDir + parentDir + File.separator
                            + currentDir + File.separator + currentFileName;
                    if (currentDir.equals("")) {
                        newDirPath = saveDir + parentDir + File.separator
                                + currentFileName;
                    }
                    if (aFile.isDirectory()) {
                        // create the directory in saveDir
                        File newDir = new File(newDirPath);
                        boolean created = newDir.mkdirs();
                        if (created) {
                            System.out.println("CREATED the directory: " + newDirPath);
                        } else {
                            System.out.println("COULD NOT create the directory: " + newDirPath);
                        }

                        // download the sub directory
                        downloadAll(ftpServer, dirToList, currentFileName,saveDir);
                    } else {
                        // download the file
                        boolean success = downloadBinary(ftpServer, filePath, newDirPath);

                        if (success) {
                            System.out.println("DOWNLOADED the file: " + filePath);
                        } else {
                            System.out.println("COULD NOT download the file: " + filePath);
                        }
                    }
                }
            }
            return true;
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
    }

    @Override
    public boolean storeFile(FtpServer ftpServer, String localFilePath, String remoteFilePath) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            File localFile = new File(localFilePath);
            InputStream inputStream = new FileInputStream(localFile);
            return ftp.storeFile(remoteFilePath, inputStream);
        }
        return false;
    }

    @Override
    public boolean storeDirectoryContent(FtpServer ftpServer,String remoteDirPath, String localParentDir, String remoteParentDir) throws IOException {

        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            System.out.println("LISTING directory: " + localParentDir);

            File localDir = new File(localParentDir);
            File[] subFiles = localDir.listFiles();
            if (subFiles != null && subFiles.length > 0) {
                for (File item : subFiles) {
                    String remoteFilePath = remoteDirPath + "/" + remoteParentDir
                            + "/" + item.getName();
                    if (remoteParentDir.equals("")) {
                        remoteFilePath = remoteDirPath + "/" + item.getName();
                    }

                    if (item.isFile()) {
                        // upload the file
                        String localFilePath = item.getAbsolutePath();
                        System.out.println("About to upload the file: " + localFilePath);
                        boolean uploaded = storeFile(ftpServer, localFilePath, remoteFilePath);
                        if (uploaded) {
                            System.out.println("UPLOADED a file to: " + remoteFilePath);
                        } else {
                            System.out.println("COULD NOT upload the file: " + localFilePath);
                        }
                    } else {
                        // create directory on the server
                        boolean created = ftp.makeDirectory(remoteFilePath);
                        if (created) {
                            System.out.println("CREATED the directory: " + remoteFilePath);
                        } else {
                            System.out.println("COULD NOT create the directory: " + remoteFilePath);
                        }

                        // upload the sub directory
                        String parent = remoteParentDir + "/" + item.getName();
                        if (remoteParentDir.equals("")) {
                            parent = item.getName();
                        }

                        localParentDir = item.getAbsolutePath();
                        uploadDirectory(ftpServer, remoteDirPath, localParentDir, parent);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean uploadDirectory(FtpServer ftpServer, String remoteDirPath, String localParentDir, String remoteParentDir) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();

        System.out.println("LISTING directory: " + localParentDir);

        File localDir = new File(localParentDir);
        File[] subFiles = localDir.listFiles();
        if (subFiles != null && subFiles.length > 0) {
            for (File item : subFiles) {
                String remoteFilePath = remoteDirPath + "/" + remoteParentDir + "/" + item.getName();
                if (remoteParentDir.equals("")) {
                    remoteFilePath = remoteDirPath + "/" + item.getName();
                }
                if (item.isFile()) {
                    // upload the file
                    String localFilePath = item.getAbsolutePath();
                    System.out.println("About to upload the file: " + localFilePath);
                    boolean uploaded = storeFile(ftpServer,localFilePath, remoteFilePath);
                    if (uploaded) {
                        System.out.println("UPLOADED a file to: " + remoteFilePath);
                    } else {
                        System.out.println("COULD NOT upload the file: " + localFilePath);
                    }
                } else {
                    // create directory on the server
                    boolean created = ftp.makeDirectory(remoteFilePath);
                    if (created) {
                        System.out.println("CREATED the directory: " + remoteFilePath);
                    } else {
                        System.out.println("COULD NOT create the directory: " + remoteFilePath);
                    }

                    // upload the sub directory
                    String parent = remoteParentDir + "/" + item.getName();
                    if (remoteParentDir.equals("")) { parent = item.getName(); }
                    localParentDir = item.getAbsolutePath();
                    uploadDirectory(ftpServer, remoteDirPath, localParentDir, parent);
                }
                return true;
            }
        }
        return false;
    }
}
