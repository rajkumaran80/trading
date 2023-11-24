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

## Example
Sample trade data file :trade.csv

date,product_id,currency,price
20160101,1,EUR,10.0
20160101,2,EUR,20.1
20160101,3,EUR,30.34
20160101,11,EUR,35.34

Sample HTTP Response (text/csv)

date,product_name,currency,price
20160101,Treasury Bills Domestic,EUR,10
20160101,Corporate Bonds Domestic,EUR,20.1
20160101,REPO Domestic,EUR,30.34
20160101,Missing Product Name,EUR,35.34
