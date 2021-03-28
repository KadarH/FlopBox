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
    // --------------   Enter Passive mode
    // ------------------------------------------------------------------

    @GetMapping("/servers/{idServer}/passive")
    public String enterPassiveMode(@PathVariable Long idServer) throws IOException {
        FtpServer ftpServer = ftpServerService.get(idServer);
        if(this.ftpClientService.connect(ftpServer)) {
            this.ftpClientService.enterPassiveMode(ftpServer);
            return "connection établie !";
        }
        else return "Probleme de connexion : user or password incorrect";
    }

    // ------------------------------------------------------------------
    // --------------   Enter Active mode
    // ------------------------------------------------------------------

    @GetMapping("/servers/{idServer}/active")
    public String enterActiveMode(@PathVariable Long idServer) throws IOException {
        FtpServer ftpServer = ftpServerService.get(idServer);
        if(this.ftpClientService.connect(ftpServer)) {
            this.ftpClientService.enterActiveMode(ftpServer);
            return "connection établie !";
        }
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
    // ------   noms des fichiers et dossiers sur un path donné
    // ------------------------------------------------------------------

    @GetMapping("/servers/{id}/content")
    public List<String> listFilesFtpServer(@PathVariable Long id, @RequestParam String path) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        return this.ftpClientService.listAllFromDirectory(ftpServer, path);
    }

    // ------------------------------------------------------------------
    // ------   nom de tous les dossiers sur un path donné
    // ------------------------------------------------------------------

    @GetMapping("/servers/{id}/directories")
    public List<FTPFile> listDirectoriesFtpServer(@PathVariable Long id, @RequestParam String path) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        return this.ftpClientService.listDirectories(ftpServer, path);
    }

    // ------------------------------------------------------------------
    // ------   ajouter un nouveau dossier sur un path donné
    // ------------------------------------------------------------------

    @GetMapping("/servers/{id}/directories/new")
    public List<FTPFile> addNewDirectoryToFtpServer(@PathVariable Long id, @RequestParam String path) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        return this.ftpClientService.addNewDirectory(ftpServer, path);
    }

    // ------------------------------------------------------------------
    // ------   supprimer un dossier d'un path donné
    // ------------------------------------------------------------------

    @GetMapping("/servers/{id}/directories/delete")
    public String deleteDirectoryFromFtpServer(@PathVariable Long id, @RequestParam String path) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        if(this.ftpClientService.deleteDirectory(ftpServer, path)) return "Dossier supprimé";
        else return "Probleme lors de la suppression du document, verifier les droit d'acces au repertoire " + path;
    }

    // ------------------------------------------------------------------
    // ------   renommer un dossier d'un path donné par un un nouveau path
    // ------------------------------------------------------------------

    @GetMapping("/servers/{id}/directories/rename")
    public String updateDirectoryFromFtpServer(@PathVariable Long id, @RequestParam String path, @RequestParam String newName) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        if(this.ftpClientService.renameDirectory(ftpServer, path, newName)) return "Dossier renommé avec succés.";
        else return "Probleme lors du renommage du document, verifier les droits d'acces au repertoire " + path;
    }


    @GetMapping("/servers/{id}/download/binary")
    public String DownloadBinaryFileFtpServer(@PathVariable Long id, @RequestParam String input, @RequestParam String output) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        if(this.ftpClientService.downloadBinary(ftpServer, input, output)){
            return "Fichier binaire télechargé avec succès";
        }
        return "problème lors du télechargement du fichier binaire"+ input;
    }


    @GetMapping("/servers/{id}/download/txt")
    public String DownloadTxtFileFtpServer(@PathVariable Long id, @RequestParam String input, @RequestParam String output) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        if(this.ftpClientService.downloadText(ftpServer, input, output)){
            return "Fichier texte télechargé avec succès";
        }
        return "problème lors du télechargement du fichier "+ input;
    }

    @GetMapping("/servers/{id}/download/all")
    public String DownloadAllFilesFtpServer(@PathVariable Long id, @RequestParam String parentDir, @RequestParam String currentDir, @RequestParam String saveDir) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        if(this.ftpClientService.downloadAll(ftpServer,parentDir,currentDir,saveDir)){
            return "Contenu du dossier "+ parentDir + "" + currentDir +" télechargé avec succès";
        }
        return "problème lors du télechargement du contenu du dossier "+ parentDir + "/" + currentDir;
    }

    @GetMapping("/servers/{id}/store/file")
    public String StoreOneFileFtpServer(@PathVariable Long id, @RequestParam String localFilePath, @RequestParam String remoteFilePath) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        if(this.ftpClientService.storeFile(ftpServer,localFilePath,remoteFilePath)){
            return "Le fichier "+ localFilePath +" est enregistré sur le serveur FTP avec succès";
        }
        return "problème lors de l'enregistrement du fichier"+ localFilePath;
    }

    @GetMapping("/servers/{id}/store/directory")
    public String StoreOneDirectoryFtpServer(@PathVariable Long id, @RequestParam String parentDir, @RequestParam String currentDir, @RequestParam String saveDir) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        if(this.ftpClientService.uploadDirectory(ftpServer,parentDir,currentDir, saveDir)){
            return "Le dossier "+ parentDir + "" + currentDir +" est enregistré sur le serveur FTP avec succès";
        }
        return "problème lors de l'enregistrement du dossier"+ parentDir;
    }

    @GetMapping("/servers/{id}/store/all")
    public String StoreAllFilesFtpServer(@PathVariable Long id, @RequestParam String parentDir, @RequestParam String currentDir, @RequestParam String saveDir) throws IOException {
        FtpServer ftpServer = ftpServerService.get(id);
        if(this.ftpClientService.storeDirectoryContent(ftpServer,parentDir,currentDir,saveDir)){
            return "Contenu du dossier "+ parentDir + "" + currentDir +" enregistré sur le serveur FTP avec succès";
        }
        return "problème lors du télechargement du contenu du dossier "+ parentDir + "/" + currentDir;
    }

}
