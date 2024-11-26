# Étape de build : utiliser l'image de base avec JDK 21
FROM openjdk:21-jdk-slim as build

# Définir le répertoire de travail dans l'image
WORKDIR /app

# Copier le fichier pom.xml pour télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source et compiler l'application
COPY src /app/src
RUN mvn clean package -DskipTests

# Étape finale : utiliser l'image de base avec JDK 21 pour exécuter l'application
FROM openjdk:21-jdk-slim

# Répertoire de travail dans l'image
WORKDIR /app

# Copier le fichier JAR généré dans l'image
COPY --from=build /app/target/*.jar app.jar

# Exposer le port sur lequel l'application Spring Boot écoute
EXPOSE 8080

# Commande pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
