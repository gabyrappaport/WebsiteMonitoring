# Website Availability and Performance Monitoring
- [Intro](#intro)
- [Requirement](#requirements)
- [Configuration](#configuration)
- [Usage](#usage)
- [Folder structure](#folder-structure)

Intro
-----

This project is my work for the website availability and performance monitoring provided by **Datadog**
You can find : 
- Complete project code in Java
- A pdf detailing the choices I made and the architecture of the project 
- A .jar file that launch the application in the console

Requirements
------------

To run this project, you must have a recent version of:
- **Java**
- **MySQL**

Further more, the project has been throughly tested on **Mac-os only**, but should work on **Linux**.

Configuration
-------------

You need to configure the database as follow : 

- Create the Database
``` 
CREATE DATABASE bdd_monitoring DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
``` 
- Create the user 
``` 
CREATE USER 'gabrielle'@'localhost' IDENTIFIED BY 'olivia';
``` 
- Give the priviledges to the user
``` 
GRANT ALL ON bdd_monitoring.* TO 'gabrielle'@'localhost' IDENTIFIED BY 'olivia';
FLUSH PRIVILEGES;
``` 
- Create the databse for Website : 
``` 
CREATE TABLE  bdd_monitoring.Website(
 idWebsite INT( 11 ) NOT NULL AUTO_INCREMENT ,
 url VARCHAR( 255 ) NOT NULL ,
checkInterval INT( 11 ) NOT NULL ,
 date DATETIME NOT NULL ,
 PRIMARY KEY ( idWebsite ),
 UNIQUE ( url )
) ENGINE = INNODB;
``` 

- Create the database for the Checks : 
``` 
CREATE TABLE  bdd_monitoring.CheckWebsite(
idCheck INT( 11 ) NOT NULL AUTO_INCREMENT ,
httpStatusCode INT( 11 ) NOT NULL ,
responseTime BIGINT( 20 ) NOT NULL ,
idWebsite INT( 11 ) NOT NULL ,
date DATETIME NOT NULL ,
PRIMARY KEY ( idCheck)
) ENGINE = INNODB;
``` 

Usage
-------

The user can type different commands in the console application : 
- runMonitoring <> : run the monitoring 
- addWebsite <url, checkInterval> : add a website to the database
- deleteWebsite <url> : delete a website from the database
- updateCheckInterval <url, checkInterval> : update checkInterval of a website 
- allWebsite <> : print all websites in the database
- help <> : print all the available commands

When the user run the monitoring, a graph user interface appears.

Folder structure
----------------
```
├── README.md                       <- The Readme !
├── Projet Repord.pdf               <- The report
├── WebsiteMonitoring.jar           <- Executable jar for the project 
├── doc                             <- JavaDoc
├── resouces                        <- resources 
│   ├── dao.properties              <- properties for the dao : url, driver, username, password
│   ├── jfreechart-1.0.19-demo.jar  <- useful to prompt the charts in the GUI
├── src                             <- complete code
│   ├── classes                     <- Objects
│   │   ├── Check.java
│   │   ├── Website.java
│   ├── clui                       <- Command Line User Interface
│   │   ├── CommandLineUserInterface.java
│   │   ├── UserCommand.java
│   ├── dao                        <- Data Access Object 
│   │   ├── CheckDao.java
│   │   ├── CheckDaoImpl.java
│   │   ├── DAOConfigurationException.java
│   │   ├── DAOException.java
│   │   ├── DAOFactory.java
│   │   ├── DAOUtility.java
│   │   ├── WebsiteDao.java
│   │   ├── WebsiteDaoImpl.java
│   ├── gui                         <- Graphical User Interface
│   │   ├── MonitorChart.java
│   │   ├── MonitorChartPanel.java
│   │   ├── MonitorFrame.java
│   │   ├── MonitorTableChart.java
│   │   ├── MonitorTableChartPanel.java
│   │   ├── PeriodPanel.java
│   │   ├── WebsitePanel.java
│   │   ├── WebsiteTab.java
│   ├── monitor
│   │   ├── MonitorAlert.java
│   │   ├── MonitorAlertTest.java
│   │   ├── MonitorCheck.java
│   │   ├── MonitorStatistics.java 
│   │   ├── MonitorWebsite.java
```
