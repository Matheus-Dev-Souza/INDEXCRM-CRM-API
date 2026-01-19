# Etapa 1: Build (Construção)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Run (Execução)
# Trocamos 'openjdk:17-jdk-slim' por 'eclipse-temurin:17-jdk-alpine'
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# ATENÇÃO: Aqui ajustamos o nome do arquivo para bater com o artifactId do seu pom.xml
# O * (asterisco) ajuda a pegar o arquivo independente da versão exata
COPY --from=build /app/target/index-crm-api-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]