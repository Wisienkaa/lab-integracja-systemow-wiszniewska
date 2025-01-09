1. Uruchamianie pierwszego kontenera

```bash
docker run ubuntu ls -l
```

![venv](ss/1.png)

```bash
docker run ubunru uname -a
```

![venv](ss/2.png)

```bash
docker run --interactive --tty ubuntu bash
```

![venv](ss/3.png)
Wnętrze kontenera

```bash
root@58826c33c71d:/# echo "skni" > skni.txt
cat skni.txt
```

![venv](ss/4.png)

```bash
C:\>docker container ls -a
```

![venv](ss/5.png)
Podgląd na wszystkie kontenery

```bash
C:\>docker start 58826c
C:\>docker ps
```

![venv](ss/6.png)
Uruchomienie kontenera

```bash
C:\>docker exec 58826 cat skni.txt
```

![venv](ss/7.png)
Wykonanie komendy za pomocą metody exec w kontenerze

2. Obrazy w dockerze
   Struktura działania obrazu i kontenera:
   ![venv](ss/8.png)
   Możemy współdzielić warsty (container layer) pomiędzy różnymi obrazami. Docker zapewnia oszczędność miejsca, nie nadpisuje plików, ponieważ zawiera warstwe, która ma informacje o plikach, które zostały zmienione.

Tworzenie nowej warstwy obrazu:

```bash
C:\>docker commit 588 skni_img
```

![venv](ss/9.png)

Sprawdzenie czy obraz został utworzony

```bash
docker image ls
```

![venv](ss/10.png)

```bash
C:\>docker history skni_img
```

Wszystkie warstwy jakie składają się na ten obraz

![venv](ss/11.png)

```bash
C:\>docker run -it skni_img bash
```

Postawienie nowego kontenera na podstawie pliku skni_img

```bash
root@8e98c4223dca:/# apt update && apt install vim
```

Instalowanie programu w kontenerze
![venv](ss/13.png)

Stworzenie nowego kontenera na podstawie poprzedniego z zainstalowanym vim
![venv](ss/14.png)

3. Docker Hub - repozytorium publicznych obrazów dockerowych
   ![venv](ss/15.png)

Pobranie systemu postgres (systemu baz danych)

```bash
C:\>docker pull postgres
```

![venv](ss/16.png)

```bash
docker push skni_img
```

Aby wykonać powyższą komendę trzeba być zalogowanym na docker.hub. Jest to wrzucanie własnych obrazów na publiczne repozytorium.

```bash
docker login
```

4. Kopiowanie plików i ich edycja

```bash
C:\>docker cp eager_buck:/skni.txt .
```

Kopiowanie plisku z kontenera do dysku C
![venv](ss/17.png)

```bash
C:\>docker cp skni.txt eager_buck:/
```

Przesłanie pliku do kontenera
![venv](ss/188.png)

5. Dockerfile - tagowanie

```bash
docker build --tag mojvim .

docker --tag mojvim:latest mojvim:2.0 .

docker --tag mojvim:ubuntu19 .
```

docker build --tag mojvim .
Opis: Ta komenda buduje obraz Docker z bieżącego katalogu (.) i nadaje mu tag mojvim.
Szczegóły: Proces budowania obrazu wykorzystuje plik Dockerfile znajdujący się w bieżącym katalogu. Po zakończeniu budowy obraz jest identyfikowany przez tag mojvim.

docker --tag mojvim:latest mojvim:2.0 .
Opis: Ta komenda buduje obraz Docker z bieżącego katalogu (.) i nadaje mu dwa tagi: mojvim:latest i mojvim:2.0.
Szczegóły: Podczas budowania obrazu z pliku Dockerfile w bieżącym katalogu, obraz jest oznaczany jako mojvim:latest oraz mojvim:2.0, co pozwala na łatwe zarządzanie różnymi wersjami obrazu.

docker --tag mojvim:ubuntu19 .
Opis: Ta komenda buduje obraz Docker z bieżącego katalogu (.) i nadaje mu tag mojvim:ubuntu19.
Szczegóły: Podobnie jak w poprzednich przypadkach, obraz jest tworzony z pliku Dockerfile w bieżącym katalogu i oznaczany jako mojvim:ubuntu19, co sugeruje, że obraz może być oparty na Ubuntu 19.

6. Build Context

```bash
docker build .

echo "asd" > skni.txt

docker build -f .. /Dockerfile .


```

docker build .
Opis:Docker do zbudowania obrazu potrzebuje kontekstu, czyli jest to katalog do którego będziemy się odnosić w Dockerfile

docker build -f .. /Dockerfile .
Opis:Czerpie kontekst z innego katalogu w podanej scieżce

