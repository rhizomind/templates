FROM gcr.io/distroless/static-debian11:debug

COPY {{artifactId}} /var/lib/{{artifactId}}/{{artifactId}}

WORKDIR /var/lib/{{artifactId}}/{{artifactId}}

ENTRYPOINT ["{{artifactId}}"]
