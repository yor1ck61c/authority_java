FROM java:8

COPY . .

RUN chmod 777 mvnw
RUN sh mvnw package -Dmaven.test.skip=true
CMD java -jar ./target/yorick61c-0.0.1-SNAPSHOT.jar