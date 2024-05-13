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
