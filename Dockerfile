FROM hmcts/cnp-java-base:openjdk-8u191-jre-alpine3.9-1.0

RUN mkdir -p /usr/local/bin
#RUN apk update && apk upgrade && apk add bash
COPY build/libs/persistence-service.jar /opt/app/
COPY docker/lib/wait-for-it.sh /usr/local/bin
#RUN chmod +x /usr/local/bin/wait-for-it.sh

HEALTHCHECK --interval=10s --timeout=10s --retries=10 CMD http_proxy="" wget -q --spider http://localhost:8282/health || exit 1

EXPOSE 8282
CMD [ "persistence-service.jar" ]
