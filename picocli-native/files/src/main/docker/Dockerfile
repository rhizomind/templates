FROM gcr.io/distroless/static-debian11:debug

COPY {{artifactId}} /var/lib/rhizomind/picocli/{{artifactId}}

WORKDIR /var/lib/rhizomind/picocli

ENTRYPOINT ["{{artifactId}}"]
