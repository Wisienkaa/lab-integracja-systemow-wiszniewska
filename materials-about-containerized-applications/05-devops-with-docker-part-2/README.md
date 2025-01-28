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

docker-compose.yml

```bash
version: '3.7'

services:
  backend:
    build:
      context: ./example-backend
    ports:
      - "8080:8080"
    environment:
      - PORT=8080
      - REQUEST_ORIGIN=https://example.com
      - REDIS_HOST=redis # Upewnij się, że jest to poprawny format
    depends_on:
      - redis

  frontend:
    build:
      context: ./example-frontend
    ports:
      - "3000:3000"
    depends_on:
      - backend

  redis:
    image: "redis:alpine"
    ports:
      - "6379:6379"
```

[venv](ss/3.png)
[Ex 2.4+] Connection to redis initialized, ready to ping pong.

## Exercises 2.5

:::info Exercise 2.5

The project https://github.com/docker-hy/material-applications/tree/main/scaling-exercise is a barely working application. Go ahead and clone it for yourself. The project already includes docker-compose.yml so you can start it by running docker compose up.

The application should be accessible through http://localhost:3000. However it doesn't work well enough and we've added a load balancer for scaling. Your task is to scale the compute containers so that the button in the application turns green.

This exercise was created with Sasu Mäkinen

Please return the used commands for this exercise.
[venv](ss/4.png)

## Exercise 2.6

Let us continue with the example app that we worked with in Exercise 2.4.

Now you should add a database to the example backend.

Use a Postgres database to save messages. For now, there is no need to configure a volume since the official Postgres image sets a default volume for us. Use the Postgres image documentation to your advantage when configuring: https://hub.docker.com/_/postgres/. Especially part Environment Variables is a valuable one.

The backend README should have all the information needed to connect.

There is again a button (and a form!) in the frontend that you can use to ensure your configuration is done right.

Submit the docker-compose.yml

TIPS:

When configuring the database, you might need to destroy the automatically created volumes. Use commands docker volume prune, docker volume ls and docker volume rm to remove unused volumes when testing. Make sure to remove containers that depend on them beforehand.
restart: unless-stopped can help if the Postgres takes a while to get ready
Backend, frontend, redis and a database

### Aktualizacja docker compose yml, dodanie bazy danych

```bash version: '3.7'

services:
  backend:
    build:
      context: ./example-backend
    ports:
      - "8080:8080"
    environment:
      - PORT=8080
      - REQUEST_ORIGIN=https://example.com
      - REDIS_HOST=redis
      - PGHOST=postgres
      - PGUSER=postgres
      - PGPASSWORD=postgres
      - PGDATABASE=messages
      - PGPORT=5432
    depends_on:
      - redis
      - postgres

  frontend:
    build:
      context: ./example-frontend
    ports:
      - "3000:3000"
    depends_on:
      - backend

  redis:
    image: "redis:alpine"
    ports:
      - "6379:6379"

  postgres:
    image: "postgres:15-alpine"
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: messages
    ports:
      - "5432:5432"
    restart: unless-stopped
```

:::
[venv](ss/4.png)

## Exercise 2.7

Postgres image uses a volume by default. Define manually a volume for the database in a convenient location such as in ./database so you should use now a bind mount. The image documentation may help you with the task.

After you have configured the bind mount volume:

Save a few messages through the frontend
Run docker compose down
Run docker compose up and see that the messages are available after refreshing browser
Run docker compose down and delete the volume folder manually
Run docker compose up and the data should be gone
TIP: To save you the trouble of testing all of those steps, just look into the folder before trying the steps. If it's empty after docker compose up then something is wrong.

Submit the docker-compose.yml

The benefit of a bind mount is that since you know exactly where the data is in your file system, it is easy to create backups. If the Docker managed volumes are used, the location of the data in the file system can not be controlled and that makes backups a bit less trivial...

:::

:::tip Tips for making sure the backend connection works

In the next exercise try using your browser to access http://localhost/api/ping and see if it answers pong

It might be Nginx configuration problem. Ensure there is a trailing / on the backend URL as specified under the location /api/ context in the nginx.conf.

### Aktualizacja docker compose yml, dodanie bind mount do PostgreSQL, aby przechowywać dane w folderze ./database.

```bash
version: '3.7'

services:
  backend:
    build:
      context: ./example-backend
    ports:
      - "8080:8080"
    environment:
      - PORT=8080
      - REQUEST_ORIGIN=https://example.com
      - REDIS_HOST=redis
      - PGHOST=postgres
      - PGUSER=postgres
      - PGPASSWORD=postgres
      - PGDATABASE=messages
      - PGPORT=5432
    depends_on:
      - redis
      - postgres

  frontend:
    build:
      context: ./example-frontend
    ports:
      - "3000:3000"
    depends_on:
      - backend

  redis:
    image: "redis:alpine"
    ports:
      - "6379:6379"

  postgres:
    image: "postgres:15-alpine"
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: messages
    ports:
      - "5432:5432"
    volumes:
      - ./database:/var/lib/postgresql/data
    restart: unless-stopped
```

:::

## Exercise 2.8

In this exercise, you shall add Nginx to work as a reverse proxy in front of the example app frontend and backend.

According to Wikipedia a reverse proxy is a type of proxy server that retrieves resources on behalf of a client from one or more servers. These resources are then returned to the client, appearing as if they originated from the reverse proxy server itself.

Backend, frontend, redis, a database and nginx

