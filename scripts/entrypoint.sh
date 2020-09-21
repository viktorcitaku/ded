#!/bin/sh
set -e

# The JVM_ARGS should not be wrapped with quotes otherwise it will lead to: cannot load main class error
# shellcheck disable=SC2068
# exec java -XX:MaxRAMPercentage="${MEM_MAX_RAM_PERCENTAGE}" -Xss"${MEM_XSS}" -XX:+UseContainerSupport ${JVM_ARGS} -jar payara-micro.jar "$@"
exec java -XX:MaxRAMPercentage="${MEM_MAX_RAM_PERCENTAGE}" -Xss"${MEM_XSS}" -XX:+UseContainerSupport ${JVM_ARGS} \
  -jar payara-micro.jar --deploydir /opt/payara/deployments --nocluster --contextroot ded --postbootcommandfile post-boot-commands.asadmin --port ${PORT}