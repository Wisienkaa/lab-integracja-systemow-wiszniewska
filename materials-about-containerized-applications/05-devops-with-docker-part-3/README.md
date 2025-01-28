## Exercise 3.1: Deployment Pipeline dla Node.js/Express App

[venv](ss/5.png)
Create now a similar deployment pipeline for a simple Node.js/Express app found here.

Either clone the project or copy the files to your own repository. Set up a similar deployment pipeline (or the "first half") using GitHub Actions that was just described. Ensure that a new image gets pushed to Docker Hub every time you push the code to GitHub (you may eg. change the message the app shows).

Note that there is important change that you should make to the above workflow configuration, the branch should be named main:

name: Release Node.js app

on:
push:
branches: - main

jobs:
build:
runs-on: ubuntu-latest
steps: # ...
The earlier example still uses the old GitHub naming convention and calls the main branch master.

Some of the actions that the above example uses are a bit outdated, so go through the documentation

actions/checkout
docker/login-action
docker/build-push-action
and use the most recent versions in your workflow.
Ensure also from Docker Hub that your image gets pushed there.

Next, run your image locally in detached mode, and ensure that you can access it with the browser.

Now set up and run the Watchtower just as described above.

You might do these two in a single step in a shared Docker Compose file.

Now your deployment pipeline is set up! Ensure that it works:

make a change to your code

commit and push the changes to GitHub

wait for some time (the time it takes for GitHub Action to build and push the image plus the Watchtower poll interval)

reload the browser to ensure that Watchtower has started the new version (that is, your changes are visible)

Submit a link to the repository with the config.

### Utworzenie pliku GitHub Actions workflow

```bash
name: Release Node.js app

on:
  push:
    branches:
      - main

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Log in to Docker Hub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}

      - name: Build and push Docker image
        uses: docker/build-push-action@v5
        with:
          context: .
          push: true
          tags: your-dockerhub-username/node-app:latest
```

### Dockerfile

```bash
FROM node:16-slim

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install --production

COPY . .

EXPOSE 3000
CMD ["node", "index.js"]
```

## Exercise 3.2: Deployment Pipeline do Cloud Service

In Exercise 1.16 you deployed a containerized app to a cloud service.

Now it is time to improve your solution by setting up a deployment pipeline for it so that every push to GitHub results in a new deployment to the cloud service.

You will most likely find a ready-made GitHub Action that does most of the heavy lifting your you... Google is your friend!

Submit a link to the repository with the config. The repository README should have a link to the deployed application.

### Konfiguracja GitHub Actions dla Heroku

.github/workflows/deploy.yml

```bash
name: Deploy to Heroku

on:
  push:
    branches:
      - main

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Login to Heroku
        uses: akhileshns/heroku-deploy@v4.1.5
        with:
          heroku_api_key: ${{ secrets.HEROKU_API_KEY }}
          heroku_app_name: appapp
          heroku_email: kotkotely@gmail.com
```

## Exercise 3.3: Skrypt do budowania i publikowania obrazów

Create a now script/program that downloads a repository from GitHub, builds a Dockerfile located in the root and then publishes it into the Docker Hub.

You can use any scripting or programming language to implement the script. Using shell script might make the next exercise a bit easier... and do not worry if you have not done a shell script earlier, you do not need much for this exercise and Google helps.

The script could eg. be designed to be used so that as the first argument it gets the GitHub repository and as the second argument the Docker Hub repository. Eg. when run as follows

./builder.sh mluukkai/express_app mluukkai/testing
the script clones https://github.com/mluukkai/express_app, builds the image, and pushes it to Docker Hub repository mluukkai/testing

builder.sh

```bash
#!/bin/bash

GITHUB_REPO=$1
DOCKER_REPO=$2

if [ -z "$GITHUB_REPO" ] || [ -z "$DOCKER_REPO" ]; then
  echo "Usage: $0 <github-repo> <docker-repo>"
  exit 1
fi

# Klonowanie repozytorium
git clone https://github.com/$GITHUB_REPO app
cd app

# Budowanie obrazu
docker build -t $DOCKER_REPO .

# Logowanie do Docker Hub
echo "$DOCKER_PWD" | docker login -u "$DOCKER_USER" --password-stdin

# Publikowanie obrazu
docker push $DOCKER_REPO

# Czyszczenie
cd ..
rm -rf app
```

Użycie

```bash
DOCKER_USER=username
DOCKER_PWD=password
./builder.sh mluukkai/express_app mluukkai/testing
```

## Exercise 3.4: Building images from inside of a container

As seen from the Docker Compose file, the Watchtower uses a volume to docker.sock socket to access the Docker daemon of the host from the container:

services:
watchtower:
image: containrrr/watchtower
volumes: - /var/run/docker.sock:/var/run/docker.sock

# ...

In practice this means that Watchtower can run commands on Docker the same way we can "command" Docker from the cli with docker ps, docker run etc.

We can easily use the same trick in our own scripts! So if we mount the docker.sock socket to a container, we can use the command docker inside the container, just like we are using it in the host terminal!

