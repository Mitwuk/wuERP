FROM openjdk:8-jre
WORKDIR /
ADD target/erp.jar app.jar
ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]