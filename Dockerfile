FROM docker.io/eclipse-temurin:17.0.7_7-jre

WORKDIR /app
COPY target/ProfileUpdate.jar /app/ProfileUpdate.jar
EXPOSE 8086
CMD ["java", "-jar", "ProfileUpdate.jar"]