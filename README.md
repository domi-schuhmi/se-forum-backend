# SE-FORUM Backend

| SE Service Engineering 259039 (2022W) |
|-|

This project constitutes the backend of the SE-FORUM.

> The corresponding frontend project can be found [here](https://github.com/RingoDev/se-forum-frontend) (access required).

## Configuration

By default, this project uses a local MongoDB docker container, therefore a [MongoDB docker image](https://hub.docker.com/_/mongo) is required. <br/> Once you have a MongoDB docker image, to run it on the appropriate port use: 

```docker run -p 27017:27017 -d mongo``` 

If preferred, the file can also be edited to target a Cloud MongoDB instead by editing the [application.properties](/src/main/resources/application.properties). 

## Run

To run the project, execute the [SeForumBackendApplication.java](src/main/java/SEForum/seforumbackend/SeForumBackendApplication.java).

