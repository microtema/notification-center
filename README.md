# notification-center-server
Message Notification Center (Server)

# REST API
Person

@GET    /rest/person will return current login Person/User

Message

@GET    /rest/message/ will return all Messages for current login Person/User
@GET    /rest/message/type/{messageType} will return all Messages filtered by MessageType for current login Person/User

@POST   /rest/message/ @BODY{Message} will update MessageType for current login Person/User

@DELETE   /rest/message will delete all Messages for current login Person/User
@DELETE   /rest/message/{ids} will delete all Messages by given ids for current login Person/User
