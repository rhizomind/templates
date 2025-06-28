FROM openjdk:11-jdk-slim

ADD test-component-0.1-SNAPSHOT.jar /opt/solidify/test-component.jar

WORKDIR /opt/solidify

CMD java -XX:MaxRAMPercentage=$RAMPercentage -XX:MinRAMPercentage=$RAMPercentage -XshowSettings:vm -Dspring.profiles.active=$PROFILE -jar ./test-component.jar
