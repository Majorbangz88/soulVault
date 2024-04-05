#FROM maven:3.8.7 as build
#COPY . .
#RUN mvn package
#
#FROM openjdk:17
#COPY --from=build target/diaryApplication-0.0.1-SNAPSHOT.jar /app/diaryApplication.jar
#ENTRYPOINT ["java", "-jar", "diaryApplication-0.0.1-SNAPSHOT.jar"]

FROM maven:3.8.7 as build
COPY . .
RUN mvn  package

FROM openjdk:17
COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]