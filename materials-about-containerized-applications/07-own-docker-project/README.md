# Projekt własny, apka webowa

### Zawartość:

- Dockerfile dla frontend i backend,
- docker-compose do orkiestracji usług,
- wolumeny do przechowywania danych,
- skrypty typu wait-for do zapewnienia zależności między usługami,
- inspekcja kontenerów i logi do debugowania.

### Struktua:

```bash
project/
├── backend/
│   ├── Dockerfile
│   ├── main.go
│   ├── go.mod
│   └── go.sum
├── frontend/
│   ├── Dockerfile
│   ├── package.json
│   ├── package-lock.json
│   └── src/
├── docker-compose.yml
├── wait-for.sh
└── README.md
```

### Backend Dockerfile

```bash
FROM golang:1.20-alpine

# Tworzenie użytkownika nierootowego
RUN addgroup -S appgroup && adduser -S appuser -G appgroup && \
    mkdir /app && chown -R appuser:appgroup /app

WORKDIR /app
COPY . .

# Uruchamianie jako użytkownik nierootowy
USER appuser

RUN go build -o backend .

EXPOSE 8080
CMD ["./backend"]
```

### Frontend Dockerfile

```bash
FROM node:16-alpine

# Tworzenie użytkownika nierootowego
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

### docker-compose.yml

```bash
version: "3.9"

services:
  frontend:
    build:
      context: ./frontend
    ports:
      - "3000:3000"
    depends_on:
      - backend
    volumes:
      - ./frontend:/app
    environment:
      - NODE_ENV=production

  backend:
    build:
      context: ./backend
    ports:
      - "8080:8080"
    depends_on:
      - db
    volumes:
      - ./backend:/app
    environment:
      - DB_HOST=db
      - DB_PORT=5432
      - DB_USER=postgres
      - DB_PASSWORD=secret
      - DB_NAME=mydb

  db:
    image: postgres:15-alpine
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: secret
      POSTGRES_DB: mydb
    volumes:
      - db_data:/var/lib/postgresql/data

volumes:
  db_data:
```

### Skrypt wait-for

```bash
#!/bin/sh

set -e

host="$1"
shift
cmd="$@"

until nc -z "$host" 5432; do
  echo "Waiting for PostgreSQL at $host..."
  sleep 1
done

exec $cmd
```

#### Backend poczeka na uruchomienie bazy danych przed próbą połączenia

W docker-compose.yml skrypt jest używany w sekcji backendu:

```bash
command: ["./wait-for.sh", "db", "--", "./backend"]
```

### wolumeny

```bash
volumes:
  db_data:
```

Wszystkie dane bazy są zapisywane w /var/lib/postgresql/data na hosta

```bash
volumes:
  - ./frontend:/app
  - ./backend:/app
```

Frontend i backend mają zmapowane katalogi kodu źródłowego

#### Wyświetlanie logów

```bash
docker-compose logs -f
```

#### Inspekcja kontenerów

```bash
docker inspect <container_name>
```

#### Frontend: http://localhost:3000.

#### Backend: http://localhost:8080.
