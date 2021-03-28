package com.nouria.flopBox.services.impl;

import com.nouria.flopBox.exceptions.FtpServerNotConnectedException;
import com.nouria.flopBox.models.FtpConnection;
import com.nouria.flopBox.models.FtpServer;
import com.nouria.flopBox.services.FtpClientService;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
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
            return Arrays.stream(ftp.listFiles(directoryPath)).map(FTPFile::getName).collect(Collectors.toList());
        }
        else throw new FtpServerNotConnectedException(ftpServer.getId());
    }

    @Override
    public List<FTPFile> listDirectories(FtpServer ftpServer, String path) throws IOException {

        // TODO : check if reply code = 550 => throw new Exception ( i think to create my own FileOrDirectoryNotFoundException exception )

        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            return Arrays.asList(ftp.listDirectories(path));
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
    }

    @Override
    public List<String> listAllFromDirectory(FtpServer ftpServer,String path) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            return Arrays.asList(ftp.listNames(path));
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
    }

    @Override
    public List<FTPFile> addNewDirectory(FtpServer ftpServer,String path) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            ftp.makeDirectory(path);
            return Arrays.asList(ftp.listFiles());
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
    }

    @Override
    public boolean deleteDirectory(FtpServer ftpServer,String path) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            return ftp.removeDirectory(path);
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");
    }

    @Override
    public boolean renameDirectory(FtpServer ftpServer, String path, String newName) throws IOException {
        FTPClient ftp = this.connexions.get(ftpServer.getId()).getFtpClient();
        if (ftp.isConnected()) {
            return ftp.rename(path, newName);
        }
        else throw new RuntimeException("Merci d'etablir une connexion au serveur ftp");

    }
}
