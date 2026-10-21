# AtlasRH API — début du projet

Projet d'API REST RH réalisé avec Java 17, Spring Boot et PostgreSQL. Cette première base contient les entités du module Responsable RH : `Department`, `Employee` et `Contract`.

## Prérequis

- Java 17
- Maven
- PostgreSQL

Créez une base PostgreSQL nommée `atlasrh`, puis lancez l'application :

```bash
mvn spring-boot:run
```

La connexion utilise par défaut `postgres` / `postgres`. Vous pouvez la modifier avec les variables `DB_URL`, `DB_USERNAME` et `DB_PASSWORD`. Liquibase crée les tables au démarrage.

L'interface Swagger sera disponible sur `http://localhost:8080/swagger-ui.html` lorsque des contrôleurs seront ajoutés.

## Organisation du code

- `entity` : objets persistés en base. Ils ne devront pas être retournés directement par l'API.
- `controller` : recevra plus tard les requêtes HTTP.
- `service` : contiendra les règles métier.
- `repository` : accédera aux données avec Spring Data JPA.
- `dto` : objets échangés par l'API.
- `mapper` : conversion entre les DTO et les entités avec MapStruct.

Les entités ont un constructeur vide protégé pour JPA et des accesseurs explicites afin de garder le code lisible au début du projet. Les règles qui nécessitent de consulter plusieurs lignes (par exemple vérifier qu'un employé n'est pas son propre manager) seront ajoutées dans la couche service.
