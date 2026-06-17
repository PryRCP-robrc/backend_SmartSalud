# ===================================================================
#  Backend SmartSalud — Spring Boot 4 / Java 21
#  Build multi-stage: compila con JDK, corre con JRE liviano
# ===================================================================

# ---------- Stage 1: build ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiamos primero los archivos de gradle para cachear dependencias
COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle
RUN chmod +x gradlew
# Descarga el wrapper de Gradle (no falla si aun no hay deps que resolver)
RUN ./gradlew --version --no-daemon

# Copiamos el codigo fuente y compilamos el jar ejecutable
COPY src ./src
RUN ./gradlew bootJar --no-daemon -x test

# ---------- Stage 2: runtime ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos solo el jar resultante
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Las variables DB_URL, DB_USERNAME, DB_PASSWORD se inyectan desde compose
ENTRYPOINT ["java", "-jar", "app.jar"]
