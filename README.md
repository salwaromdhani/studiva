# STUDIVA : réseau social étudiant

**STUDIVA** est une API REST de réseau social destinée aux étudiants. Un étudiant peut créer son profil, publier, commenter, aimer des publications, suivre d'autres étudiants et utiliser des hashtags.

Projet réalisé dans le cadre du module **Développement Web avec Spring Boot et Angular**.
## Contexte

Les étudiants échangent chaque jour sur leurs cours, leurs examens, les stages et les événements universitaires. Ces échanges sont aujourd'hui dispersés sur plusieurs réseaux sociaux généralistes, mélangés aux contenus personnels.

**Problématique :** comment offrir aux étudiants un espace numérique dédié, simple et interactif, pour échanger et partager autour de leur vie universitaire ?

**Solution :** STUDIVA, une API REST développée avec Spring Boot, qui permet de gérer les étudiants, les publications, les commentaires, les likes, les abonnements et les hashtags.
---

## Technologies

| Technologie | Utilisation |
|---|---|
| Java 17 | langage |
| Spring Boot 4 | framework principal |
| Spring Web | API REST |
| Spring Data JPA / Hibernate | persistance des données |
| Bean Validation | validation des données (`@NotBlank`, `@Email`, `@Size`…) |
| MySQL (XAMPP) | base de données |
| Maven | gestion des dépendances |
| Postman | tests des API |

---

## Architecture du projet

```
src/main/java/com/example/studiva
├── model         → les 6 entités JPA
├── repository    → accès à la base (Spring Data JPA) et requêtes personnalisées
├── service       → logique métier et règles de gestion
├── controller    → API REST (endpoints)
├── exception     → exceptions personnalisées et GlobalExceptionHandler
└── StudivaApplication.java
src/main/resources
└── application.properties
```

---

## Les entités et leurs relations

| Entité | Attributs principaux |
|---|---|
| **Etudiant** | id, nom, prenom, email (unique), motDePasse, dateInscription |
| **Publication** | id, contenu, imageUrl, datePublication |
| **Commentaire** | id, contenu, dateCommentaire |
| **Like** | id, dateLike |
| **Abonnement** | id, dateAbonnement |
| **Hashtag** | id, nom (unique) |

| Relation | Type |
|---|---|
| Etudiant → Publication | OneToMany / ManyToOne |
| Etudiant → Commentaire | OneToMany / ManyToOne |
| Publication → Commentaire | OneToMany / ManyToOne |
| Etudiant → Like | OneToMany / ManyToOne |
| Publication → Like | OneToMany / ManyToOne |
| Etudiant → Abonnement | deux ManyToOne : `abonne` (celui qui suit) et `suivi` (celui qui est suivi) |
| Publication ↔ Hashtag | ManyToMany (table `publication_hashtags`) |

---

## Installation et lancement

1. **Démarrer MySQL** dans XAMPP (bouton *Start*).
2. **Cloner le projet** :
   ```bash
   git clone https://github.com/salwaromdhani/studiva.git
   ```
3. **Importer** dans Spring Tools for Eclipse : *File → Import → Maven → Existing Maven Projects*.
4. Vérifier `src/main/resources/application.properties` :
   ```properties
   server.port=8081
   spring.datasource.url=jdbc:mysql://localhost:3306/studiva?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=update
   ```
5. **Lancer** : clic droit sur le projet → *Run As → Spring Boot App*.
6. La base `studiva` et ses tables sont **créées automatiquement**.
7. Tester : `http://localhost:8081/api/etudiants`

---

## Endpoints de l'API

Adresse de base : `http://localhost:8081`

### Étudiants `/api/etudiants`
| Méthode | URL | Description |
|---|---|---|
| GET | `/api/etudiants` | liste des étudiants |
| GET | `/api/etudiants/{id}` | un étudiant |
| POST | `/api/etudiants` | créer un étudiant |
| PUT | `/api/etudiants/{id}` | modifier un étudiant |
| DELETE | `/api/etudiants/{id}` | supprimer un étudiant |

