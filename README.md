# notification-center-server
Message Notification Center (Server)

### REST API
Person

@GET    /rest/person will return current login Person/User

Message

@GET    /rest/message/ will return all Messages for current login Person/User
@GET    /rest/message/type/{messageType} will return all Messages filtered by MessageType for current login Person/User

@POST   /rest/message/ @BODY{Message} will update MessageType for current login Person/User

@DELETE   /rest/message will delete all Messages for current login Person/User
@DELETE   /rest/message/{ids} will delete all Messages by given ids for current login Person/User

#Describe Message Notification Project

### Install

* **Java**  ( SDK 1.7)
* **Apache Maven**  ( 3.2.3 )
* **SQL Server**  ( MS SQL Express 2012)
* **GIT**  ( 2.7.1)
* **SourceTree**  ( 1.7.0)
* **IDE**  ( 15.0.3)


### Development

* **mvn clean test**  ( run JUnit test)
* **mvn clean verify**  ( run JUnit and IT test)
* **mvn clean install**  ( compile, test, build and install to maven repository)
* **mvn clean install -Pintegration-test,production**  ( compile, test, build and install to maven repository with given profiles)


We'll be building a simple but realistic Message Notification Center that you can read and edit, a basic version of the realtime Messages offered by RESTServer.

We'll provide:

* A ORM of Messages
* A form to import Messages via Apache Camel Route
* JPA Hooks to provide SSE Broadcast for frontend
* Junit Test and IT
* CDD and TDD

It'll also have a few neat features:

* **Live loading:** Messages will be imported from FTP/Remote-Folder with Camel Route.
* **Data converting, validating and persisting:** Convert DTO to Entity
* **Live SSE broadcasting to client:** CRUD hooks will broadcast SSE event.


### Running a server

In order to start this tutorial, we're going to require a running JBoss server. This will serve purely as an API endpoint which we'll use for importing *.xml files and server through REST endpoint.
