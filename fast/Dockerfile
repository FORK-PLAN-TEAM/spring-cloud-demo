FROM openjdk:8-jdk-alpine
ADD fast-0.0.1-SNAPSHOT.jar fast-0.0.1-SNAPSHOT.jar
ENV PARAMS=""
EXPOSE 8088
ENTRYPOINT ["sh" , "-c" , "java $PARAMS  -jar /fast-0.0.1-SNAPSHOT.jar"]