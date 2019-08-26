FROM java:8-jdk-alpine
COPY ./target/myRetail-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch myRetail-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","myRetail-0.0.1-SNAPSHOT.jar"]