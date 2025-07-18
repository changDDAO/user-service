FROM amazoncorretto:17
LABEL authors="changhoyoun"

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} user-service.jar

EXPOSE 8082

ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-jar", "/user-service.jar"]