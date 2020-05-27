FROM openjdk:8
ADD target/file-upload-db-service.jar file-upload-db-service.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "file-upload-db-service.jar"]