7. Konteneryzacja aplikacji konsolowej i webowej

Konteneryzacja aplikacji konsolowej polega na umieszczeniu aplikacji działającej w trybie tekstowym (CLI) w kontenerze. Pozwala to na uruchamianie jej w spójny sposób na różnych maszynach.

```bash
# Wybierz obraz bazowy z Pythonem
FROM python:3.8-slim

# Skopiuj pliki aplikacji do katalogu /app w kontenerze
COPY . /app

# Ustaw katalog roboczy
WORKDIR /app

# Zainstaluj zależności
RUN pip install -r requirements.txt

# Ustal domyślną komendę uruchamiania aplikacji
CMD ["python", "app.py"]
```

Konteneryzacja aplikacji webowej polega na umieszczeniu aplikacji serwerowej (np. webowej) w kontenerze. Dzięki temu aplikacja może być łatwo wdrożona i uruchomiona w różnych środowiskach.

8. Polecenia COPY, ADD i WORKDIR
   Polecenie ADD kopiuje pliki i katalogi z lokalnego systemu plików lub zdalnych URL do systemu plików kontenera.

```bash
ADD <źródło>... <katalog docelowy>
```

Polecenie COPY kopiuje pliki i katalogi z lokalnego systemu plików do systemu plików kontenera. Jest bardziej restrykcyjne niż ADD i nie obsługuje zdalnych URL ani automatycznego rozpakowywania archiwów tar.

```bash
COPY <źródło>... <katalog docelowy>
```

Polecenie WORKDIR ustawia katalog roboczy dla kolejnych instrukcji w Dockerfile oraz dla domyślnego katalogu roboczego po uruchomieniu kontenera.

```bash
WORKDIR /app
COPY moje_pliki .  # Kopiuje pliki do /app
RUN ls -l          # Wykonuje polecenie `ls -l` w katalogu /app
```

9. EntryPoint a CMD
   CMD: formy shell i exec
   Polecenie CMD określa domyślną komendę, która ma być uruchomiona, gdy kontener startuje. Występuje w dwóch formach:

Shell form (forma powłoki):

Komenda jest uruchamiana przez powłokę (/bin/sh -c na Linuxie).

```bash
CMD echo "Hello, World!"
/bin/sh -c "echo Hello, World!"
```

Exec form (forma exec):

Komenda jest uruchamiana bezpośrednio jako proces.

```bash
CMD ["echo", "Hello, World!"]
echo "Hello, World!"
```

Komentarze w Dockerfile są oznaczane znakiem #

CMD vs docker run
CMD definiuje domyślną komendę uruchamianą podczas startu kontenera.
docker run pozwala na nadpisanie komendy zdefiniowanej przez CMD.
Nadpisanie komendy:

```bash
docker run my_python_app python other_script.py
```

ENTRYPOINT definiuje komendę, która będzie uruchomiona i nie może być nadpisana przez docker run, chyba że użyje się flagi --entrypoint.
Przykład Dockerfile:

```bash
FROM python:3.8-slim
WORKDIR /app
COPY . .
RUN pip install -r requirements.txt
ENTRYPOINT ["python"]
CMD ["app.py"]


docker run my_python_app
```

Współpraca ENTRYPOINT i CMD
ENTRYPOINT i CMD mogą być używane razem. CMD dostarcza domyślne argumenty dla ENTRYPOINT.
Dockerfile:

```bash
FROM ubuntu
ENTRYPOINT ["top", "-b"]
CMD ["-c"]
```

Nadpisywanie ENTRYPOINT w docker run
ENTRYPOINT może być nadpisane przy użyciu flagi --entrypoint podczas uruchamiania kontenera.
Dockerfile:

```bash
FROM python:3.8-slim
WORKDIR /app
COPY . .
RUN pip install -r requirements.txt
ENTRYPOINT ["python"]
CMD ["app.py"]
```

Uruchomienie kontenera z nadpisanym ENTRYPOINT:

```bash
docker run --entrypoint /bin/bash my_python_app
```

10. Volumes zarządzane przez dockera
    Są to wirtualne dyski, a tworzy się je tak:

```bash
docker volume create moj-volume
docker volume ls
docker volume rm moj-volume
```

Dockerfile:

```bash
FROM ubuntu
WORKDIR /katalog
CMD ls -al && echo "test" > file.txt && ls -al
```

Zbudowanie obrazu:

```bash
docker build -f vol.Dockerfile -t vol vol_test
docker run vol_test
```

Używamy Volume by przechowywać dane pomiędzy różnymi kontenerami:
Używamy volumenu i podpiąc go wewnątrz kontenera pod /katalog

```bash
docker run --volume moj-volume:/katalog
```

Volumeny są niezależne od kontenerów.

