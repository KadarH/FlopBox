package com.nouria.flopBox.controller;


import com.nouria.flopBox.models.FtpServer;
import com.nouria.flopBox.services.FtpClientService;
import com.nouria.flopBox.services.FtpServerService;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("")
public class FTPServerController {

    private FtpServerService ftpServerService;
    private FtpClientService ftpClientService;

    public FTPServerController(FtpServerService ftpServerService, FtpClientService ftpClientService) {
        this.ftpServerService = ftpServerService;
        this.ftpClientService = ftpClientService;
    }

    @GetMapping("/servers")
    public List<FtpServer> listFtpServers() {
        return this.ftpServerService.list();
    }

    @PostMapping("/servers")
    public FtpServer addNewFtpServer(@RequestBody FtpServer ftpServer) {

        this.ftpServerService.add(ftpServer);
        return this.ftpServerService.add(ftpServer);
    }

    @PutMapping("/servers/{id}")
    public FtpServer updateFtpServer(@RequestBody FtpServer ftpServer, @PathVariable Long id) {

        ftpServer.setId(id);
        this.ftpServerService.update(ftpServer);
        return this.ftpServerService.add(ftpServer);
    }

    @DeleteMapping("/servers/{id}")
    public String deleteFtpServer(@PathVariable Long id) {

        this.ftpServerService.delete(id);
        return "Serveur FTP supprimé avec succès !";
    }

    @GetMapping("/servers/{idServer}/connect")
    public String connectFtpConnexion(@PathVariable Long idServer) throws IOException {
        FtpServer ftpServer = ftpServerService.get(idServer);
        if(this.ftpClientService.connect(ftpServer))
            return "connexion opened";
        else return "user or password incorrect";
    }

    @GetMapping("/servers/{id}/close")
    public String closeFtpServer(@PathVariable Long id) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        this.ftpClientService.close(ftpServer);
        return "Connexion fermé!";
    }

    @GetMapping("/servers/{id}/files")
    public List<FTPFile> listFilesFtpServer(@PathVariable Long id) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        return this.ftpClientService.listFiles(ftpServer);
    }


}
