FROM openjdk:17
COPY "./target/futbol-1.jar" "app.bar"
EXPOSE 8100
ENTRYPOINT [ "java", "-jar", "app.bar" ]