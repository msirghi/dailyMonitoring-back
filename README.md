## Daily Monitoring
<p align="center">
<img src="https://github.com/msirghi/dailyMonitoring-front/blob/master/src/assets/logo_transparent_2.png" width="240">
</p>

## Requirements ###

* Java 8

## Setup Project
* Setup Project :
    * Windows : `gradlew.bat wrapper`
    * Linux : `./gradlew wrapper`
* Setup environment variables for custom configuration in application.yml:
    * IntelliJ: [Run]->[Edit Configurations]->"Environment variables"  \
    Example:
      * email_username=email@email.com;
      * email_pwd=password;  

### Code
We are using Google [Java Style Guide](https://google.github.io/styleguide/javaguide.html) :

* Import Style Code rule in your IDE import :
    * Intellij IDEA : `config/intellij-java-google-style.xml`
    * Eclipse : `config/eclipse-java-google-style.xml`
* In order to check your code you need to run :
    * Windows : `gradlew.bat check`
    * Linux : `./gradlew check`
