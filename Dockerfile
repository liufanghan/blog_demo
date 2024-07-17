# 使用一个基础的 OpenJDK 17 镜像
FROM openjdk:17-jdk-alpine

# 将工作目录设置为 /app
WORKDIR /app

# 将本地的 JAR 文件复制到容器中的 /app 目录
COPY target/app.jar app.jar

# 暴露应用程序的端口
EXPOSE 8080

# 运行应用程序
ENTRYPOINT ["java", "-jar", "app.jar"]
