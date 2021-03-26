# FlopBox
Ftp Client REST Api that allows to save the list of your preferred FTP Servers, create connections, add and remove files from a distant ftp server.


## End points : 

### ENR:  Enregistrer un nouveau serveur FTP dans le FlopBox

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


### SUP: Supprimer un serveur FTP depuis notre FlopBox : 

type de la requête : DELETE
url de la requête  :  /servers/{id} 
content-type : application/json

- Retour attendu:

on doit avoir une phrase de confirmation de suppression du serveur :  Serveur FTP supprimé avec succès!


### REN: Modifier un serveur FTP dans le FlopBox par son id.

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
Retour attendu : un objet FtpServer contenant les informations du serveur modifier + id .


### Liste des serveurs FTP dans le FlopBOX

type de la requête : GET
url de la requête  :  /servers 
content-type : application/json

Retour attendu : une liste d’objets FtpServer contenant tous les serveurs ftp du FlopBox .


