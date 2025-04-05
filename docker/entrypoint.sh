#!/usr/bin/env bash

if [ -f "${CUSTOM_CONFIGURATION_PATH}/application.yaml" ]; then
  APPLICATION_CONFIG="--spring.config.additional-location=file:${CUSTOM_CONFIGURATION_PATH}/application.yaml"
fi

exec -- java ${JAVA_OPTS} -jar /app/app.jar ${APPLICATION_CONFIG}
