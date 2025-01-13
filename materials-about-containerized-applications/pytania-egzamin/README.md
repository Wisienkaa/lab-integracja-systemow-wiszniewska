# VI. Pytania, które mogą się pojawić na zaliczeniu (egzaminie)

### 1. Utwórz plik z obrazem Dockerfile, w którym z hosta do kontenera kopiowany będzie folder code (zawiera np. jeden skrypt w języku Python 🐍) i zbuduj go:

uruchom ww. skrypt wewnątrz kontenera.

Example Dockerfile

```bash
# official Python image
FROM python:3.9-slim

WORKDIR /app

# Copy the code folder from the host to the container
COPY code /app/code

# Set the default command to run the Python script
CMD ["python", "/app/code/script.py"]
```

Directory

```bash
.
├── Dockerfile
├── code/
    ├── script.py
    ├── requirements.txt (optional, if your script needs external dependencies)
```

Build it and run it

```bash
docker build -t python-script-container .
docker run --rm python-script-container
```

### 2. Skopiuj wybrany plik tekstowy z hosta (swojego komputera) do kontenera Dockerowego.

#### Option 1: During Docker Build

Dockerfile

```bash
FROM ubuntu:latest

WORKDIR /app

COPY myfile.txt /app/myfile.txt

CMD ["cat", "/app/myfile.txt"]
```

Place the myfile.txt in the same directory as Dockerfile
Build

```bash
docker build -t textfile-container .
```

Run

```bash
docker run --rm textfile-container
```

#### Option 2: Using docker cp After the Container is Running

Start container

```bash
docker compose up -d
```

Copy

```bash
docker cp /path/to/myfile.txt simple-web-service:/app/myfile.txt
```

Check

```bash
docker exec -it simple-web-service bash
ls /app
cat /app/myfile.txt
```

### 3. Skopiuj wybrany plik tekstowy z kontenera Dockerowego do hosta (swojego komputera).

```bash
docker cp <container_name>:<path_in_container> <destination_on_host>
```

### 4. Pokaż działanie komend ENTRYPOINT i CMD w wybranym projekcie.

#### ENTRYPOINT:

Specifies the main command that will always be executed when the container starts. It’s typically used for defining the core functionality of the container.

#### CMD:

Provides default arguments or commands for the ENTRYPOINT. If ENTRYPOINT is not defined, CMD acts as the default command.
File structure

```bash
.
├── Dockerfile
├── script.sh

```

sript.sh

```bash
#!/bin/bash
chmod +x script.sh
echo "Running the script with arguments: $@"
```

Simple Dockerfile

```bash
FROM ubuntu:latest

WORKDIR /app

COPY script.sh /app/script.sh

RUN chmod +x /app/script.sh

ENTRYPOINT ["/app/script.sh"]

CMD ["default_arg"]
```

Build the docker image

```bash
docker build -t entrypoint-cmd-example .
```

Run the Container Without Arguments:

```bash
docker run --rm entrypoint-cmd-example
```

Run the Container With Custom Arguments:

```bash
docker run --rm entrypoint-cmd-example custom_arg1 custom_arg2
```

ENTRYPOINT ensures that script.sh is always executed when the container runs.
CMD provides default arguments, which can be overridden by passing custom arguments during runtime.

### 5. Pokaż działanie usługi bazodanowej z wykorzystaniem docker-compose.

Set up a database service using docker-compose. PostgreSQL database and a simple admin tool (e.g., pgAdmin)
docker-compose.yml

```bash
services:
  db:
    image: postgres:15
    container_name: postgres_db
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: secret
      POSTGRES_DB: example_db
    ports:
      - "5432:5432"
    volumes:
      - db_data:/var/lib/postgresql/data

  admin:
    image: dpage/pgadmin4
    container_name: pgadmin
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@example.com
      PGADMIN_DEFAULT_PASSWORD: admin
    ports:
      - "8080:80"
    depends_on:
      - db

volumes:
  db_data:
```

#### db Service:

Uses the official postgres image.
Exposes port 5432 on the host for database connections.
Environment variables:
POSTGRES_USER: Username for the database.
POSTGRES_PASSWORD: Password for the database.
POSTGRES_DB: Name of the default database to create.

#### admin Service:

Uses the official pgAdmin 4 image.
Exposes port 8080 on the host, allowing access via a browser.
Environment variables:
PGADMIN_DEFAULT_EMAIL and PGADMIN_DEFAULT_PASSWORD set the admin credentials for pgAdmin.

#### Volumes:

db_data persists the database data so it’s not lost when the container stops.

Run

```bash
docker compose up -d
```

#### Logging into pgAdmin

Open http://localhost:8080 in your browser.
Log in with the credentials:
Email: admin@example.com
Password: admin

### 6. Pokaż działanie komend ADD i COPY i WORKDIR w wybranym projekcie.

```bash
my-app/
│
├── Dockerfile
├── app/
│   ├── main.py
│   └── requirements.txt
├── README.md
```

