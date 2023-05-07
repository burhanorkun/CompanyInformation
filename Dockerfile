FROM maven:3.8.3-openjdk-17 AS build
#FROM maven:3.8.2-jdk-8

WORKDIR /companyinfo
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run
