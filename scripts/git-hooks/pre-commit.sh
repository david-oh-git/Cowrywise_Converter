#!/bin/sh
echo "Running pre -commit checks..."

JAVA_HOME=$(/usr/lib/jvm/java-8-openjdk-amd64 -v 1.8)
export JAVA_HOME

OUTPUT="/tmp/res"
./gradlew ktlintFormat spotlessApply ktlint --daemon > ${OUTPUT}
EXIT_CODE=$?
if [ ${EXIT_CODE} -ne 0 ]; then
    cat ${OUTPUT}
    rm ${OUTPUT}
    echo "Pre Commit Checks Failed. Please fix the above issues before committing"
    exit ${EXIT_CODE}
else
    rm ${OUTPUT}
    echo "Pre Commit Checks Passed -- no problems found"
fi