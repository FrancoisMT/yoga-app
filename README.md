# Yoga Test Application
Yoga Application is a software that allows individuals to participate to yoga classes, while enabling the admin to plan sessions.
This application is a working support for tests (unit, integration and end to end).

## Setting up the Database
Verify the presence of a MySQL database instance installed and set up correctly on your system
The SQL script responsible for creating the tables is located in the folder: src/main/ressources/scripts/script.sql

### Environment Variables for Database
To run this project, you must configure the following environment variables:
- `DATASOURCE_URL`: The database connection URL.   (ex : jdbc:mysql://localhost:3306/{yoga-db}?allowPublicKeyRetrieval=true
- `DATASOURCE_USERNAME`: The username for the database.  (ex: root)
- `DATASOURCE_PASSWORD`: The password for the database.  (ex : my-db-pwd)

## Installing the Application
1. Make sure you've installed all needed dependencies: Java, Node.js, Maven.
2. Clone this repo to your local environment.
3. Move to the back-end project folder and execute `mvn clean install` to fetch dependencies and build the project.
4. Go to the front-end project folder and use `npm install` to fetch front-end dependencies.
   
## Launching the Application
1. In the back-end project folder, run `mvn spring-boot:run` to initiate the back-end server.
2. In the front-end project folder, use `npm run start` to start the front-end interface.

## Executing Tests

### Front-end Unit and Integration Testing (Jest)
1. In the front-end project folder, execute `npm run test` to conduct front-end unit tests via Jest.
2. For the coverage report, utilize `npm run test:coverage`.
3. A `index.html` report will be created in the `front/coverage/jest/index.html` path.

### End-to-End Testing (Cypress)
1. In the front-end project area, execute `npm run e2e` for conducting end-to-end testing using Cypress.
2. For the coverage overview, utilize `npm run e2e:coverage`.
3. The `index.html` report will be found in the `front/coverage/lcov-report/index.html` folder.

### Back-end Unit and Integration Testing (JUnit and Mockito)
1. In the back-end project area, run `mvn clean test` to perform back-end unit and integration tests via JUnit and Mockito.1
2. A coverage report will be accessible in the `back/target/site/jacoco/index.html` folder.

## Technologies Utilized in the Development of the Yoga App :
- NodeJS v16
- Angular CLI v14
- Java 11
- SpringBoot 2.6.1
- Cypress
- JUnit
- Mockito
- Jest

