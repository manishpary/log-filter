# log-filter
### Running program

To run the application, you can eiter 
* Download source codes and import to Intellij  as a gradle project
* build the project
* Select LogFilterApplication class -> open run configuration 
* in Program arguments field, fill **log file absolute path**.

OR
* Download the source code
* Run `./gradlew clean build` under the root project directory in a command line window
* Under same directory, run `java -jar build/libs/log-filter-0.0.1-SNAPSHOT.jar "<PATH>"`



## To run test

Either
* under the root project directory in a command line window, then run ``` ./gradlew test ```

OR
* in Intellij, right click /src/test/java, then select "run all tests"
