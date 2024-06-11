I. Git

1. Utwórz nową gałąź (np. nowa) z bieżącej (najczęściej będzie to main), stwórz w nowej gałęzi nowy plik i zmerguj nową gałąź do bieżącej.
   Utworzenie nowej gałęzi i przełączenie się na nią:

```bash
git checkout -b nowa
```

Stworzenie nowego pliku i dodanie go do repozytorium:

```bash
echo "Zawartość pliku" > nowy_plik.txt
git add nowy_plik.txt
git commit -m "Dodano nowy plik"
```

Przełączenie się na gałąź main:

```bash
git checkout main
```

Zmergowanie gałęzi nowa do main:

```bash
git merge nowa
```

Usunięcie gałęzi nowa po zmergowaniu (opcjonalnie):

```bash
git branch -d nowa
```

2. Pokaż jak działa pull request na jednym ze swoich repozytoriów.
   Stworzenie nowej gałęzi (np. nowa-feature):

```bash
git checkout -b nowa-feature
```

Wprowadzenie zmian w repozytorium i commitowanie:

```bash
echo "Nowa funkcjonalność" > nowy_plik.txt
git add nowy_plik.txt
git commit -m "Dodano nową funkcjonalność"
```

Wypchnięcie nowej gałęzi na zdalne repozytorium:

```bash
git push origin nowa-feature
```

3. Omów różnicę między git fetch a git pull na przykładzie swojego repozytorium.
   git fetch:
   Pobiera najnowsze zmiany ze zdalnego repozytorium, ale nie integruje ich z lokalnym repozytorium.

```bash
git fetch origin
```

git pull:

Pobiera najnowsze zmiany ze zdalnego repozytorium i automatycznie łączy je z aktualnie aktywną gałęzią lokalną.

```bash
git pull origin main
```

Przykład:

Jeśli mam gałąź main z najnowszymi zmianami w zdalnym repozytorium, wykonanie git fetch pobierze te zmiany, ale nie wprowadzi ich do mojej lokalnej gałęzi main.
Wykonanie git pull pobierze te zmiany i automatycznie je zmerguje z moją lokalną gałęzią main.

4. Pokaż działanie git stash.
   Wprowadzenie zmian w repozytorium bez commitowania:

```bash
echo "Niezapisane zmiany" > tymczasowy_plik.txt
git add tymczasowy_plik.txt
```

Użycie git stash do zapisania tych zmian tymczasowo:

```bash
git stash
```

Przywrócenie zmian ze schowka:

```bash
git stash pop
```

5. Omów działanie git rebase i wskaż różnice w stosunku do git merge (mile widziany rysunek).
   git rebase:

Przenosi zestaw commitów z jednej gałęzi i umieszcza je na szczycie innej gałęzi, zmieniając historię commitów.
Użyteczny do tworzenia czystej, liniowej historii.
git merge:

Łączy dwie gałęzie, zachowując historię commitów obu gałęzi.
Powoduje utworzenie nowego commita merge.
Przykład działania git rebase:

Załóżmy, że mamy dwie gałęzie main i feature

```bash
git checkout feature
git rebase main
```

Przykład działania git merge:

Łączenie gałęzi feature do main

```bash
git checkout main
git merge feature
```

Rysunek:

```bash
Before rebase:
main:    A---B---C
           \
feature:    D---E---F

After rebase:
main:    A---B---C
                \
feature:         D'---E'---F'

After merge:
main:    A---B---C-------G
           \       /
feature:    D---E---F
```

W rebase, commity D, E, F są przenoszone na szczyt gałęzi main jako D', E', F', zmieniając ich identyfikatory. W merge, commity D, E, F pozostają na swoich miejscach, a nowy commit G jest dodawany jako merge commit, zachowując całą historię.

II. Bazy danych

1. Za pomocą skryptu w wybranym języku dodaj kolejny rekord do wskazanej bazy danych.

```bash
INSERT INTO person (ident, name) VALUES (25 ,'Jan Kowalski');
```

2. Dla wybranej bazy danych pokaż działanie co najmniej trzech różnych typów JOIN'a.
   Tworzenie tabeli students i courses