Dockerfile

```bash
FROM python:3.9-slim

# Ustawienie katalogu roboczego w kontenerze
WORKDIR /app

COPY app/requirements.txt /app/

RUN pip install --no-cache-dir -r requirements.txt

# Skopiowanie całego katalogu 'app' do katalogu roboczego w kontenerze
COPY app/ /app/

# Skopiowanie pliku README do katalogu roboczego
ADD README.md /app/

CMD ["python", "main.py"]
```

Komenda COPY służy do kopiowania plików z lokalnego systemu do kontenera.

Komenda WORKDIR ustawia katalog roboczy w kontenerze. W tym przypadku /app będzie katalogiem roboczym, w którym wykonane będą kolejne komendy.

W tym przypadku, ADD README.md /app/ kopiuje plik README.md z lokalnego systemu do katalogu /app w kontenerze. Działa to podobnie jak COPY, ale jest bardziej elastyczne.

### 7. Pokaż działanie docker compose w swoim projekcie.

docker-compose.yml

```bash
services:
  web:
    build: .
    command: bundle exec rails server -b 0.0.0.0
    volumes:
      - .:/usr/src/app
    environment:
      DATABASE_URL: postgres://redmine:mysecretpassword@db/redmine_development
    ports:
      - "3000:3000"
    depends_on:
      - db
    dns:
      - 8.8.8.8
      - 8.8.4.4

  db:
    image: postgres:latest
    volumes:
      - pgdata:/var/lib/postgresql/data
    environment:
      POSTGRES_DB: redmine_development
      POSTGRES_USER: redmine
      POSTGRES_PASSWORD: mysecretpassword

volumes:
  pgdata:
```

#### web:

Serwis aplikacji Ruby on Rails. Wykorzystuje komendę bundle exec rails server -b 0.0.0.0, aby uruchomić serwer aplikacji. Dzięki wolumenowi - .:/usr/src/app, pliki aplikacji w lokalnym systemie plików są synchronizowane z kontenerem, co pozwala na łatwiejszy rozwój aplikacji.

#### DATABASE_URL

zawiera konfigurację połączenia z bazą danych PostgreSQL.
Aplikacja działa na porcie 3000, który jest mapowany z kontenera na lokalny port 3000 ("3000:3000").
depends_on oznacza, że serwis web będzie czekał na uruchomienie serwisu db przed rozpoczęciem działania.

#### db:

Serwis bazy danych PostgreSQL. Korzysta z oficjalnego obrazu postgres:latest. Dzięki zmiennej środowiskowej POSTGRES_DB, POSTGRES_USER i POSTGRES_PASSWORD ustalam podstawowe dane konfiguracyjne bazy danych.

Dane bazy są przechowywane w wolumenie pgdata, co umożliwia zachowanie ich pomiędzy restartami kontenerów.

#### volumes:

Wolumen pgdata jest używany do przechowywania danych bazy danych PostgreSQL na zewnętrznym dysku w systemie plików, zapewniając trwałość danych.

### 8. Omów na podstawie swojej aplikacji komendy docker inspect i docker logs.

Komenda docker inspect pozwala na uzyskanie szczegółowych informacji o kontenerze, obrazie, wolumenie lub sieci. Można ją wykorzystać do analizy właściwości kontenerów, takich jak konfiguracja sieci, zmienne środowiskowe, ustawienia portów, mapowanie wolumenów i inne dane, które są użyteczne w diagnostyce.

```bash
cd /home/kotko/projects/Wtyczka_SCRUM
docker inspect wtyczka_scrum-web-1
```

Wynikowo mapowanie portów, zmienne środowiskowe, mogę sprawdzić też sieć
Komenda docker logs pozwala na podglądanie logów kontenera, co jest niezwykle przydatne do debugowania aplikacji, sprawdzania błędów lub monitorowania działania kontenera. Logi te zawierają standardowe wyjście i standardowy błąd aplikacji uruchomionej w kontenerze.

```bash
docker logs wtyczka_scrum-web-1
```

Wynikowo informacje o uruchomieniu serwera Ruby on Rails, ewentualne błędy, które wystąpiły podczas startu aplikacji oraz informacje diagnostyczne

### 9. Czym są sieci w Dockerze? Zaprezentuj przykład na bazie swojego projektu.

Sieci umożliwiają komunikację między kontenerami.
Domyślnie, Docker udostępnia kilka typów sieci, w tym:
bridge: Sieć typu mostu, domyślna sieć dla kontenerów, które nie są przypisane do innej sieci. Używana do komunikacji między kontenerami na tym samym hoście.
host: Kontener korzysta z sieci hosta, co oznacza, że wszystkie jego porty są widoczne na porcie hosta.
none: Kontener nie ma żadnej sieci.
overlay: Używana do komunikacji między kontenerami na różnych hostach w przypadku korzystania z Docker Swarm.

