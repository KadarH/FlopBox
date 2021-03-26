package com.nouria.flopBox.services;

import com.nouria.flopBox.models.FtpServer;

import java.util.List;

public interface FtpServerService {

    List<FtpServer> list();
    FtpServer get(Long id);
    FtpServer add(FtpServer ftpServer);
    FtpServer update(FtpServer ftpServer);
    void delete(Long id);

}
