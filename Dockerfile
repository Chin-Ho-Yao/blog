FROM openjdk:8-slim
MAINTAINER Jack<q82717182q@gmail.com>
# 設定工作目錄
#WORKDIR /app
#ARG JAR_FILE=target/*.jar
# 將 Maven/Gradle 構建的 JAR 文件複製到容器中
COPY ./target/blog.jar blog.jar
# 暴露應用程序運行的端口
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=dev
# 啟動應用程序
ENTRYPOINT ["java", "-jar", "blog.jar"]