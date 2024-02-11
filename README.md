
### Build a standalone app
- Package a standalone app with dependencies: `mvn clean package spring-boot:repackage`
- Then you can run the generated jar in the target directory with `java -jar platform-0.1.jar`

### Directly run the app
- Run the app in one terminal with the command `mvn spring-boot:run`

### When the app is running
- Execute `run.sh` script in another terminal build the scenario given in the specifications
paper and create a trade order
