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

    // ------------------------------------------------------------------
    // --------------   Gestion de mes serveur FTP sur le FlopBOX    ----
    // ------------------------------------------------------------------

    @PostMapping("/servers")
    public FtpServer addNewFtpServer(@RequestBody FtpServer ftpServer) {

        return this.ftpServerService.add(ftpServer);
    }

    @GetMapping("/servers")
    public List<FtpServer> listFtpServers() {
        return this.ftpServerService.list();
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

    // ------------------------------------------------------------------
    // --------------   Etablir une connexion FTP à un serveur donné
    // ------------------------------------------------------------------

    @GetMapping("/servers/{idServer}/connect")
    public String connectFtpConnexion(@PathVariable Long idServer) throws IOException {
        FtpServer ftpServer = ftpServerService.get(idServer);
        if(this.ftpClientService.connect(ftpServer))
            return "connection établie !";
        else return "Probleme de connexion : user or password incorrect";
    }

    // ------------------------------------------------------------------
    // --------------   liste des fichiers sur le path /
    // ------------------------------------------------------------------

    @GetMapping("/servers/{id}/files")
    public List<String> listFilesFtpServer(@PathVariable Long id) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        return this.ftpClientService.listFiles(ftpServer, "/");
    }

    // ------------------------------------------------------------------
    // ------   nom de tous les fichiers et dossiers sur un path donné
    // ------------------------------------------------------------------

    @GetMapping("/servers/{id}/content")
    public List<String> listFilesFtpServer(@PathVariable Long id, @RequestBody String path) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        return this.ftpClientService.listAllFromDirectory(ftpServer, path);
    }

    // ------------------------------------------------------------------
    // ------   nom de tous les dossiers sur un path donné
    // ------------------------------------------------------------------

    @GetMapping("/servers/{id}/directories")
    public List<FTPFile> listDirectoriesFtpServer(@PathVariable Long id, @RequestBody String path) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        return this.ftpClientService.listDirectories(ftpServer, path);
    }

    // ------------------------------------------------------------------
    // ------   ajouter un nouveau dossier sur un path donné
    // ------------------------------------------------------------------

    @GetMapping("/servers/{id}/directories/new")
    public List<FTPFile> addNewDirectoryToFtpServer(@PathVariable Long id, @RequestBody String path) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        return this.ftpClientService.addNewDirectory(ftpServer, path);
    }

}