### Publications `/api/publications`
| Méthode | URL | Description |
|---|---|---|
| GET | `/api/publications` | liste des publications |
| GET | `/api/publications/{id}` | une publication |
| POST | `/api/publications?auteurId=1` | publier |
| PUT | `/api/publications/{id}` | modifier |
| DELETE | `/api/publications/{id}` | supprimer |
| GET | `/api/publications/etudiant/{id}` | publications d'un étudiant (**requête personnalisée 1**) |
| GET | `/api/publications/fil-actualite` | fil d'actualité trié par date (**requête personnalisée 2**) |
| POST | `/api/publications/{id}/hashtags/{hashtagId}` | associer un hashtag |

### Commentaires `/api/commentaires`
| Méthode | URL | Description |
|---|---|---|
| GET | `/api/commentaires` | liste des commentaires |
| GET | `/api/commentaires/{id}` | un commentaire |
| POST | `/api/commentaires?auteurId=1&publicationId=1` | commenter |
| PUT | `/api/commentaires/{id}` | modifier |
| DELETE | `/api/commentaires/{id}` | supprimer |
| GET | `/api/commentaires/publication/{id}` | commentaires d'une publication (**requête personnalisée 3**) |

### Likes `/api/likes`
| Méthode | URL | Description |
|---|---|---|
| GET | `/api/likes` | liste des likes |
| GET | `/api/likes/{id}` | un like |
| POST | `/api/likes?etudiantId=1&publicationId=1` | aimer une publication |
| DELETE | `/api/likes/{id}` | retirer un like |
| GET | `/api/likes/publication/{id}/count` | nombre de likes d'une publication |

### Abonnements `/api/abonnements`
| Méthode | URL | Description |
|---|---|---|
| GET | `/api/abonnements` | liste des abonnements |
| GET | `/api/abonnements/{id}` | un abonnement |
| POST | `/api/abonnements?abonneId=1&suiviId=2` | suivre un étudiant |
| DELETE | `/api/abonnements/{id}` | ne plus suivre |
| GET | `/api/abonnements/etudiant/{id}/suivis` | étudiants suivis |
| GET | `/api/abonnements/etudiant/{id}/abonnes` | abonnés d'un étudiant |

### Hashtags `/api/hashtags`
| Méthode | URL | Description |
|---|---|---|
| GET | `/api/hashtags` | liste des hashtags |
| GET | `/api/hashtags/{id}` | un hashtag |
| POST | `/api/hashtags` | créer |
| PUT | `/api/hashtags/{id}` | modifier |
| DELETE | `/api/hashtags/{id}` | supprimer |

> Un **like** et un **abonnement** ne se modifient pas : on les ajoute ou on les retire. Ils n'ont donc pas de `PUT`.

---

## Exemple de requête (Postman)

**POST** `http://localhost:8081/api/etudiants`, avec dans *Body → raw → JSON* :
```json
{
  "nom": "Ben Salah",
  "prenom": "Amine",
  "email": "amine@studiva.tn",
  "motDePasse": "amine2026"
}
```
Réponse : **201 Created**. Le mot de passe n'est jamais renvoyé dans les réponses.

---

## Règles métier

- L'email d'un étudiant doit être **unique**.
- Un étudiant ne peut aimer **qu'une seule fois** la même publication.
- Un étudiant **ne peut pas se suivre lui-même**.
- Un abonnement ne peut pas être créé **en double**.
- Deux hashtags ne peuvent pas avoir le **même nom**.

---

## Gestion des erreurs

Les erreurs sont gérées par des **exceptions personnalisées** (`NotFoundException`, `ConflictException`, `BadRequestException`). Le `GlobalExceptionHandler` (`@RestControllerAdvice`) renvoie un message JSON clair.

| Code | Signification | Exemple |
|---|---|---|
| 200 | OK | lecture ou modification réussie |
| 201 | Created | création réussie |
| 204 | No Content | suppression réussie |
| 400 | Bad Request | données invalides, un étudiant qui se suit lui-même |
| 404 | Not Found | étudiant ou publication introuvable |
| 409 | Conflict | email déjà utilisé, like ou abonnement en double |
| 500 | Internal Server Error | erreur imprévue |


