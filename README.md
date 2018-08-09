
# Spring Security Boot App [![Build Status](https://travis-ci.org/geraldoms/spring-security-boot.svg?branch=master)](https://travis-ci.org/geraldoms/spring-security-boot)  
=======================

This is a basic example of web application using Spring Security.
The authentication and authorization are provided by Spring Security 5. The users 
are stored in the H2 (in-memory) database. For the pages the Thymeleaf and Bootstrap were used.
The database schema is basically a ManyToMany relationship between User and Role table.

## Requirements
* JDK 8 or later
* Maven 3.2+

## Installation 
`$ mvn package`

## Usage 

After running the command above (in the installation section), a jar file should be in the `target` folder. Run the command below using that jar file.   
 
`jar -jar target/spring-security-boot-0.0.1-SNAPSHOT.jar`

## Running the tests

For the unit and integration test, run the command below: 

`$ mvn test`

## Usage example 

When the application starts you can access it at `http://localhost:8080/home`. 
Since you are not authenticated, in the first time, it will redirect you to the login page in 'http://localhost:8080/login', as shown in the gif below.

For this example, I'm using a common user with a USER role. This user can access the User Page, 
but cannot access the Admin Page (which requires the ADMIN role). All authenticated users can access the Home Page.  

![spring-security-boot-app](https://user-images.githubusercontent.com/13106549/43911130-a4018230-9bcc-11e8-9116-d6fc7d5261a9.gif)


