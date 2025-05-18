FROM openjdk:17
COPY "./target/futbol-1.jar" "app.jar"
EXPOSE 8100
ENTRYPOINT [ "java", "-jar", "app.jar" ]
