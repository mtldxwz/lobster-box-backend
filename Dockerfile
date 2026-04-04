FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# 复制 Maven 配置文件
COPY pom.xml .

# 安装 Maven 并下载依赖
RUN apk add --no-cache maven && \
    mvn dependency:go-offline -B

# 复制源代码
COPY src ./src

# 构建项目
RUN mvn clean package -DskipTests

# 暴露端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "target/*.jar"]