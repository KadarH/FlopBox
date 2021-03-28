
<div align="center">
  
<h2>J-FlopBox</h2>

![GitHub](https://img.shields.io/github/license/KadarH/FlopBox)
![](https://img.shields.io/badge/version-1.0-green)


</div>

Ftp Client REST Api based Spring boot that allows to save the list of your preferred FTP Servers on a temporary H2 database, open connections to them, save and remove files from a distant ftp server.

---

#### Enregistrer un nouveau serveur FTP

- type de la requête : POST
- url de la requête  :  /servers 
- content-type : application/json
- body :

```bash
{
	"server":"localhost",
	"port":2121,
	"user":"anonymous",
	"password":"anonymous"
}
```

Retour attendu : un objet FtpServer contenant les informations du serveur enregistrer + l’id generé par la base de données Mysql | H2-console


#### Supprimer un serveur FTP

- type de la requête : DELETE
- url de la requête  :  /servers/{id} 
- content-type : application/json
- **Retour attendu**: on doit avoir une phrase de confirmation de suppression du serveur :  Serveur FTP supprimé avec succès!


#### Modifier un serveur FTP par id

- type de la requête : PUT
- url de la requête  :  /servers/{id} 
- content-type : application/json

body :
```bash
{
	"server":"hostnameX",
	"port":2121,
	"user":"anonymous",
	"password":"anonymous"
}
```
**Retour attendu** : un objet FtpServer contenant les informations du serveur modifier + id .


#### Specifier le port d'un serveur FTP par id

- type de la requête : PUT
- url de la requête  :  /servers/{id} 
- content-type : application/json

body :
```bash
{
	"server":"hostnameX",
	"port":'nouveauPort' de type int,
	"user":"anonymous",
	"password":"anonymous"
}
```
**Retour attendu** : un objet FtpServer contenant les informations du serveur modifier + id .

#### Liste des serveurs FTP dans le FlopBOX

- type de la requête : GET
- url de la requête  :  /servers 
- content-type : application/json

-**Retour attendu** : une liste d’objets FtpServer contenant tous les serveurs ftp du FlopBox .


#### Se connecter à un serveur ftp par son id

- type de la requête : GET
- url de la requête  :  /servers/{id}/connect
- content-type : application/json

-**Retour attendu** : on doit avoir une phrase de confirmation de connexion au serveur : Connexion établie !


#### Entrer en mode Passive pour un serveur ftp 

- type de la requête : GET
- url de la requête  :  /servers/{id}/passive
- content-type : application/json

-**Retour attendu** : on doit avoir une phrase de confirmation de mode passif au serveur : In Passive mode !



#### Entrer en mode Active pour un serveur ftp 

- type de la requête : GET
- url de la requête  :  /servers/{id}/passive
- content-type : application/json

-**Retour attendu** : on doit avoir une phrase de confirmation de mode active au serveur : In Active mode !


#### Se déconnecter d'un serveur ftp par son id

- type de la requête : GET
- url de la requête  :  /servers/{id}/close
- content-type : application/json

-**Retour attendu** : on doit avoir une phrase de confirmation de connexion au serveur : Connexion fermée !


#### Afficher la liste des fichier sur le / d'un serveur ftp par son id

- type de la requête : GET
- url de la requête  :  /servers/{id}/files
- content-type : application/json

-**Retour attendu** : liste des noms des fichiers

#### Afficher la liste des dossier d'un repertoire donnée d'un serveur ftp par son id

- type de la requête : GET
- url de la requête  :  /servers/{id}/directories
- content-type : application/json
- @RequestParam : {pathRep}

-**Retour attendu** : liste des dossier dans le path pathRep

#### ajouer un dossier à un repertoire donnée d'un serveur ftp par son id

- type de la requête : GET
- url de la requête  :  /servers/{id}/directories/new
- content-type : application/json
- @RequestParam : {pathRep/nomRep}

-**Retour attendu** : liste des dossier dans le path pathRep


#### supprimer un dossier d'un repertoire donné d'un serveur ftp par son id

- type de la requête : GET
- url de la requête  :  /servers/{id}/directories/delete
- content-type : application/json
- @RequestParam : {pathRep/nomRep}

-**Retour attendu** : une phrase de confirmation de suppression du dossier


#### renommer un dossier d'un repertoire donné d'un serveur ftp par son id

- type de la requête : GET
- url de la requête  :  /servers/{id}/directories/rename
- content-type : application/json
- @RequestParam : {pathRep/nomRep} {pathRep/newName}

-**Retour attendu** : une phrase de confirmation de suppression du dossier

---

L'application respecte les exigences suivantes : 

- Le code source de l'application s'execute avec maven  ( mvn clean install )
- Le code suit les principes de la programation orientée objet. 
- Le code est documenté et testé via curl et postman
- Le code compile correctement via maven.
- La plateforme supporte les connexions de plusieurs clients simultanées.
- La plateform supporte les mode Actif / Passif