So in our case, the reverse proxy will be the single point of entry to our application, and the final goal will be to set both the React frontend and the Express backend behind the reverse proxy.

The idea is that a browser makes all requests to http://localhost. If the request has a URL prefix http://localhost/api, Nginx should forward the request to the backend container. All the other requests are directed to the frontend container.

So, at the end, you should see that the frontend is accessible simply by going to http://localhost. All buttons, except the one labeled Exercise 2.8 may have stopped working, do not worry about them, we shall fix that later.

The following file should be set to /etc/nginx/nginx.conf inside the Nginx container. You can use a file bind mount where the contents of the file is the following:

events { worker_connections 1024; }

http {
server {
listen 80;

    location / {
      proxy_pass _frontend-connection-url_;
    }

    # configure here where requests to http://localhost/api/...
    # are forwarded
    location /api/ {
      proxy_set_header Host $host;
      proxy_pass _backend-connection-url_;
    }

}
}
Nginx, backend and frontend should be connected in the same network. See the image above for how the services are connected. You find Nginx-documentation helpful, but remember, the configuration you need is pretty straightforward, if you end up doing complex things, you are most likely doing something wrong.

If and when your app "does not work", remember to have a look in the log, it can be pretty helpful in pinpointing errors:

2_7-proxy-1 | /docker-entrypoint.sh: Launching /docker-entrypoint.d/30-tune-worker-processes.sh
2_7-proxy-1 | /docker-entrypoint.sh: Configuration complete; ready for start up
2_7-proxy-1 | 2023/03/05 09:24:51 [emerg] 1#1: invalid URL prefix in /etc/nginx/nginx.conf:8
2_7-proxy-1 exited with code 1
Submit the docker-compose.yml

### Aktualizacja docker compose yml, dodanie Nginx jako reverse proxy

```bash
version: '3.7'

services:
  backend:
    build:
      context: ./example-backend
    environment:
      - PORT=8080
      - REQUEST_ORIGIN=http://localhost
      - REDIS_HOST=redis
      - PGHOST=postgres
      - PGUSER=postgres
      - PGPASSWORD=postgres
      - PGDATABASE=messages
      - PGPORT=5432
    depends_on:
      - redis
      - postgres

  frontend:
    build:
      context: ./example-frontend
    depends_on:
      - backend

  redis:
    image: "redis:alpine"

  postgres:
    image: "postgres:15-alpine"
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: messages
    volumes:
      - ./database:/var/lib/postgresql/data
    restart: unless-stopped

  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
    depends_on:
      - frontend
      - backend
```

### Zawartość pliku nginx w katalogu glownym

```bash
events { worker_connections 1024; }

http {
  server {
    listen 80;

    location / {
      proxy_pass http://frontend:3000;
    }

    location /api/ {
      proxy_set_header Host $host;
      proxy_pass http://backend:8080;
    }
  }
}
```

:::

## Exercise 2.9

Most of the buttons may have stopped working in the example application. Make sure that every button for exercises works.

Remember to take a peek into the browser's developer consoles again like we did back part 1, remember also this and this.

The buttons of the Nginx exercise and the first button behave differently but you want them to match.

If you had to make any changes explain what you did and where.

Submit the docker-compose.yml and both Dockerfiles.

### poprawki dla docker compose yml

```bash
version: '3.7'

services:
  backend:
    build:
      context: ./example-backend
    environment:
      - PORT=8080
      - REQUEST_ORIGIN=http://localhost
      - REDIS_HOST=redis
      - PGHOST=postgres
      - PGUSER=postgres
      - PGPASSWORD=postgres
      - PGDATABASE=messages
      - PGPORT=5432
    depends_on:
      - redis
      - postgres

  frontend:
    build:
      context: ./example-frontend
    environment:
      - REACT_APP_BACKEND_URL=http://localhost/api
    depends_on:
      - backend

  redis:
    image: "redis:alpine"

  postgres:
    image: "postgres:15-alpine"
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: messages
    volumes:
      - ./database:/var/lib/postgresql/data
    restart: unless-stopped

  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
    depends_on:
      - frontend
      - backend
```

### poprawienie konfiguracji Nginx

```bash
    location /api/ {
      proxy_set_header Host $host;
      proxy_set_header X-Forwarded-For $remote_addr;
      proxy_set_header X-Forwarded-Proto $scheme;
      proxy_pass http://backend:8080;
    }
```

:::

## Exercise 2.11

Select some of your own development projects and start utilizing containers in the development environment.

Explain what you have done. It can be anything, e.g., support for docker-compose.yml to have services (such as databases) containerized or even a fully blown containerized development environment.

### Konteneryzacja projektu Django z PostgreSQL i Redisem

docker compose yml

```bash
version: '3.9'

services:
  web:
    build: .
    command: python manage.py runserver 0.0.0.0:8000
    volumes:
      - .:/app
    ports:
      - "8000:8000"
    environment:
      - DATABASE_URL=postgres://postgres:password@db:5432/mydatabase
      - REDIS_URL=redis://redis:6379/0
    depends_on:
      - db
      - redis

  db:
    image: postgres:15-alpine
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
      POSTGRES_DB: mydatabase
    volumes:
      - ./data:/var/lib/postgresql/data

  redis:
    image: redis:alpine
```

Dockerfile

```bash
FROM python:3.10-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .

EXPOSE 8000

CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]
```

[venv](ss/5.png)
[venv](ss/6.png)
