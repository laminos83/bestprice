# B Spurs test best price WM and BB

###### Technical platform :

- Install Oracle jdk 8 - for running environment
- Install Maven 3- for building project (ubuntu: sudo apt-get install maven)
- Check java and maven version (java -version, mvn -version)

###### Execution :

- make **run.sh** bash script executable   (chmod +x run.sh)
- run bash script (./run.sh)
OR
- Get to the root directory of project
- Build the project (mvn clean install)
- Run generated jar (java -jar target/bluespurs_test-1.0.jar --port 8080)

###### Test :

- install curl (apt-get install curl)
- curl [http://localhost:8080/bluespurs_test-1.0/rs/product/search?name=ipod]
OR
- use web interface [http://localhost:8080/bluespurs_test-1.0/]

###### Comments :

- Jersey RESTful framework is used for developing RESTful Web Services 
- Payara embedded & Micro used for execution



