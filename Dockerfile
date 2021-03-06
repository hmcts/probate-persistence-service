ARG APP_INSIGHTS_AGENT_VERSION=2.3.1
FROM hmctspublic.azurecr.io/base/java:openjdk-8-distroless-1.0
# Mandatory!
ENV APP persistence-service.jar

COPY build/libs/$APP /opt/app/
COPY lib/applicationinsights-agent-2.3.1.jar lib/AI-Agent.xml /opt/app/


EXPOSE 8282
CMD [ "persistence-service.jar" ]