```bash
CREATE TABLE students (
    id INTEGER PRIMARY KEY,
    name TEXT,
    age INTEGER
);

INSERT INTO students (name, age) VALUES ('Jan Kowalski', 25);
INSERT INTO students (name, age) VALUES ('Anna Nowak', 22);


CREATE TABLE courses (
    id INTEGER PRIMARY KEY,
    student_id INTEGER,
    course_name TEXT,
    FOREIGN KEY (student_id) REFERENCES students(id)
);

INSERT INTO courses (student_id, course_name) VALUES (1, 'Matematyka');
INSERT INTO courses (student_id, course_name) VALUES (2, 'Biologia');
INSERT INTO courses (student_id, course_name) VALUES (1, 'Informatyka');
```

INNER JOIN:

```bash
SELECT students.name, courses.course_name
FROM students
INNER JOIN courses ON students.id = courses.student_id;
```

LEFTJOIN:

```bash
SELECT students.name, courses.course_name
FROM students
LEFT JOIN courses ON students.id = courses.student_id;
```

RIGHTJOIN: (SQLite nie obsługuje RIGHT JOIN bezpośrednio, ale można uzyskać podobny efekt używając LEFT JOIN z zamianą ról tabel)

```bash
SELECT students.name, courses.course_name
FROM courses
LEFT JOIN students ON students.id = courses.student_id;
```

3. Zaloguj się do bazy danych PostgreSQL w kontenerze Dockerowym i wykonaj operację SELECT dla dowolnej tabeli.
   Uruchomienie kontenera Dockerowego:

```bash
docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres
```

Logowanie do bazy danych PostgreSQL:

```bash
docker exec -it some-postgres psql -U postgres
```

Wykonanie operacji SELECT:

```bash
\c your_database_name
SELECT * FROM your_table_name;
```

4. Wskaż różnice między SQLite a PostgreSQL na wybranym przez siebie przykładzie.
   Przykład: Implementacja tabeli z autoinkrementacją i kluczami obcymi.
   SQLite:

```bash
CREATE TABLE students (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT,
    age INTEGER
);

CREATE TABLE courses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    student_id INTEGER,
    course_name TEXT,
    FOREIGN KEY (student_id) REFERENCES students(id)
);
```

PostgreSQL:

```bash
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name TEXT,
    age INTEGER
);

CREATE TABLE courses (
    id SERIAL PRIMARY KEY,
    student_id INTEGER,
    course_name TEXT,
    FOREIGN KEY (student_id) REFERENCES students(id)
);
```

Różnice:

SQLite używa AUTOINCREMENT dla autoinkrementacji, podczas gdy PostgreSQL używa SERIAL.
PostgreSQL oferuje bardziej zaawansowane funkcje takie jak wsparcie dla typów danych, rozszerzenia, pełne zarządzanie transakcjami, replikację itp.

5. Przygotuj zapytania zawierające polecenia WHERE, LIKE, COUNT, GROUP BY, HAVING i bądz gotowy do ich uruchomienia i modyfikacji.
   WHERE i LIKE:

```bash
SELECT * FROM students
WHERE name LIKE 'Jan%';
```

COUNT:

```bash
SELECT COUNT(*) FROM students
WHERE age > 20;
```

GROUP BY i HAVING:

```bash
SELECT age, COUNT(*) as count
FROM students
GROUP BY age
HAVING COUNT(*) > 1;
```

III. Aplikacja wg wzorca projektowego MVC (Model-View-Controller)

1. Czym jest ORM, zaprezentuj praktycznie na przykładzie własnego projektu.
   ORM, czyli Object-Relational Mapping, to technika programistyczna, która umożliwia mapowanie obiektów z bazy danych relacyjnych na obiekty języka programowania i vice versa. Pozwala to programistom pracować z bazą danych korzystając z obiektowego podejścia, co często ułatwia zarządzanie danymi i ułatwia pracę z bazą danych.

Przykładem ORM może być biblioteka SQLAlchemy w języku Python, która zapewnia narzędzia do mapowania obiektów Pythona na struktury relacyjne w bazie danych.

W praktyce, na przykładzie prostego projektu, możemy utworzyć klasę w Pythonie, która reprezentuje tabelę w bazie danych, a następnie korzystać z metod tej klasy do operacji na danych, zamiast korzystać bezpośrednio z języka zapytań do bazy danych.

