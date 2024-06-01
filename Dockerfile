# 使用 OpenJDK 的 Java 8 映像作為基礎映像
#FROM openjdk:8-jre-slim

FROM amazoncorretto:8u412-alpine3.19

#ARG version=8.412.08.1
ARG JAR_FILE=target/*.jar

# 設置容器的工作目錄
WORKDIR /app
# 將本地的 JAR 文件複製到容器中
COPY target/blog.jar /app/blog.jar
#COPY src/main/resources/application.properties /app/src/main/resources/
#COPY src/main/resources/old/application.yml /app/src/main/resources/
#COPY src/main/resources/old/application-dev.yml /app/src/main/resources/



# 執行應用程式
CMD ["java", "-jar", "/app/blog.jar"]