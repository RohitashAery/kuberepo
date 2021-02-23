FROM java:8-jdk-alpine
COPY  kubeconfigmap-all-1.0.jar .
WORKDIR .
ADD kubeconfigmap-all-1.0.jar kubeconfigmap-all-1.0.jar
EXPOSE 8080
CMD ["java", "-jar", "kubeconfigmap-all-1.0.jar"]
