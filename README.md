
### Build a standalone app
- Package a standalone app with dependencies: `mvn clean package spring-boot:repackage -DskipTests=true` (1)
- Then you can run the generated jar in the target directory with `java -jar platform-0.1.jar`

### Directly run the app
- Run the app in one terminal with the command `mvn spring-boot:run`

### When the app is running
- Execute `run.sh` script in another terminal, which build the scenario given in the specifications
paper and create a trade order

(1)
`-DskipTests=true` option is because IT tests in `trading.dao` and `trading.services.it` are failing with errors like these:

`trading.dao.TradeRepoTest.testFindById(): Cannot invoke "org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager.persist(Object)" because "this.testEntityManager" is null`

`trading.services.it.SecurityServiceTest.testCreateAndSearchUser(): Cannot invoke "trading.services.SecurityService.createSecurities(java.util.List)" because "this.service" is null`

My first sight is that moving JPA configuration from `Application` to `JpaConfig` class is creating another type of issues while testing, as components are not loading only
in this scope, as app. works fine when run.

This JPA configuration file is done in this way to avoid this issue:
`NoSuchBeanDefinitionException: No bean named 'entityManagerFactory' is defined`
The solution was found here: 
`https://github.com/spring-projects/spring-boot/issues/256`
specifically targeting this comments and following ones:
`https://github.com/spring-projects/spring-boot/issues/256#issuecomment-33226274`
and finding the approach used in this one:
`https://github.com/spring-projects/spring-boot/issues/256#issuecomment-775084779`