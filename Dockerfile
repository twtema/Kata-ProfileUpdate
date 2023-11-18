FROM openjdk:17

WORKDIR /app
COPY target/ProfileUpdate.jar /app/ProfileUpdate.jar
EXPOSE 8086
CMD ["java", "-jar", "ProfileUpdate.jar"]