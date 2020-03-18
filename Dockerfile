ARG APP_INSIGHTS_AGENT_VERSION=2.5.1
FROM hmctspublic.azurecr.io/base/java:openjdk-8-distroless-1.4

COPY lib/AI-Agent.xml /opt/app/
COPY build/libs/persistence-service.jar /opt/app/

EXPOSE 8282
CMD [ "persistence-service.jar" ]
