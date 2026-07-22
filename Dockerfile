FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/loan-management-system-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=k8s"]
