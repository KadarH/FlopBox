package com.nouria.flopBox.services.impl;

import com.nouria.flopBox.models.FtpServer;
import com.nouria.flopBox.services.FtpClientService;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class FtpClientServiceImpl implements FtpClientService {

    private FTPClient ftp = new FTPClient();

    @Override
    public boolean connect(FtpServer ftpServer) throws IOException {
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            ftp.connect(ftpServer.getServer(), ftpServer.getPort());
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new IOException("Exception in connecting to FTP Server");
            }
            return ftp.login(ftpServer.getUser(), ftpServer.getPassword());
    }

    @Override
    public void close(FtpServer ftpServer) throws IOException {
        ftp.disconnect();
    }

    @Override
    public List<FTPFile> listFiles(FtpServer ftpServer) throws IOException {

        return Arrays.asList(ftp.listFiles());
    }
}
