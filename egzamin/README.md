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

Jeśli masz gałąź main z najnowszymi zmianami w zdalnym repozytorium, wykonanie git fetch pobierze te zmiany, ale nie wprowadzi ich do twojej lokalnej gałęzi main.
Wykonanie git pull pobierze te zmiany i automatycznie je zmerguje z twoją lokalną gałęzią main.

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
