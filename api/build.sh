mvn -f account/pom.xml clean install
mvn -f auth/pom.xml clean install
mvn -f data/pom.xml clean install
mvn -f voucher/pom.xml clean install

mvn -f account-service/pom.xml clean package
mvn -f auth-service/pom.xml clean package
mvn -f data-service/pom.xml clean package
mvn -f voucher-service/pom.xml clean package

mvn -f gateway-service/pom.xml clean package