```bash
from sqlalchemy import Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class User(Base):
    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    name = Column(String)
    age = Column(Integer)

# Przykładowe użycie ORM SQLAlchemy
# Tworzenie nowego użytkownika
new_user = User(name='John', age=30)

# Dodawanie użytkownika do bazy danych
session.add(new_user)
session.commit()

# Pobieranie użytkownika o określonym identyfikatorze
user = session.query(User).filter_by(name='John').first()
print(user.name, user.age)
```

W powyższym przykładzie, ORM (w tym przypadku SQLAlchemy) pozwala nam operować na obiektach klasy User tak, jakby były one rekordami w bazie danych, zamiast bezpośrednio operować na tabelach i kolumnach.

2. Czym jest wzorzec MVC? Wskaż w kodzie aplikacji poszczególne elementy tego wzorca i określ ich role.
   Wzorzec MVC (Model-View-Controller) jest wzorcem architektonicznym stosowanym w projektowaniu aplikacji, który dzieli aplikację na trzy główne składniki: Model, Widok (View) i Kontroler (Controller). Każdy z tych składników ma określoną rolę w procesie działania aplikacji.

Model: Reprezentuje dane oraz logikę biznesową aplikacji. Odpowiada za dostęp do danych oraz manipulację nimi. W przypadku aplikacji webowej, model zazwyczaj odzwierciedla strukturę bazy danych.

Widok (View): Odpowiada za prezentację danych użytkownikowi oraz interakcję z nim. Wyświetla informacje użytkownikowi w czytelnej formie i interpretuje interakcje użytkownika, takie jak kliknięcia czy wprowadzanie danych.

Kontroler (Controller): Odpowiada za kontrolę przepływu aplikacji oraz reakcję na interakcje użytkownika. Przetwarza żądania użytkownika, wywołuje odpowiednie akcje w modelu oraz aktualizuje widok w zależności od wyników tych akcji.

Poniżej przedstawiam przykładowy kod aplikacji z wykorzystaniem wzorca MVC:

```bash
# Model
class User:
    def __init__(self, name, email):
        self.name = name
        self.email = email

# Widok
class UserView:
    def show_user_details(self, user):
        print(f"Name: {user.name}, Email: {user.email}")

# Kontroler
class UserController:
    def __init__(self, model, view):
        self.model = model
        self.view = view

    def get_user_details(self):
        user = self.model.get_user()
        self.view.show_user_details(user)

# Użycie
if __name__ == "__main__":
    # Inicjalizacja modelu, widoku i kontrolera
    model = UserModel()
    view = UserView()
    controller = UserController(model, view)

    # Pobranie i wyświetlenie szczegółów użytkownika
    controller.get_user_details()
```

W powyższym kodzie:

Model reprezentowany jest przez klasę User, która przechowuje dane użytkownika.
Widok reprezentowany jest przez klasę UserView, która odpowiada za wyświetlanie danych użytkownika.
Kontroler reprezentowany jest przez klasę UserController, która kontroluje przepływ aplikacji i interakcję z modelem oraz widokiem.
W ten sposób, każdy z komponentów (Model, Widok, Kontroler) jest odpowiednio odseparowany od siebie, co ułatwia zarządzanie kodem oraz umożliwia jego ponowne użycie.

3. Dodaj nowy URL w aplikacji i spraw, aby po uruchomieniu go w przeglądarce pojawiło się Twoje imię i nazwisko.

app.py

```bash
from flask import Flask

app = Flask(__name__)

@app.route('/')
def home():
    return 'Monika W'

if __name__ == '__main__':
    app.run(debug=True)
```

Flask to mikroframework webowy napisany w języku Python, który jest wykorzystywany do tworzenia aplikacji internetowych. Jest prosty i elastyczny.

4. Dodaj nowy URL w aplikacji i spraw, aby po uruchomieniu go w przeglądarce pojawił się formularz, który pozwala dodać dwie liczby.
   urls.py

```bash
from django.urls import path
from . import views

urlpatterns = [
    path('', views.post_list, name='post_list'),
    path('myname/', views.my_name, name='my_name'),
    path('add/', views.add_numbers, name='add_numbers'),
]
```

