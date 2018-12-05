# User Logging System and Message Center Tool
#### Jacob Kahn - Team Lead
#### Bhargav Makkapati - Scrum Master
#### Vinicius Tavares - Developer
#### Patrick Jean Baptiste - Developer
#### Joshua Carberry - Developer

## Overview
The software will be developed for the Undersea Warfare Rapid Innovation Center (USWRIC), a type of makerspace within the Naval Undersea Warfare Center (NUWC). Within the USWRIC, users can experiment with equipment such as Raspberry Pi Computers and 3D printers, reserve breakout rooms for team meetings, and check equipment out of the room for personal use. Our system seeks to make administration of the room an easy task. User loggin is taken care of through an Android application which will allow users to sign into the room by just selecting their name whenever they enter. 3D printers and breakout rooms can be reserved through another section of the Android application. Administrators can check equipment out to users through the Android application as well. Finally, there is a local Windows application which will handle administrative activities such as viewing room usage statistics, viewing reservations, and displaying messages and events to USWRIC displays. 

## Running the Server
### Using our .jar file
The server is exported to a .jar located in the *runnables* folder. To run the jar on your local computer, a MySQL database must be running locally, and the database url must be `jdbc:mysql://localhost:3306/user_logging_system`. The application will take care of creating all necessary tables as long as that DB exists. Additionally, the username and password for the DB must both be `root`. A runtime exception might fly past on startup but it will not affect use of the system. 

### Using custom database url or login credentials
Download the poject and import it into your preferred IDE as a Gradle project. The correct file to import from is the *build.gradle* file located in the senior-design-project folder. If you want to use a different DB url or username/password, these values must be changed in the database configuration located in the file *src/main/java/senior/design/group10/application/HibernateConfig.java*. Once in this file, modify the required values in the `dataSource()` method. The application can be run from *src/main/java/senior/design/group10/application/Application.java*.

## Running the Client
### Import into Android Studio
Currently, the only way to run the Android application is to use Android Studio. Import the project into Android Studio. The Android application is contained in the *UserLoginSystem* folder. Once imported, open up the ***Edit Configurations*** by navigating to Run -> Edit Configurations. Click the green + symbol in the top lefthand corner to add an Android App if one does not exist already. Within this app, the following settings are required:
* Module should be **app**
* Deploy should be **Default APK**
* Launch should be **Specified Activity**
* Activity should be **nuwc.userloginsystem.initialScreen**
* Target should be **Open Select Deployment Target Dialog**

Once these settings are correct, click the **Apply** button at the bottom right hand corner. Clicking the green triangle in the top right side of the screen should open the emulator and our application will display.

**The server must be running in order for the application to fully work.**
