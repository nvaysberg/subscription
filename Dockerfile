FROM openjdk:17.0.2-jdk-oraclelinux8
COPY target/subscription-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
