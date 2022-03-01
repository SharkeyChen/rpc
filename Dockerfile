FROM java:8
MAINTAINER sharkey
ADD rpc-0.0.1-SNAPSHOT.jar rpc.jar
EXPOSE 9400
ENTRYPOINT ["java","-jar","/rpc.jar"]