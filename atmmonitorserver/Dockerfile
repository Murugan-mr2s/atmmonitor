
# pull image from repository
#FROM openjdk:17-jdk-alpine

# create directory

#RUN mkdir /apps

# copy jar file into app directory
#ADD target/*.jar /apps/atm-monitor-server.jar

# dir change into apps
#WORKDIR /apps

#expose port
#EXPOSE 8080

# execute jar
#CMD ["java" ,"-jar" , "atm-monitor-server.jar"]

# Use the latest Alpine image as the base
FROM alpine:latest

# Install the OpenJDK 21 package
RUN apk update && apk add openjdk21-jdk

# Set JAVA_HOME environment variable
ENV JAVA_HOME /usr/lib/jvm/java-21-openjdk

# Verify the installation
RUN java --version

RUN mkdir /apps

# copy jar file into app directory
ADD target/*.jar /apps/atm-monitor-server.jar

# dir change into apps
WORKDIR /apps

#expose port
EXPOSE 8080

# execute jar
CMD ["java" ,"-jar" , "atm-monitor-server.jar"]
