FROM java:8-jdk-alpine
COPY  /build/libs/kubeconfigmap-all-1.0.jar test
WORKDIR test
ADD kubeconfigmap-all-1.0.jar kubeconfigmap-all-1.0.jar
EXPOSE 8080
CMD ["java", "-jar", "kubeconfigmap-all-1.0.jar"]
