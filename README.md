# Notes Meetup #10 demo

export MAVEN_OPTS="-Xmx512m"

INITIAL TAG: beginning

1) Starting and introducing the registry and centralized config

    docker-compose -f catalog/src/main/docker/jhipster-registry.yml up -d
    
2) Start catalog (ide), registry UI, calls via swagger
    
3) Start gateway (ide), presenting API gateway

4) Generate a new microservice: booking

    mkdir booking && cd booking && jhipster
    ./mvnw

5) Importation of a JDL file in booking and gateway services

    cd booking && jhipster import-jdl ../jdl/booking.jdl
    cd gateway && jhipster import-jdl ../jdl/booking.jdl

BACKUP TAG: all-generated

6) Start the full stack with docker (close everthing before to free up ram)

    mvn package -DskipTests -Pprod,zipkin dockerfile:build
    mkdir docker && jhipster docker-compose

    docker-compose up -d && watch docker-compose ps

7) Checkup that everything is working, registry, docker-compose logs, docker stats

8) (optionel) Test microservice scaling

    docker-compose scale catalog-app=2               

9) Go to prod with K8S
    mkdir k8s && cd k8s && jhipster kubernetes

    kubectl apply -f registry/
    kubectl apply -f console/

    kubectl port-forward jhipster-registry-*** 8761

    kubectl apply -f gateway/
    kubectl port-forward gateway-*** 8080

    kubectl apply -f catalog/
    kubectl apply -f booking/

    watch kubectl get pod

10) Monitoring
    (Mettre tout les logs en trace avec le registry)

    kubectl port-forward jhipster-console-*** 5601
    kubectl port-forward jhipster-zipkin-*** 9411



