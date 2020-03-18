# Probate Persistence Service 
Microservice to handle persistence layer for probate

## This microservice is no longer in use

## Getting Started
### Prerequisites
- Java 8
- Gradle
- Docker


### Running the application
#### Docker
To run the service locally you can use the *postgres* Docker image which can be run with the following command:  
```
S docker run --name postgres -e POSTGRES_PASSWORD=password123 -e POSTGRES_USER=probate -p 15432:5432 -d postgres
```

Once the image has been imported or started, you can access the database with the following command:  
```
$ psql -p 15432 -U probate -h 0.0.0.0
```  
You will be prompted for a password which is `password123`.

#### Building and Running the Persistence Service
Install dependencies and build the service by executing the following command:  
```
$ ./gradlew clean build
```

Once the build has completed, you will find the new *.jar* in `build/libs`. If the postgres Docker container is running, you can run the *.jar*, else you will need to refer to the previous section before running the command:  
```
$ java -jar build/libs/persistence-service-1.0.1.jar
```

## Developing

### Unit tests

To run all unit tests please execute the following command:

```bash
$ ./gradlew test
```

### Coding style tests

To run all checks (including unit tests) please execute the following command:

```bash
$ ./gradlew check
```

## Versioning

We use [SemVer](http://semver.org/) for versioning.
For the versions available, see the tags on this repository.

## Troubleshooting

### IDE Settings

#### Project Lombok Plugin
When building the project in your IDE (eclipse or IntelliJ), Lombok plugin will be required to compile. 

For IntelliJ IDEA, please add the Lombok IntelliJ plugin:
* Go to `File > Settings > Plugins`
* Click on `Browse repositories...`
* Search for `Lombok Plugin`
* Click on `Install plugin`
* Restart IntelliJ IDEA

Plugin setup for other IDE's are available on [https://projectlombok.org/setup/overview]

#### JsonMappingException when running tests in your IDE
Add the `-parameters` setting to your compiler arguments in your IDE (Make sure you recompile your code after).  
This is because we use a feature of jackson for automatically deserialising based on the constructor.  
For more info see: https://github.com/FasterXML/jackson-modules-java8/blob/a0d102fa0aea5c2fc327250868e1c1f6d523856d/parameter-names/README.md

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.
