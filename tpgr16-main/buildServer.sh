#!/bin/bash

cd "ServidorCentral/"

mvn clean package

cd "target/"

java -jar ServidorCentral-1-jar-with-dependencies.jar

