FROM amazoncorretto:17
ADD /target/income-cls-response-processor-0.1.0.jar income-cls-response-processor-0.1.0.jar
ENTRYPOINT ["java","-jar","income-cls-response-processor-0.1.0.jar"]