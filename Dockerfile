# -------------------------------------------------------------------
# ETAPA 1: BUILD (Compilación)
# Usamos una imagen oficial de Java 21 (Eclipse Temurin es muy estable)
# Le ponemos el apodo 'builder' para referirnos a ella luego.
# -------------------------------------------------------------------
FROM eclipse-temurin:21-jdk-alpine AS builder

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos primero SOLO los archivos de configuración de Gradle.
# ¿Por qué? Para aprovechar la "Caché de Docker". Si no cambias dependencias,
# Docker se saltará el paso de descargar internet entero.
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Damos permisos de ejecución al script de gradle y descargamos librerías
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon || return 0

# Ahora sí, copiamos tu código fuente (Java)
COPY src src

# Compilamos y creamos el JAR.
# -x test: Saltamos los tests para que el despliegue sea rápido en producción
# (Se asume que ya pasaste los tests en tu etapa de CI/CD o en local)
RUN ./gradlew bootJar --no-daemon -x test

# -------------------------------------------------------------------
# ETAPA 2: RUNTIME (Ejecución)
# Usamos una imagen JRE (Java Runtime Environment) que es mucho más ligera
# porque no tiene compiladores, solo lo necesario para correr Java.
# -------------------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiamos SOLAMENTE el archivo .jar generado en la etapa anterior (builder)
COPY --from=builder /app/build/libs/*.jar app.jar

# Exponemos el puerto 8080 (Informativo, para saber dónde escucha Spring)
EXPOSE 8080

# Comando que se ejecuta al arrancar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]