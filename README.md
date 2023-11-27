# Personal Agenda Management System

The aim of this work is to develop a system to manage and organize a personal agenda, including appointments.

## Description

The objective of your work is to develop a system to manage and organize a personal agenda, including appointments and tasks, following these recommendations:

- The system should allow its user to manage their personal information and plan their daily activities, including planning and controlling tasks and scheduling appointments.

-  Besides recording information, the application should support various types of queries (e.g., tasks to be completed daily, weekly, monthly) and alarms (e.g., dentist appointment at 3:00 PM).

- Tasks should have a status (planned, active, completed) and a completion date.

- Each task will belong to a category (e.g., personal, work, projects). The list of categories should be managed by the user.

## Minimum Functionalities

- Task management (registration, query, modification, and removal)
- Category management
- Viewing today's and future tasks
- Marking a task as completed
- Other functionalities that you find important


## Functionalities Not Included in This Version

- Category management
- Start-end date of activity. It is only possible to create a one-time activity for the chosen day.

## Functionalities Included in This Version

- Task management (registration, query, modification, and removal)
- Viewing today's and future tasks
- Marking a task as completed
- Activity search system that can find activities in the database through the data: month, day, year, hour, title, description, category, status

## Notes of This Version

- The categories are fixed and include: 'Work', 'Personal', 'Education', 'Health and Wellness', 'Social', 'Travel', 'Financial', 'Reminders', 'Projects', 'Cultural and Entertainment', 'Family', 'Sports', 'Others'.
- The status of the activities are also fixed and include: 'Planned', 'Active', 'Completed'.

# Project Dependencies and Environment

### Gradle
- **Version:** 8.0.2
- **Build time:** 2023-03-03 16:41:37 UTC
- **Revision:** 7d6581558e226a580d91d399f7dfb9e3095c2b1d

### Java Virtual Machine
- **JVM:** 17.0.8 (Oracle Corporation 17.0.8+9-LTS-211)

### Operating System
- **OS:** Windows 11 10.0 amd64

### Database
- **Version:** 8.0.32

### Database Connector
- **MySQL Connector:** mysql:mysql-connector-java:8.0.27

# Database Setup

To set up the database for this project, use the following SQL commands:

```
CREATE SCHEMA `db_agenda` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE db_agenda;
CREATE TABLE compromissos (
    id_compromisso INT AUTO_INCREMENT PRIMARY KEY,
    data DATE,
    hora TIME,
    titulo TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    descricao TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
 	categoria ENUM('Trabalho', 'Pessoal', 'Educação', 'Saúde e Bem-Estar', 'Social', 'Viagens', 'Financeiro', 'Lembretes', 'Projetos', 'Cultural e Entretenimento', 'Família', 'Esportes', 'Outros') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
status ENUM('Planejada', 'Ativa', 'Concluída') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

```
## Using `gradlew`

The gradlew is the Gradle executable for Windows. The project was developed based on this application. The project folder already contains a build.gradle.kts file which includes the specifications of what we are using in the project.

```
/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/8.0.2/userguide/building_java_projects.html
 */
plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("com.xcporter.metaview") version "0.0.6"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")

    runtimeOnly("mysql:mysql-connector-java:8.0.27")
}

application {
    // Define the main class for the application.
    mainClass.set("agendajava.App")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>{
    options.encoding = "UTF-8"
}

```

**Execute the build command in ../agenda_pp/agendajava.**

**For Linux:**
```
./gradle run
```
**For Windows:**
```
./gradlew.bat run
```

## Application Important files
<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/folder_project.png?token=GHSAT0AAAAAACKUAAMBF5I3PHWWNDF6A67IZLD33IQ" width="350">
</p>

The source code, where the entire application is structured, can be found at:
```
/agenda_pp/agendajava/app/src/main/java/agendajava
```
There are three important folders in agendajava:

- AppointmentDAO contains all database queries.
- Conexaodb contains the structure for connecting to the database.
- Entity contains the simplest concept of a task.
- In the main folder, you will find screens and content related to user display.
- The entry point is the App.java file.


## Application Walkthrough

**1. Initial Screen**

The application starts in the form of a calendar, with navigation controls at the top to move forward and backward in months. Below, the days of the week are organized according to the logic that determines the start of the week, ensuring accuracy. Next, the days of the month are displayed, with those having scheduled appointments highlighted in red. At the bottom of the screen, there is a text box indicating the current date and a button to access advanced database queries.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/initial-screen.jpg?token=GHSAT0AAAAAACKUAAMBHIW2D4F4YHBDFZ4OZLD44XQ" width="350" title="Initial Screen">
    <n><figcaption>Initial Screen</figcaption></n>
</p>



**2. The Days**

Days have an associated event. Each day, when clicked once, triggers an event that has logic to retrieve the specific selected day. When double-clicked, a screen is triggered that provides the option to view the day's appointments or add a new one.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/dias.jpg?token=GHSAT0AAAAAACKUAAMAQ7J4C6OLCRZKWEFUZLD5ALQ"width="350" title="Days">
    <n><figcaption>A screen with a double click on a selected day</figcaption></n>
</p>


