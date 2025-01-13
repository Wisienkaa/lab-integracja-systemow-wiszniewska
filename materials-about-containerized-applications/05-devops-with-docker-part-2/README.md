# Part 2

## Exercise 2.1

:::info Exercise 2.1

Let us now leverage the Docker Compose with the simple webservice that we used in the Exercise 1.3

Without a command devopsdockeruh/simple-web-service will create logs into its /usr/src/app/text.log.

Create a docker-compose.yml file that starts devopsdockeruh/simple-web-service and saves the logs into your filesystem.

Submit the docker-compose.yml, and make sure that it works simply by running docker compose up if the log file exists.

:::

```bash
docker-compose.yml
version: '3.8'

services:
  simple-web-service:
    image: devopsdockeruh/simple-web-service:ubuntu
    container_name: simple-web-service
    volumes:
      - ./logs:/usr/src/app
    command: tail -f /usr/src/app/text.log
```

## Exercises 2.2 - 2.3

:::info Exercise 2.2

Read about how to add the command to docker-compose.yml from the documentation.

The familiar image devopsdockeruh/simple-web-service can be used to start a web service, see the exercise 1.10.

Create a docker-compose.yml, and use it to start the service so that you can use it with your browser.

Submit the docker-compose.yml, and make sure that it works simply by running docker compose up

```bash
services:
  simple-web-service:
    image: devopsdockeruh/simple-web-service:ubuntu
    container_name: simple-web-service
    ports:
      - "8080:8080"
```

:::
[venv](ss/1.png)

## :::caution Mandatory Exercise 2.3

As we saw previously, starting an application with two programs was not trivial and the commands got a bit long.

In the previous part we created Dockerfiles for both frontend and backend of the example application. Next, simplify the usage into one docker-compose.yml.

Configure the backend and frontend from part 1 to work in Docker Compose.

Submit the docker-compose.yml

```bash
services:
  backend:
    build:
      context: ./backend
    container_name: backend
    ports:
      - "5000:5000"
    environment:
      - NODE_ENV=production

  frontend:
    build:
      context: ./frontend
    container_name: frontend
    ports:
      - "3000:3000"
    depends_on:
      - backend
    environment:
      - REACT_APP_BACKEND_URL=http://backend:5000
```

[venv](ss/2.png)
:::

## :::info Exercise 2.4

In this exercise you should expand the configuration done in Exercise 2.3 and set up the example backend to use the key-value database Redis.

Redis is quite often used as a cache to store data so that future requests for data can be served faster.

The backend uses a slow API to fetch some information. You can test the slow API by requesting /ping?redis=true with curl. The frontend app has a button to test this.

So you should improve the performance of the app and configure a Redis container to cache information for the backend. The documentation of the Redis image might contain some useful info.

The backend README should have all the information that is needed for configuring the backend.

When you've correctly configured the button will turn green.

Submit the docker-compose.yml

Backend, frontend and redis

The restart: unless-stopped configuration can help if the Redis takes a while to get ready.

## Exercises 2.5

:::info Exercise 2.5

The project https://github.com/docker-hy/material-applications/tree/main/scaling-exercise is a barely working application. Go ahead and clone it for yourself. The project already includes docker-compose.yml so you can start it by running docker compose up.

The application should be accessible through http://localhost:3000. However it doesn't work well enough and we've added a load balancer for scaling. Your task is to scale the compute containers so that the button in the application turns green.

This exercise was created with Sasu Mäkinen

Please return the used commands for this exercise.
