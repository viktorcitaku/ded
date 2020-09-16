#!/bin/sh
set -e

# The JVM_ARGS should not be wrapped with quotes otherwise it will lead to: cannot load main class error
# shellcheck disable=SC2068
exec java -XX:MaxRAMPercentage="${MEM_MAX_RAM_PERCENTAGE}" -Xss"${MEM_XSS}" -XX:+UseContainerSupport ${JVM_ARGS} -jar payara-micro.jar "$@"