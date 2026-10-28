# AtlasRH API — début du projet

Projet d'API REST RH réalisé avec Java 17, Spring Boot et PostgreSQL. Cette première base contient les entités du module Responsable RH : `Department`, `Employee` et `Contract`.

## Prérequis

- Java 17
- Maven
- PostgreSQL

Créez une base PostgreSQL nommée `atlasrh`, puis lancez l'application :

```bash
$env:RH_ADMIN_PASSWORD = "choose-a-strong-password"
mvn spring-boot:run
```

La connexion utilise par défaut `postgres` / `postgres`. Vous pouvez la modifier avec les variables `DB_URL`, `DB_USERNAME` et `DB_PASSWORD`. Liquibase crée les tables au démarrage.

## Lancer avec Docker

Avec Docker Desktop démarré, à la racine du projet :

```bash
docker compose up --build
```

Compose démarre PostgreSQL, attend qu'il soit prêt, puis démarre l'API. Liquibase applique le changelog au démarrage de l'API. L'API est accessible sur `http://localhost:8080`. Pour arrêter les conteneurs, utilisez `docker compose down` ; les données PostgreSQL restent dans le volume `atlasrh-postgres-data`.

L'interface Swagger est disponible sur `http://localhost:8080/swagger-ui.html`. Le contrat OpenAPI est fourni dans `openapi.yaml`.

## Organisation du code

- `entity` : objets persistés en base. Ils ne devront pas être retournés directement par l'API.
- `controller` : recevra plus tard les requêtes HTTP.
- `service` : contiendra les règles métier.
- `repository` : accédera aux données avec Spring Data JPA.
- `dto` : objets échangés par l'API.
- `mapper` : conversion entre les DTO et les entités avec MapStruct.

Les entités ont un constructeur vide protégé pour JPA et des accesseurs explicites. Les règles métier sont appliquées dans la couche service.

## Fonctionnalités RH

- Départements : création, consultation, modification et suppression lorsqu'aucun employé n'y est rattaché.
- Employés : création, consultation, modification et suppression sans dossiers liés, avec département et responsable hiérarchique.
- Contrats : gestion des contrats CDI, CDD et stage, avec filtrage par employé.
- Congés : dépôt, consultation, approbation ou refus des demandes. Les demandes approuvées qui se chevauchent sont refusées.
- Pointage : entrée, sortie et résumés des heures enregistrées.
- Recrutement : annonces de poste et suivi des candidatures.
- Formation : catalogue, inscriptions et suivi des formations terminées.
- Paie : création et consultation des fiches mensuelles. Le net est calculé comme salaire brut + ajouts - déductions.

Les endpoints REST sont préfixés par `/api` et protégés par HTTP Basic. Le compte RH utilise `RH_ADMIN_USERNAME` (par défaut `rh-admin`) et le mot de passe obligatoire `RH_ADMIN_PASSWORD`. Les pages Swagger/OpenAPI restent publiques. Les champs `departmentId`, `managerId` et `employeeId` référencent les ressources existantes.
