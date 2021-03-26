# FlopBox

![GitHub](https://img.shields.io/github/license/KadarH/FlopBox)
![](https://img.shields.io/badge/version-1.0-green)

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


#### Se déconnecter d'un serveur ftp par son id

- type de la requête : GET
- url de la requête  :  /servers/{id}/close
- content-type : application/json

-**Retour attendu** : on doit avoir une phrase de confirmation de connexion au serveur : Connexion fermée !