views.py

```bash
from django.shortcuts import render
from django.http import HttpResponse

def post_list(request):
    return render(request, 'blog/post_list.html', {})

def my_name(request):
    return HttpResponse('Monika W')

def add_numbers(request):
    if request.method == 'POST':
        try:
            number1 = int(request.POST.get('number1'))
            number2 = int(request.POST.get('number2'))
            result = number1 + number2
            return render(request, 'blog/add_numbers.html', {'result': result})
        except ValueError:
            return render(request, 'blog/add_numbers.html', {'error': 'Invalid input. Please enter valid numbers.'})
    return render(request, 'blog/add_numbers.html')
```

add_numbers.html

```bash
<!DOCTYPE html>
<html>
<head>
    <title>Add Numbers</title>
</head>
<body>
    <h1>Add Two Numbers</h1>
    <form method="post">
        {% csrf_token %}
        <label for="number1">Number 1:</label>
        <input type="text" id="number1" name="number1"><br><br>
        <label for="number2">Number 2:</label>
        <input type="text" id="number2" name="number2"><br><br>
        <input type="submit" value="Add">
    </form>
    {% if result is not None %}
        <h2>Result: {{ result }}</h2>
    {% endif %}
    {% if error %}
        <h2 style="color: red;">{{ error }}</h2>
    {% endif %}
</body>
</html>
```

5. Dodaj nowy URL w aplikacji i spraw, aby po uruchomieniu go w przeglądarce wyświetliły się cztery zdjęcia ze strony https://jsonplaceholder.typicode.com/photos.

urls.py

```bash
from django.urls import path
from . import views

urlpatterns = [
    path('', views.post_list, name='post_list'),
    path('myname/', views.my_name, name='my_name'),
    path('add/', views.add_numbers, name='add_numbers'),
    path('photos/', views.display_photos, name='display_photos'),
]
```

views.py

```bash
from django.shortcuts import render
from django.http import HttpResponse
import requests

def post_list(request):
    return render(request, 'blog/post_list.html', {})

def my_name(request):
    return HttpResponse('Jan Kowalski')

def add_numbers(request):
    if request.method == 'POST':
        try:
            number1 = int(request.POST.get('number1'))
            number2 = int(request.POST.get('number2'))
            result = number1 + number2
            return render(request, 'blog/add_numbers.html', {'result': result})
        except ValueError:
            return render(request, 'blog/add_numbers.html', {'error': 'Invalid input. Please enter valid numbers.'})
    return render(request, 'blog/add_numbers.html')

def display_photos(request):
    response = requests.get('https://jsonplaceholder.typicode.com/photos')
    photos = response.json()[:4]  # Pobierz pierwsze cztery zdjęcia
    return render(request, 'blog/photos.html', {'photos': photos})
```

photos.html

```bash
<!DOCTYPE html>
<html>
<head>
    <title>Photos</title>
</head>
<body>
    <h1>Photos</h1>
    <div>
        {% for photo in photos %}
            <div style="margin-bottom: 20px;">
                <img src="{{ photo.url }}" alt="{{ photo.title }}" style="max-width: 200px;">
                <p>{{ photo.title }}</p>
            </div>
        {% endfor %}
    </div>
</body>
</html>
```

IV. Docker

1. Utwórz plik z obrazem Dockerfile, w którym z hosta do kontenera kopiowany będzie folder code (zawiera np. jeden skrypt w języku Python 🐍) i zbuduj go:
   uruchom ww. skrypt wewnątrz kontenera.

   Utworzenie skryptu pythona i Dockerfile
   W termilanu w lokalizacji project

   ```bash
   docker build -t python-script-container .
   ```

   Komenda docker build buduje obraz Docker, a opcja -t nadaje mu nazwę python-script-container. Kropka na końcu oznacza bieżący katalog jako kontekst budowania.

   Uruchamianie

   ```bash
   docker run --rm python-script-container
   ```

   Flaga --rm powoduje, że kontener zostanie usunięty po zakończeniu pracy.

   Sprawdzenie Logów Kontenera

   ```bash
   docker logs my-python-script-container
   ```

2. Skopiuj wybrany plik tekstowy z hosta (swojego komputera) do kontenera Dockerowego.

