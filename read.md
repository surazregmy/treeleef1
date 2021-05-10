Solutions to Questions Number 1 to 3 are in:
src/main/java/com.example.treeleef1/question/**QualificationTaskFromOneTwoThree**

This is a spring boot application that requires JDBC connection :
Configuration can be managed in database/Database && Create tables by running scripts in mysqlquery/tables.sql

APIS:

Basic Authentication is implemmented. Username : admin Password : admin

**END-POINTS**

**Location:**

GET /api/locations : List all the locations

GET /api/locations/{id}: Get location by ID

POST /api/locations : Create location

PUT /api/locations : Update Location {id: id} must be passed

DELETE /api/locations/{id} : delete location by id

Similar for other Camera => /api/cameras

**Vehicle**:

GET /api/vehicles : List all the vehicles

GET /api/vehicles/{id}: Get vehicle by ID

POST /api/vehicles : Create Vehicle {location: [{id:id}] } if passed sets the movement of vehicle to locations during
creation.

GET /{id}/detectMovement/{locationId}: detects the vehicle movement in location

GET /api/vehicles/{id}/movementReport: Creates PDF report. Send and download the report to .pdf extension from POSTMAN
to view.

contact suraz.regmy@gmail.com if there's error on running the program! 