If there is nothing associated with that day, clicking on 'Ver compromissos do dia' will trigger a notification stating that there are no activities for that day.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/dias-sem-compromisso.jpg?token=GHSAT0AAAAAACKUAAMANFFW7AYFIOMT2JYKZLD5GFA"width="350" title="Screen for a day without appointments">
    <n><figcaption>Screen for a day without appointments</figcaption></n>
</p>

If there are appointments, another screen will open displaying the day's appointments in a paginated manner.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/dias-com-compomisso.jpg?token=GHSAT0AAAAAACKUAAMBXQWTYYMZVURXU7H2ZLD6ZQA" title="Screen for a day with appointments">
    <n><figcaption>Screen for a day with appointments</figcaption></n>
</p>

On the appointments screen, you can double-click on items in the list to edit their content. It's possible to edit the title, description, time, category, and status.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/double-click-image.jpg?token=GHSAT0AAAAAACKUAAMA435TDVOH3Z2WNPN6ZLD62NA" title="Double-click on items in the list to edit their content">
    <n><figcaption>Double-click on items in the list to edit their content</figcaption></n>
</p>

**3. Adding a new appointment**

To add a new appointment, simply double-click on the selected day, and then click on the green button labeled 'Adicionar Novo Compromisso'.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/adding_new_task.jpg?token=GHSAT0AAAAAACKUAAMA7NNFSONKL7VJ5T2WZLD625A" title="Screen with the event of a new Appointment">
    <n><figcaption>Screen with the event of a new Appointment</figcaption></n>
</p>

Data entry is subject to data validation, and if a requirement is not met, a warning will be raised, such as in the case of an invalid time format.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/treated-insertion.jpg?token=GHSAT0AAAAAACKUAAMARP2U4MS3SMWPYMP6ZLD63MA" title="Screen with an event for an incorrect time format">
    <n><figcaption>Screen with an event for an incorrect time format</figcaption></n>
</p>

If the data is valid, it is inserted into the system.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/addd-aaaa.png?token=GHSAT0AAAAAACKUAAMATJGFAHONZWEQHFRUZLD64BA" title="Inserting a valid input">
    <n><figcaption>Inserting a valid input</figcaption></n>
</p>


<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/saving_data.jpg?token=GHSAT0AAAAAACKUAAMBZUFDU7CLLD62YQXWZLD67TA" title="Valid inputed inserted on database">
    <n><figcaption>Valid inputed inserted on database</figcaption></n>
</p>

**4. Advanced Queries**

When clicking on 'Consultas Avançadas' a new screen opens.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/data_base_reach.jpg?token=GHSAT0AAAAAACKUAAMBASBNWLKDAVMZJEUCZLD6TLQ" title="Advanced Queries">
    <n><figcaption>Advanced Queries (Consultas Avançadas)</figcaption></n>
</p>

In the center of the screen, there is an advanced search window overlaid on the calendar. This window contains multiple form fields to refine the event search in the calendar. The fields include "Month", "Day", "Year", "Time (H:Mm)", "Title", "Description", "Category", and "Status", all of which are empty, awaiting user input. In the bottom right corner of this search window, there is a "Search" button.


If you click on 'Search' (Pesquisar) without entering any information, it will return all the information from the database.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/data_base_reach_2.jpg?token=GHSAT0AAAAAACKUAAMA5GCCIEG6E72PSV5UZLD7AWA" title="Result of an empty search">
    <n><figcaption>Result of an empty search</figcaption></n>
</p>

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/data_base_reach_3.jpg?token=GHSAT0AAAAAACKUAAMAITQT3L2S7ODGOQZKZLD6WNA" title="Comparing with the database">
    <n><figcaption>Comparing with the database</figcaption></n>
</p>

It is possible to double-click on items in the results screen and obtain more details about the activity. Here, I did not proceed with the option to edit the application.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/Imagem16.jpg?token=GHSAT0AAAAAACKUAAMAIBEPIYV26MBTDZJ2ZLD7DBQ" title="Viewing content">
    <n><figcaption>Viewing content. Screen with double-click trigger to view details of the result item.</figcaption></n>
</p>

For example, You can perform other searches, such as appointments scheduled for 22:22.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/Imagem17.jpg?token=GHSAT0AAAAAACKUAAMBGYWJSOMX4NAM7SCGZLD7FNA" title="Screen for searching appointments scheduled for 22:22">
    <n><figcaption>Screen for searching appointments scheduled for 22:22.</figcaption></n>
</p>

You can narrow the search down to January.

<p align="center">
  <img src="https://raw.githubusercontent.com/adrielvasques/agenda_pessoal/main/images/Imagem18.png?token=GHSAT0AAAAAACKUAAMBM7TOWGFXU2YR2VCSZLD7GVQ" title="Screen for searching appointments scheduled for 22:22 in January">
    <n><figcaption>Screen for searching appointments scheduled for 22:22 in January.</figcaption></n>
</p>

## Notes
Despite the application having some flaws, I believe it is quite comprehensive for a v1 version. Another point to highlight is that there could be improvements in terms of screen design and the program's overall flow. Additionally, the code could have been better organized with improved folder structures, code structure, and class naming conventions.


## Feedback
Got some thoughts or suggestions? Don't hesitate to reach out to me at avs@icomp.ufam.edu.br. I'd love to hear from you! 💡

## Ressources
This project is based on: https://github.com/skarabocu/Personal-Agenda