Utworzyłam plik example.txt
Uruchomienie kontenera w tle

```bash
docker run -d --name my-python-script-container python-script-container
```

Kopiowanie pliku do kontenera

```bash
docker cp C:\Users\kotko\lab-integracja-systemow-wiszniewska\egzamin\project\example.txt my-python-script-container:/app/code/example.txt
```

3. Skopiuj wybrany plik tekstowy z kontenera Dockerowego do hosta (swojego komputera).
   Skopiowanie Pliku Tekstowego z Kontenera do Hosta

```bash
docker cp my-python-script-container:/app/code/example.txt C:\Users\kotko\lab-integracja-systemow-wiszniewska\egzamin\example.txt
```

4. Pokaż użycie komend ENTRYPOINT i CMD.
   ENTRYPOINT
   ENTRYPOINT ustawia domyślną komendę, która jest uruchamiana po starcie kontenera. Można przekazać argumenty podczas uruchamiania kontenera.

```bash
# Dockerfile
FROM python:3.9-slim

WORKDIR /app/code

COPY code/ /app/code

ENTRYPOINT ["python", "script.py"]
```

Uruchamiając ten kontener, skrypt script.py zostanie uruchomiony automatycznie.

CMD ustawia domyślne polecenie, które może być nadpisane podczas uruchamiania kontenera. Jest często używane w połączeniu z ENTRYPOINT do przekazywania domyślnych argumentów.

```bash
# Dockerfile
FROM python:3.9-slim

WORKDIR /app/code

COPY code/ /app/code

ENTRYPOINT ["python"]
CMD ["script.py"]
```

W tym przypadku python jest ustawiony jako komenda bazowa przez ENTRYPOINT, a script.py jako domyślny argument przez CMD. Można uruchomić kontener z innym skryptem

```bash
docker run python-script-container another_script.py
```

5. Pokaż użycie komend ADD i COPY i WORKDIR w swoim projekcie.
   Komenda ADD jest bardziej wszechstronna niż COPY, ponieważ obsługuje automatyczne rozpakowywanie archiwów tar i może pobierać pliki z zewnętrznych URL-ów.

```bash
# Dockerfile
FROM python:3.9-slim

WORKDIR /app/code

# Dodaje plik z URL-a
 ADD http://example.com/somefile.tar.gz /app/code/

# Dodaje i rozpakowuje plik tar.gz
 ADD somefile.tar.gz /app/code/
```

COPY jest bardziej preferowaną komendą do kopiowania plików i katalogów z lokalnego kontekstu budowy.

```bash
# Dockerfile
FROM python:3.9-slim

WORKDIR /app/code

# Kopiuje katalog 'code' z lokalnego kontekstu budowy do kontenera
COPY code/ /app/code
```

WORKDIR ustawia domyślny katalog roboczy dla kolejnych komend RUN, CMD, ENTRYPOINT, COPY i ADD.

```bash
# Dockerfile
FROM python:3.9-slim

# Ustawia katalog roboczy na /app/code
WORKDIR /app/code

COPY code/ /app/code
```

6. Pokaż działanie docker compose w swoim projekcie.
   Docker Compose umożliwia definiowanie i uruchamianie wielokontenerowych aplikacji Docker za pomocą pliku YAML
   przykładowy plik docker-compose.yml

```bash
version: '3.8'

services:
  python-service:
    build: .
    container_name: python-script-container
    volumes:
      - ./code:/app/code
    command: python script.py
```

Opis działania:
-version: '3.8' określa wersję Compose.
-services definiuje listę usług (kontenerów).
-python-service to nazwa usługi.
-build: . wskazuje na budowanie obrazu z bieżącego katalogu (gdzie znajduje się Dockerfile).
-container_name ustawia nazwę kontenera.
-volumes mapuje lokalny katalog ./code do katalogu /app/code w kontenerze.
-command określa komendę do uruchomienia w kontenerze.

Uruchamianie docker compose

```bash
docker-compose up
```

Uruchamianie w tle (dodanie flagi)

```bash
docker-compose up -d
```

7. Omów na podstawie swojej aplikacji komendy docker inspect i docker logs.

