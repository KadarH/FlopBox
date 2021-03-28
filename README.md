
<div align="center">
  
<h2>J-FlopBox</h2>

![GitHub](https://img.shields.io/github/license/KadarH/FlopBox)
![](https://img.shields.io/badge/version-1.0-green)


</div>

Ftp Client REST Api based Spring boot that allows to save the list of your preferred FTP Servers on a temporary H2 database, open connections to them, save and remove files from a distant ftp server.

---
Pour lancer l'application : 

methode 1 : 

```bash
mvn clean install 
./mvnw spring-boot:run
```

methode 2 : 


```bash
mvn clean install 
mvn package
cd target/
java -jar flopBox-0.0.1-SNAPSHOT.jar
```

---
Les endpoints : 

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


#### Telecharger un fichier binaire depuis le serveur ftp

- type de la requête : GET
- url de la requête  :  /servers/{id}/download/binary
- content-type : application/json
- @RequestParam : {path}

-**Retour attendu** : une phrase de confirmation de téléchargement du fichier


#### Telecharger un fichier texte depuis le serveur ftp

- type de la requête : GET
- url de la requête  :  /servers/{id}/download/txt
- content-type : application/json
- @RequestParam : {path}

-**Retour attendu** : une phrase de confirmation de téléchargement du fichier


#### Telecharger tout le contenu d'un dossier depuis le serveur ftp

- type de la requête : GET
- url de la requête  :  /servers/{id}/download/all
- content-type : application/json
- @RequestParam String parentDir
- @RequestParam String currentDir
- @RequestParam String saveDir

#### Enregistrer un dossier dans le serveur ftp

- type de la requête : GET
- url de la requête  :  /servers/{id}/store/directory
- content-type : application/json
- @RequestParam String parentDir
- @RequestParam String currentDir
- @RequestParam String saveDir


-**Retour attendu** : une phrase de confirmation de l'enregistrement du dossier


#### Enregistrer un fichier dans le serveur ftp

- type de la requête : GET
- url de la requête  :  /servers/{id}/store/file
- content-type : application/json
- @RequestParam String localFilePath
- @RequestParam String remoteFilePath

-**Retour attendu** : une phrase de confirmation de l'enregistrement du fichier


#### Enregistrer tout le contenu d'un dossier dans le serveur ftp

- type de la requête : GET
- url de la requête  :  /servers/{id}/store/all
- content-type : application/json
- @RequestParam String localFilePath
- @RequestParam String remoteFilePath

-**Retour attendu** : une phrase de confirmation de l'enregistrement du fichier

---

L'application respecte les exigences suivantes : 

- Le code source de l'application s'execute avec maven  ( mvn clean install )
- Le code suit les principes de la programation orientée objet. 
- Le code est documenté et testé via curl et postman
- Le code compile correctement via maven.
- La plateforme supporte les connexions de plusieurs clients simultanées.
- La plateform supporte les mode Actif / Passif

---

### Instructions à suivre : 

1 - Lancer l'application FlopBox 

```bash
git clone https://github.com/KadarH/FlopBox
mvn clean install
./mvnw spring-boot:run
```

2 - Lister les serveurs FTP existants ( normalement la liste est vide au debut ):

```bash
curl http://localhost:8080/servers
```

3- Ajouter un nouveau serveur:

```bash
curl -X POST -d '{"server":"localhost", "port":2121, "user":"anonymous", "password":"anonymous"}' -H 'Content-Type: application/json'  http://localhost:8080/servers
```

4- Ajouter un deuxième serveur:

```bash
curl -X POST -d '{"server":"speedtest.tele2.net", "port":22, "user":"anonymous", "password":"anonymous"}' -H 'Content-Type: application/json'  http://localhost:8080/servers
```

5- Modifier un serveur ( par exemple : modifier le port  du serveur 2):

```bash
curl -X PUT -d  '{"server":"speedtest.tele2.net", "port":21, "user":"anonymous", "password":"anonymous"}' -H 'Content-Type: application/json' http://localhost:8080/servers/2
```

6- Supprimer un serveur:

```bash
curl -X DELETE http://localhost:8080/servers/2
```

7- Se Connecter à un serveur FTP  ( id =1 )

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/connect'
```

8- Passer en mode Active:

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/active'
```

9- Passer en mode passive:

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/passive'
```

10- Liste des fichier dans le / du serveur: 

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/files'
```

11- Liste des fichiers dans le un dossier donné ( exemple : dossier avec le nom test): 

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/content?path=/test'
```

12- Liste des dossiers dans le un dossier donné ( exemple : dossier avec le nom test): 

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/directories?path=/test'
```

13- Telecharger un fichier binaire depuis le serveur FTP:

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/download/binary?input=path-fichier&output=/Users/mac/Documents/path-fichier’
```

14- Telecharger un fichier texte depuis le serveur FTP:

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/download/txt?input=path-fichier&output=/Users/mac/Documents/path-fichier’
```

15- Telecharger tout un dossier depuis un serveur FTP:

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/download/all?parentDir=/test/&currentDir=&saveDir=/Users/Nouria/Documents/’
```

16- Enregistrer un fichier sur un serveur FTP:

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/store/file?localFilePath=/Users/Nouria/Documents/logoM.png&remoteFilePath=/test/logoM.png’
```

17- Enregistrer un dossier sur un serveur FTP:

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/store/all?parentDir=/test/&currentDir=&saveDir=/Users/Nouria/Documents/’
```

18- Enregistrer tout le contenu d'un dossier depuis un serveur FTP:

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/store/all?parentDir=/test/&currentDir=&saveDir=/Users/Nouria/Documents/’
```

19- Fermé la connexion d'un serveur FTP ( Normalement j'ai développé une fonction Batch qui permet la fermeture de la connexion dans 30 min si l'utilisateur n'a pas effectué une opération)

```bash
curl -H "Content-Type:application/json" -X GET  'http://localhost:8080/servers/1/close
```