```bash
docker run --volume /katalof vol-test
```

Tworzy gotowy volumen i dodaje do kontenera, nadaje mu losowe id, nie są przeznaczone by ręcznie w nich pracować
Jeżeli dwa kontenery będą pracować z tym samym volumenem to nic się nie stanie.
Podpinanie kontenera do plików lokalnych razem BIND MOUNT

```bash
docker run --volume "sciezka do katalogu" //c/Users/kotko/w/docker/s3/katalog:/katalog
```

Teraz mamy katalog i na komputerze lokalnie i w kontenerze
Monitorowanie na żywo zmian:

```bash
docker run ubuntu watch ls /app
```

11. Baza danych w kontenerze
    Wypisanie wszystkich zmiennych środowiskowych w kontenerze:

```bash
docker run ubuntu env
```

Przekazywanie ustawień do programu działającego w kontenerze:

```bash
docker run -e MOJA_ZMIENNA=ture ubuntu env
```

Uruchamianie bazy w kontenerze (konfiguracja z dockerhub)

```bash
docker run --name baza --detach postgres #kontener uruchamia się w tle
docker logs baza
docker rm baza #usuwanie konetnera baza
docker run --name baza --detach -e POSTGRES_ PASSWORD= haslo postgres
docker exec -it baza psql --username postgres #konsola bazy w dockerze

create table tabela();
select * from tabela;

docker stop baza
docker rm baza
```

```bash
docker run --name baza --detach -e POSTGRES PASSWORD=haslo volume dane_bazy:/var/lib/postgresql/data postgres
docker exec -it baza psql --username postgres #konsola bazy w dockerze

create table tabela();
docker stop baza
docker rm baza
drocker volume ls #pozostaje volume danej bazy

docker exec -it baza psql --username postgres #uruchomienie nowej instancji ale używając danych które używaliśmy przed chwilą
```

12. Docker inspect
    docker inspect to komenda w Dockerze, która służy do wyświetlania szczegółowych informacji o obiektach Docker, takich jak kontenery, obrazy, wolumeny lub sieci. Informacje są prezentowane w formacie JSON.

```bash
docker inspect my_container
```

Wynikowo daje to nam:

```bash
[
    {
        "Id": "8dfafdbc3a40",
        "Created": "2020-04-07T12:44:20.041Z",
        "Path": "/bin/sh",
        "Args": ["-c", "while true; do echo hello world; sleep 1; done"],
        "State": {
            "Status": "running",
            "Running": true,
            "Paused": false,
            "Restarting": false,
            "OOMKilled": false,
            "Dead": false,
            "Pid": 12345,
            "ExitCode": 0,
            "Error": "",
            "StartedAt": "2020-04-07T12:44:21.171Z",
            "FinishedAt": "0001-01-01T00:00:00Z"
        }, #dalsza część kodu...
    }
]
```

13. Docker Networks: wirtualne sieci w kontenerach

```bash
docker run -dit --name contA busybox #Uruchomienie kontenera contA w tle (detached mode)
docker run -dit --name contB busybox
docker network inspect bridge
docker attach contA
docker stop contA contB #Zatrzymanie kontenerów contA i contB
docker rm contA contB
docker network create --driver bridge moja-siec #Tworzy nową sieć o nazwie moja-siec używając sterownika typu bridge
docker run -dit --name contC --network moja-siec busybox #Uruchomienie kontenera contC w tle (detached mode) i podłączenie go do sieci moja-siec
```

docker network inspect bridge
Wyświetla szczegółowe informacje o sieci bridge, w tym listę podłączonych kontenerów, konfigurację IP i inne ustawienia sieciowe.

docker attach contA
Łączy terminal użytkownika z aktywną sesją kontenera contA, umożliwiając interakcję z procesami uruchomionymi w kontenerze.

14. Sieci - łączenie kontenerów

```bash
docker network create baza=-net
docker run --name baza -v dane_bazy:/var/lib/postgresql/data -e POSTGRES_DB=mojabaza -e POSTGRES_USER=ja -e POSTGRES_PASSWORD=mojehaslo --network baza-net --detach postgres
docker run  -p 8080:8080 --network baza-net adminer
```

