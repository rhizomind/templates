FROM openjdk:11-jdk-slim

ADD {{artifactId}}-{{artifactVersion}}.jar /opt/solidify/{{artifactId}}.jar

WORKDIR /opt/solidify

CMD java -XX:MaxRAMPercentage=$RAMPercentage -XX:MinRAMPercentage=$RAMPercentage -XshowSettings:vm -Dspring.profiles.active=$PROFILE -jar ./{{artifactId}}.jar
