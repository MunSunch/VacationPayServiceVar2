FROM bellsoft/liberica-openjdk-alpine:21.0.4 AS builder
EXPOSE 8080
WORKDIR /application
COPY . .
RUN --mount=type=cache,target=/root/.m2 chmod +x mvnw && ./mvnw clean install -Dmaven.test.skip

ARG SPRING_SECURITY_USER_NAME
ARG SPRING_SECURITY_USER_PASSWORD

ENV SPRING_SECURITY_USER_NAME=${SPRING_SECURITY_USER_NAME}
ENV SPRING_SECURITY_USER_PASSWORD=${SPRING_SECURITY_USER_PASSWORD}

FROM bellsoft/liberica-openjre-alpine:21.0.4 AS layers
WORKDIR /application
COPY --from=builder /application/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM bellsoft/liberica-openjre-alpine:21.0.4
VOLUME /tmp
RUN adduser -S spring-user
USER spring-user
COPY --from=layers /application/dependencies/ ./
COPY --from=layers /application/spring-boot-loader/ ./
COPY --from=layers /application/snapshot-dependencies/ ./
COPY --from=layers /application/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]