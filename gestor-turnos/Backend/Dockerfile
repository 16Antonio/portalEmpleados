# Etapa 1: Descargamos Maven y Java 21 para compilar el proyecto
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Creamos el servidor final solo con lo necesario para ejecutarlo
FROM eclipse-temurin:21-jre
WORKDIR /app
# Copiamos el archivo .jar que se ha generado en la Etapa 1
COPY --from=build /app/target/*.jar app.jar
# Exponemos el puerto
EXPOSE 8080
# Comando para encender el servidor
ENTRYPOINT ["java", "-jar", "app.jar"]