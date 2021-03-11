FROM java:8

RUN chmod 777 mvnw
RUN sh mvnw package -Dmaven.test.skip=true
CMD sh mvnw tomcat:run