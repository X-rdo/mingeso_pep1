FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} mingeso_pep1.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/mingeso_pep1.jar"]