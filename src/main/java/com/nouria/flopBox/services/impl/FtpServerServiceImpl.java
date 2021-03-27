package com.nouria.flopBox.services.impl;

import com.nouria.flopBox.exceptions.FtpServerNotFoundException;
import com.nouria.flopBox.models.FtpServer;
import com.nouria.flopBox.repo.FtpServerRepository;
import com.nouria.flopBox.services.FtpServerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FtpServerServiceImpl implements FtpServerService {

    private FtpServerRepository ftpServerRepository;

    public FtpServerServiceImpl(FtpServerRepository ftpServerRepository) {
        this.ftpServerRepository = ftpServerRepository;
    }

    @Override
    public List<FtpServer> list() {
        return ftpServerRepository.findAll();
    }

    @Override
    public FtpServer get(Long id) {
        return ftpServerRepository.findById(id).orElseThrow(() -> new FtpServerNotFoundException(id));
    }

    @Override
    public FtpServer add(FtpServer ftpServer) {
        return ftpServerRepository.save(ftpServer);
    }

    @Override
    public FtpServer update(FtpServer ftpServer) {

        FtpServer ftpServerOld = ftpServerRepository.findById(ftpServer.getId()).orElseThrow(() -> new FtpServerNotFoundException(ftpServer.getId()));

        if(!ftpServer.getServer().equals(ftpServerOld.getServer()))
            ftpServerOld.setServer(ftpServer.getServer());
        if(ftpServer.getPort() != ftpServerOld.getPort())
            ftpServerOld.setPort(ftpServer.getPort());
        if(!ftpServer.getUser().equals(ftpServerOld.getUser()))
            ftpServerOld.setUser(ftpServer.getUser());
        if(!ftpServer.getPassword().equals(ftpServerOld.getPassword()))
            ftpServerOld.setPassword(ftpServer.getPassword());

        return ftpServerRepository.save(ftpServerOld);
    }

    @Override
    public void delete(Long id) {
        ftpServerRepository.delete(ftpServerRepository.findById(id).orElseThrow(() -> new FtpServerNotFoundException(id)));
    }
}
