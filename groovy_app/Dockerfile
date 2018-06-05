from java:8
COPY . .
RUN ./gradlew clean build
EXPOSE 3000
ENTRYPOINT ["./gradlew", "bootRun"]
