FROM amazoncorretto:20-alpine
VOLUME /tmp
ADD user-starter/target/user-service-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-Xmx256m -Xms256m"
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar
EXPOSE 8081