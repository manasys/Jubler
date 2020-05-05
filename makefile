
build:
	mvn clean package

run:
	java -Xmx8G -jar modules/installer/target/jubler/lib/jubler.jar

run2:
	java -jar modules/installer/target/jubler/lib/jubler.jar

run3:
	java -jar modules/core/target/jubler-7.0.0.jar
