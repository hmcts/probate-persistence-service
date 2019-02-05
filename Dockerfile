FROM hmcts/cnp-java-base:openjdk-8u181-jre-alpine3.8-1.0

# Mandatory!
ENV APP persistence-service.jar
ENV APPLICATION_TOTAL_MEMORY 1024M
ENV APPLICATION_SIZE_ON_DISK_IN_MB 66

RUN mkdir -p /usr/local/bin
#RUN apk update && apk upgrade && apk add bash
COPY build/libs/$APP /opt/app/
COPY docker/lib/wait-for-it.sh /usr/local/bin
#RUN chmod +x /usr/local/bin/wait-for-it.sh


COPY docker/entrypoint.sh /


HEALTHCHECK --interval=10s --timeout=10s --retries=10 CMD http_proxy="" wget -q --spider http://localhost:8282/health || exit 1

EXPOSE 8282

ENTRYPOINT [ "/entrypoint.sh" ]
