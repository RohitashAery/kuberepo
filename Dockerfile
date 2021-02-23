FROM java:8-jdk-alpine
COPY C:/Rohitash/Ericsson/Projects/ATT/ATT_PI/PI1210/f886/test/kubeconfigmap-all-1.0.jar test
WORKDIR test
ADD kubeconfigmap-all-1.0.jar kubeconfigmap-all-1.0.jar
EXPOSE 8080
CMD ["java", "-jar", "kubeconfigmap-all-1.0.jar"]