docker inspect dostarcza szczegółowych informacji o obiekcie Docker, takim jak kontener lub obraz.
Np.

```bash
docker inspect python-script-container
```

Komenda zwróci szczegółowe informacje o kontenerze python-script-container, takie jak konfiguracja sieci, volumy, zmienne środowiskowe, i inne ustawienia.

docker logs wyświetla logi z uruchomionego lub zatrzymanego kontenera.
Np.

```bash
docker logs python-script-container
```

Wynikowo:

```bash
Starting script...
Process data...
Script completed successfully.
```

8. Czym są sieci w Dockerze? Zaprezentuj przykład na bazie swojego projektu.
   Sieci w Dockerze umożliwiają komunikację między kontenerami, a także między kontenerami a hostem oraz światem zewnętrznym. Docker domyślnie tworzy kilka typów sieci, w tym:

-Bridge (most): Domyślny typ sieci, który umożliwia komunikację między kontenerami na tej samej maszynie hosta.
-Host: Używa sieci hosta bez izolacji sieciowej dla kontenerów.
-None: Wyłącza wszystkie funkcje sieciowe dla kontenera.  
-Overlay: Umożliwia komunikację między wieloma hostami Docker.

W przykładzie projektu użyję sieci Bridge aby umożliwić komunikację kontenerom uruchamiającym skrypt i od serwera baz danych.  
Tworzenie sieci

```bash
docker network create my_bridge_network
```

Zaktualizowałam plik docker-compose.yml, aby oba kontenery były częścią tej samej sieci, czyli:  
-Dodałam usługę db, która uruchamia kontener z bazą danych MySQL.  
-Obie usługi (python-service i db) są częścią sieci my_bridge_network.  
-Sekcja networks definiuje sieć my_bridge_network z typem bridge.

Uruchomienie docker compose z moją siecią:

```bash
docker-compose up
```

Skrypt Pythona może teraz komunikować się z bazą danych MySQL za pomocą nazwy usługi db jako hosta:

```bash
import mysql.connector

connection = mysql.connector.connect(
    host="db",
    user="user",
    password="password",
    database="testdb"
)

cursor = connection.cursor()
cursor.execute("SELECT DATABASE()")
data = cursor.fetchone()
print("Connected to database:", data)
connection.close()
```

host="db" odnosi się do usługi db zdefiniowanej w docker-compose.yml.

docker-compose.yml ver1

```bash
version: '3.8'

services:
  python-service:
    build: .
    container_name: python-script-container
    volumes:
      - ./code:/app/code
    command: python script.py
```

V. Programowanie

1. Przygotuj klasę Kalkulator z czterema wybranymi działaniami matematycznymi (jako metody) i bądź gotowy do utworenia obiektów i modyfikacji tejże klasy wg wytycznych.
   w kalkulator.py w code

2. Napisz skrypt, pobierajacy dane w formacie JSON ze wskazanego API (online, np. https://jsonplaceholder.typicode.com/photos) i zapisz te dane do pliku tekstowego.
   w pobieranie_danych.py w code

3. Napisz skrypt, który odczytuje dane z pliku tekstowego i wyświetla je we wskazanej postaci.
   w odczyt.py w code
   read_and_display_file to funkcja, która przyjmuje ścieżkę do pliku jako argument i odczytuje jego zawartość.
   Wykorzystujemy blok try-except do obsługi błędów, takich jak brak pliku.
   Otwieramy plik w trybie odczytu ('r') i używamy metody read() do odczytania jego zawartości.

4. Pokaż działanie dziedziczenia w programowaniu obiektowym, poprzez utworzenie klasy Person (jej atrybuty to name i surname) i klas z niej dziedziczących, które mają dodatkowe atrybuty i metody. Może to być np. kod/program dotyczący osób na uczelni lub osób w firmie z określoną hierarchią.
   Klasa Person zawiera podstawowe informacje o osobie: imię i nazwisko. Klasy Student i Employee dziedziczą po klasie Person i dodają dodatkowe atrybuty (student_id dla studenta i employee_id dla pracownika) oraz metody (study dla studenta i work dla pracownika). Metody display_info zostały nadpisane w klasach pochodnych, aby uwzględnić dodatkowe informacje unikalne dla każdej z tych klas.
