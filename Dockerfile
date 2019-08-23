ARG APP_INSIGHTS_AGENT_VERSION=2.3.1
FROM hmctspublic.azurecr.io/base/java:openjdk-8-distroless-1.0

RUN mkdir -p /usr/local/bin
#RUN apk update && apk upgrade && apk add bash
COPY lib/applicationinsights-agent-2.3.1.jar lib/AI-Agent.xml /opt/app/
COPY build/libs/persistence-service.jar /opt/app/
COPY docker/lib/wait-for-it.sh /usr/local/bin
#RUN chmod +x /usr/local/bin/wait-for-it.sh


EXPOSE 8282
CMD [ "persistence-service.jar" ]
