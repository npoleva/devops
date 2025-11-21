FROM maven:3.9.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S appgroup && \
    adduser -S appuser -G appgroup && \
    rm -rf /var/cache/apk/*

COPY --from=builder --chown=appuser:appgroup /app/target/my-app-1.0.0.jar app.jar

USER appuser

ENV SERVER_PORT=8080
ENV JAVA_OPTS="-Xmx256m -Xms128m"


EXPOSE 8080

CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