docker run: Uruchamia nowy kontener.
--name baza: Nadaje kontenerowi nazwę baza.
-v dane_bazy:/var/lib/postgresql/data: Mapuje wolumen nazwany dane_bazy do ścieżki /var/lib/postgresql/data w kontenerze, co pozwala na trwałe przechowywanie danych bazy danych.
-e POSTGRES_DB=mojabaza: Ustawia zmienną środowiskową POSTGRES_DB, co powoduje, że PostgreSQL utworzy bazę danych o nazwie mojabaza.
-e POSTGRES_USER=ja: Ustawia zmienną środowiskową POSTGRES_USER, co tworzy użytkownika PostgreSQL o nazwie ja.
-e POSTGRES_PASSWORD=mojehaslo: Ustawia zmienną środowiskową POSTGRES_PASSWORD, co ustawia hasło dla użytkownika PostgreSQL na mojehaslo.
--network baza-net: Podłącza kontener do wcześniej utworzonej sieci baza-net.
--detach: Uruchamia kontener w tle (detached mode).
postgres: Używa obrazu PostgreSQL.
-p 8080:8080: Mapuje port 8080 na hoście do portu 8080 w kontenerze, umożliwiając dostęp do aplikacji Adminer przez http://localhost:8080.

15. Docker compose

```bash
vim docker-compose.yml
```

```bash
version: '3'
services:
  db:
    image: postgres
    environment:
      POSTGRES_DB: mojabaza
      POSTGRES_USER: ja
      POSTGRES_PASSWORD: mojehaslo
    volumes:
      - dane_bazy:/var/lib/postgresql/data
  web:
    image: adminer
    ports:
      - "8080:8080"
    depends_on:
      - db
volumes:
  dane_bazy:
```

Umożliwia definiowanie usług, sieci i wolumenów w jednym pliku docker-compose.yml.
Można uruchamiać, zatrzymywać i zarządzać wszystkimi usługami zdefiniowanymi w docker-compose.yml za pomocą jednej komendy.

docker-compose up: Uruchamia wszystkie usługi zdefiniowane w pliku docker-compose.yml.
docker-compose down: Zatrzymuje i usuwa wszystkie usługi oraz sieci i wolumeny zdefiniowane w pliku docker-compose.yml.
docker-compose ps: Wyświetla status uruchomionych usług.
docker-compose logs: Wyświetla logi z wszystkich usług.

16. Django i docker compose
    Dockerfile, który tworzy obraz aplikacji

```bash
FROM python:3.8
WORKDIR app
RUN pip install Django gunicorn psycopg2
COPY mysite .
CMD funicorn --bind=0.0.0.0:8080 mysite.wsgi
```

Tworzenie obrazu

```bash
docker build -t django_app .
docker run -p 8082:8080
```

W docker compose dodajemy

```bash
django_app:
    build: ./django_app
    image: django_img
    networks:
        - baza-net
    ports:
        - 8080:8080
    depends_on:
        -baza
```

Budowanie docker file

```bash
docker-copmpose up --build
```

17. NGINX
    NGINX może pełnić rolę serwera HTTP, obsługując statyczne strony internetowe, pliki multimedialne, oraz dynamiczne aplikacje webowe

```bash
django_app:
    build: ./django_app
    image: django_img
    networks:
        - baza-net
    ports:
        - 8080:8080
    depends_on:
        -baza
gateway:
    image: nginx
    volumes:
        - ./nginx.conf:/etc/nginx/nginx.conf:ro
    depends_on:
        my_app
    networks:
        - app-net
    ports:
        - 7070:8080
volumes:
    dane_bazy:
networks:
    baza-net:
    app-net:
```

```bash
ls django_app docker-compose.yml nginx.conf
set COMPOSE_CONVERT_WINDOWS_PATHS=1
```

18. Multi-Stage build
    Multi-stage build to technika używana w Dockerze, która pozwala na tworzenie lekkich i zoptymalizowanych obrazów kontenerów poprzez wykorzystanie wielu etapów budowania w jednym Dockerfile

```bash
FROM golang:1.16 AS builder
WORKDIR /app
COPY . .
RUN go build -o myapp

FROM alpine:latest
WORKDIR /root/
COPY --from=builder /app/myapp .
CMD ["./myapp"]

```

FROM golang:1.16 AS builder: Używamy oficjalnego obrazu Go do kompilacji naszej aplikacji. Nadajemy temu etapowi nazwę builder.
WORKDIR /app: Ustawiamy katalog roboczy na /app.
COPY . .: Kopiujemy wszystkie pliki z bieżącego katalogu na hosta do katalogu roboczego w kontenerze.
RUN go build -o myapp: Kompilujemy aplikację Go, tworząc plik binarny myapp.
FROM alpine:latest: Używamy lekkiego obrazu Alpine jako podstawy dla naszego finalnego obrazu.
WORKDIR /root/: Ustawiamy katalog roboczy na /root/.
COPY --from=builder /app/myapp .: Kopiujemy skompilowaną binarkę myapp z etapu builder do bieżącego katalogu roboczego (/root/) w finalnym obrazie.
CMD ["./myapp"]: Definiujemy polecenie, które ma zostać wykonane po uruchomieniu kontenera.
