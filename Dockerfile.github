FROM gradle:jdk8 as builder

COPY . /home/gradle/src
USER root
RUN chown -R gradle:gradle /home/gradle/src
USER gradle

WORKDIR /home/gradle/src
RUN gradle assemble

FROM openjdk:8-alpine

RUN mkdir -p /usr/local/bin
RUN apk update && apk upgrade && apk add bash
COPY docker/lib/wait-for-it.sh /usr/local/bin
RUN chmod +x /usr/local/bin/wait-for-it.sh


COPY docker/entrypoint.sh /
COPY --from=builder /home/gradle/src/build/libs/persistence-service.jar /persistence-service.jar

HEALTHCHECK --interval=10s --timeout=10s --retries=10 CMD http_proxy= curl --silent --fail http://localhost:8282/health

EXPOSE 8282

ENTRYPOINT [ "/entrypoint.sh" ]
