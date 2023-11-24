# Trading
Trading is a java project to deal with trade enrichment.

## Repository
checkout the project from github https://github.com/rajkumaran80/trading.

## Prerequisite
- JAVA 17
- MAVEN

## Usage
To locally build the project
- mvn clean install

To run as application
- mvn spring-boot:run

To test with curl
- curl --request POST --data-binary @trade.csv --header 'Content-Type: text/csv' --header 'Accept: text/csv' http://localhost:8080/api/v1/enrich

To test with postman
- POST http://localhost:8080/api/v1/enrich
- Content-Type : text/csv
- Input as binary file or request body
