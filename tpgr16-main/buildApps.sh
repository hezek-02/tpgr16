#!/bin/bash

./apache-tomcat-10.1.15/bin/shutdown.sh

cd "ServidorWeb/"
mvn clean package
cd "target"
mv ServidorWeb-1.war ServidorWeb.war
cp ServidorWeb.war ../../apache-tomcat-10.1.15/webapps
cd ..
cd ..

cd "DispositivoMovil/"
mvn clean package

cd "target"
mv DispositivoMovil-1.war DispositivoMovil.war
cp DispositivoMovil.war ../../apache-tomcat-10.1.15/webapps
cd ..
cd ..
./apache-tomcat-10.1.15/bin/startup.sh
