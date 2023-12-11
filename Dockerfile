FROM jhipster/jhipster:v8.1.0
USER jhipster
COPY --chown=jhipster:jhipster . /home/jhipster/jhipster-online/
RUN \
    cd /home/jhipster/jhipster-online/ && \
    rm -Rf target node_modules && \
    chmod +x mvnw && \
    sleep 1 && \
    ./mvnw package -Pgcp -DskipTests && \
    mv /home/jhipster/jhipster-online/target/*.war /home/jhipster && \
    rm -Rf /home/jhipster/jhipster-online/ /home/jhipster/.m2 /home/jhipster/.cache /tmp/* /var/tmp/*

USER root
RUN \
    npm install -g generator-jhipster-azure-spring-apps

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS=""
CMD echo "The application will start in ${JHIPSTER_SLEEP}s..." && \
    sleep ${JHIPSTER_SLEEP} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /home/jhipster/jhonline*.war
EXPOSE 8080
