FROM openjdk:17-jdk-alpine
COPY target/tondeuse-0.0.1-SNAPSHOT.jar tondeuse.jar
ENTRYPOINT ["java", "-jar", "tondeuse.jar"]