```bash

services:
  web:
    build: .
    command: bundle exec rails server -b 0.0.0.0
    volumes:
      - .:/usr/src/app
    environment:
      DATABASE_URL: postgres://redmine:mysecretpassword@db/redmine_development
    ports:
      - "3000:3000"
    depends_on:
      - db
    dns:
      - 8.8.8.8
      - 8.8.4.4

  db:
    image: postgres:latest
    volumes:
      - pgdata:/var/lib/postgresql/data
    environment:
      POSTGRES_DB: redmine_development
      POSTGRES_USER: redmine
      POSTGRES_PASSWORD: mysecretpassword

volumes:
  pgdata:
```

Nie zdefiniowałam sieci w swoim projekcie, dlatego Docker Compose domyślnie tworzy własną sieć, do której wszystkie kontenery w ramach tego projektu są przypisane, jeśli nie zostanie podana żadna inna sieć.
Jest to sieć typu bridge. Natomiast kontener łączy sie z bazą danych za pomocą URL.

Aby dodać do mojego projektu sieć i mieć większą kontrolę mogę zmodyfikować docker-compose.yml

```bash
version: "3.8"

services:
  web:
    build: .
    command: bundle exec rails server -b 0.0.0.0
    volumes:
      - .:/usr/src/app
    environment:
      DATABASE_URL: postgres://redmine:mysecretpassword@db/redmine_development
    ports:
      - "3000:3000"
    depends_on:
      - db
    networks:
      - mynetwork  # okreslenie, ze kontener nalezy do tej sieci

  db:
    image: postgres:latest
    volumes:
      - pgdata:/var/lib/postgresql/data
    environment:
      POSTGRES_DB: redmine_development
      POSTGRES_USER: redmine
      POSTGRES_PASSWORD: mysecretpassword
    networks:
      - mynetwork

networks:
  mynetwork:  # definicja nowej network
    driver: bridge
```

Sprawdzanie sieci kontenerów

```bash
docker network inspect wtyczka_scrum-web-1
```

### 10. Jaka jest różnica między obrazem i kontenerem? Pokaż przykład budowania obrazu (Dockerfile) i uruchamiania na jego podstawie kontenera.

#### Różnica między obrazem a kontenerem w Dockerze

##### Obraz (Image):

Obraz to szablon, który zawiera wszystkie niezbędne pliki, zależności, konfiguracje oraz instrukcje potrzebne do uruchomienia aplikacji lub usługi w kontenerze.
Obraz jest niezmienny; raz zbudowany, nie zmienia się, chyba że zostanie ponownie zbudowany.
Obrazy mogą być używane do uruchamiania wielu kontenerów.
Obraz jest tworzony na podstawie pliku Dockerfile.

#### Kontener (Container):

Kontener to instancja obrazu uruchomiona na hoście. Kontener jest rozproszonym środowiskiem uruchomieniowym, które działa na podstawie obrazu.
Kontener jest zmienny. Można w nim wykonywać różne operacje, a zmiany są wprowadzane w jego systemie plików, dopóki kontener nie zostanie zatrzymany lub usunięty.
Kontener może być uruchomiony, zatrzymany, usunięty, a jego stan jest izolowany od innych kontenerów.

Najpierw buduję obraz

```bash
docker build ubuntu . # w bierzącym katalogu
```

Uruchomienie kontenera za pomocą tego obrazu

```bash
docker run -p 3000:3000 --name rails-container -d ubuntu
```

### 11. Pokaż jak "wejść" do wybranego kontenera.

Utwórz w nim plik tekstowy z dowolnymi danymi. Co zrobić, żeby po zamknięciu kontenera dane z pliku były dostępne po ponownym uruchomieniu kontenera?
Zademonstruj dowolny sposób.

```bash
docker-compose run web bash
echo "test file" > /usr/src/app/plik.txt
cat /usr/src/app/plik.txt
```

Żeby ten plik został i nie usunął się po wyłączeniu kontenera muszę użyc wolumenu
w docker-compose.yml w web:

```bash
volumes:
      - ./app-data:/usr/src/app  # Przypisanie lokalnego katalogu jako wolumenu
```

Uruchamiamy kontener i zatrzymujemy, potem uruchamiamy i sprawdzamy cat ./app-data/plik.txt

### 12. Zbuduj wybrany przez siebie obraz, nadaj mu 'tag' i opublikuj na DockerHubie. Następnie usuń lokalnie ww. obraz i pobierz go z DockerHuba.

```bash
docker build -t wisienkaa66/my-rails-app:v1.0 .
```

-t to dodanie tagu
Wrzucenie obrazu na docker hub

```bash
docker push wisienkaa666/my-rails-app:v1.0
```

Usuwanie obrazu lokalnie

```bash
docker rmi myusername/my-rails-app:v1.0
docker images
```

Pobranie obrazu z docker DockerHuba

```bash
docker pull wisienkaa666/my-rails-app:v1.0
```
