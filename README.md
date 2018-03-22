Enmasse playground: Spring Boot JMS (Artemis) producer application

Uses the ActiveMQ Artemis JMS client. 

* The application is meant to be deployed on OpenShift.
* To deploy to OpenShift using the Fabric8 Maven plugin:
```
$ mvn fabric8:deploy -Popenshift
```
* The application expects a ConfigMap named `consumer-jms-artemis-sb` containing an `application.properties` file.
* Example `application.properties`:
```
artemis.host=messaging.enmasse.svc.cluster.local
artemis.port=5671
artemis.query=sslEnabled=true&transport.trustAll=false&transport.verifyHost=true

spring.artemis.url=tcp://${artemis.host}:${artemis.port}?${artemis.query}
spring.artemis.user=user
spring.artemis.password=password

spring.artemis.session-cache-size=25

spring.jms.pub-sub-domain=false

producer.destination=queue-test
```
* The application expects a secret called `enmasse-truststore` containing a JKS truststore with the Enmasse messaging certificate.
* To create the truststore:
```
keytool -importcert -trustcacerts -file external-certs-messaging.crt -keystore enmasse.jks -storepass password -noprompt
```
* To send a message to the destination address:
```
$ curl -X POST -d '{"message":"test"}' <application route URL>/message 
```