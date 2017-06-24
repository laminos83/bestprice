#!/bin/bash

mvn clean install &&
cd target &&
java -jar bluespurs_test-1.0.jar --port 8080