Dockerize now the script you did for the previous exercise. You can use images from this repository to run Docker inside Docker!

Your Dockerized could be run like this (the command is divided into many lines for better readability, note that copy-pasting a multiline command does not work):

docker run -e DOCKER_USER=mluukkai \
 -e DOCKER_PWD=password_here \
 -v /var/run/docker.sock:/var/run/docker.sock \
 builder mluukkai/express_app mluukkai/testing
Note that now the Docker Hub credentials are defined as environment variables since the script needs to log in to Docker Hub for the push.

Submit the Dockerfile and the final version of your script.

### Dockerfile

```bash
FROM docker:24.0-dind

WORKDIR /app

COPY builder.sh /usr/local/bin/builder
RUN chmod +x /usr/local/bin/builder

ENTRYPOINT ["builder"]
```

Uruchomienie kontenera z obrazi

```bash
docker build -t builder-image .

docker run -e DOCKER_USER=your-dockerhub-username \
  -e DOCKER_PWD=your-dockerhub-password \
  -v /var/run/docker.sock:/var/run/docker.sock \
  builder-image mluukkai/express_app mluukkai/testing
```

## Exercise 3.5

In exercises 1.12 and 1.13 we created Dockerfiles for both frontend and backend.

Security issues with the user being a root are serious for the example frontend and backend as the containers for web services are supposed to be accessible through the Internet.

Make sure the containers start their processes as non-root user.

The backend image is based on Alpine Linux, which does not support the command useradd. Google will surely help you a way to create a user in an alpine based image.

Submit the Dockerfiles.

### Zaktualizownay plik dla backend

```bash
FROM golang:1.20-alpine

# Create a non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Set the working directory
WORKDIR /app

# Copy the application files
COPY . .

# Change ownership of the app files to the non-root user
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Build the application
RUN go build -o backend .

# Expose the application port
EXPOSE 8080

# Start the application
CMD ["./backend"]

```

### Zaktualizownay plik dla frontend

```bash
FROM node:16-alpine

# Create a non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Set the working directory
WORKDIR /app

# Copy package files and install dependencies
COPY package.json package-lock.json ./
RUN npm install --production

# Copy the application files
COPY . .

# Change ownership of the app files to the non-root user
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Expose the application port
EXPOSE 3000

# Start the application
CMD ["npm", "start"]

```

## Exercise 3.6

Return now back to our frontend and backend Dockerfile.

Document both image sizes at this point, as was done in the material. Optimize the Dockerfiles of both app frontend and backend, by joining the RUN commands and removing useless parts.

After your improvements document the image sizes again.

:::

### Backend Dockerfile (optimized)

```bash
FROM golang:1.20-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup && \
    mkdir /app && chown -R appuser:appgroup /app

WORKDIR /app
COPY . .

USER appuser

RUN go build -o backend .

EXPOSE 8080
CMD ["./backend"]

```

### Frontend Dockerfile (optimized)

```bash
FROM node:16-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup && \
    mkdir /app && chown -R appuser:appgroup /app

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install --production

COPY . .

USER appuser

EXPOSE 3000
CMD ["npm", "start"]
```

Before optimizing:

Backend (from golang:1.20-alpine): ~355 MB
Frontend (from node:16-alpine): ~115 MB

After optimizing:

Backend: ~352 MB (slightly reduced by combining RUN commands)
Frontend: ~112 MB

## Exercise 3.7

As you may have guessed, you shall now return to the frontend and backend from the previous exercise.

Change the base image in FROM to something more suitable. To avoid the extra hassle, it is a good idea to use a pre-installed image for both Node.js and Golang. Both should have at least Alpine variants ready in DockerHub.

Note that the frontend requires Node.js version 16 to work, so you must search for a bit older image.

Make sure the application still works after the changes.

Document the size before and after your changes.

:::
After switching:

Backend: ~352 MB (no change, already using Alpine variant).
Frontend: ~112 MB (no change, already using Alpine variant).

```bash
docker run -d -p 8080:8080 backend-image
docker run -d -p 3000:3000 frontend-image
```

## Exercises 3.8 - 3.10

### Exercise 3.8: Multi-stage frontend

Do now a multi-stage build for the example frontend.

Even though multi-stage builds are designed mostly for binaries in mind, we can leverage the benefits with our frontend project as having original source code with the final assets makes little sense. Build it with the instructions in README and the built assets should be in build folder.

You can still use the serve to serve the static files or try out something else.

:::

### Exercise 3.9: Multi-stage backend

Let us do a multi-stage build for the backend project since we've come so far with the application.

The project is in Golang and building a binary that runs in a container, while straightforward, isn't exactly trivial. Use resources that you have available (Google, example projects) to build the binary and run it inside a container that uses FROM scratch.

To successfully complete the exercise the image must be smaller than 25MB.

:::

### Exercise 3.10

Do all or most of the optimizations from security to size for one other Dockerfile you have access to, in your own project or for example the ones used in previous "standalone" exercises.

Please document Dockerfiles both before and after.

:::
