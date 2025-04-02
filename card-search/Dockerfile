FROM gradle:8.7.0-jdk17 AS build_stage

ARG SPRING_DATASOURCE_URL
ENV SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ENV SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME
ARG DEV_DB_PASSWORD
ENV DEV_DB_PASSWORD=$DEV_DB_PASSWORD

WORKDIR /app


COPY --chown=gradle:gradle ./ ./

RUN gradle clean assemble

FROM openjdk:17-alpine

COPY --from=build_stage /app/build/libs/*.jar ./build/app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c"]

CMD ["java -jar build/app.jar"]
