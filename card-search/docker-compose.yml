FROM gradle:8.7.0-jdk17 AS build_stage

WORKDIR /app

COPY --chown=gradle:gradle ./ ./

RUN gradle clean assemble

FROM openjdk:17-alpine

COPY --from=build_stage /app/build/libs/*.jar ./build/app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c"]

CMD ["java -jar build/app.jar"]
