# My Assets

An application to track your assets, locations, and warranties.

## Description

The application is fully functioning, you can create category, and location as well as Assets.
### Installing

* Install .zip file, and extract all. Make sure you know where the files are extracting.
* In Eclipse IDE, go to File > Import > General > Existing Projects into Workspace
* Locate the extracted dev-07-11 v1.1 file, and select folder.
* Add Sqlite JDBC Driver from libSqliteDriver folder to your JRE System Library STEPS: JRE System Library > Build Path > Configure Build Path > Libraries > ADD JAR > dev-07-11 > libSqliteDriver > sqlite-jdbc-3.42.00.jar> Apply and Close

### Executing program

* To execute, you must add JavaFx runtime components.
* Right click Main.java, located inside src/application, go to Run As > Run Configurations.
* Under "Arguments", write the following where "path to JavaFX" is your directory path to your JavaFX installation's lib folder.

    --module-path "path to JavaFX" --add-modules javafx.controls,javafx.graphics,javafx.fxml

* Click "Apply".
* Under "Dependencies", select JavaFX (under Classpath Entries) and remove it.
* Select "Modulepath Entries", and select Advanced > Add Library > User Library > JavaFX.
* After clicking "Finish", apply the changes and run the program.

## Authors

Balkarandeep Singh

Huynh Duc Vo

Jonathan Sy

Phong Diep


