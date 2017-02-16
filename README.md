# Localisator Server


Compile in an IDE by exporting the project, or use Maven
```bash
mvn package
```

To execute, in an IDE, run the class Main.

To launch jar after mvn package:
```bash
java -jar target/LocalisatorServeur-1.0-SNAPSHOT.jar
```

The GPS and users data are stored in the folder res/.

To connect to the server, in the mobile application, use the
IP adress of the machine running the server, and port 4567
```
<IP address of machine running the server>:4567
```