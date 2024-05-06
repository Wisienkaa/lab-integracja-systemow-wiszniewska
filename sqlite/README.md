1. Selecting All Values from Table

```bash
select * from little_penguins;
```

2. Specifying Columns

```bash
select
    species,
    island,
    sex
from little_penguins;
```

![venv](1.png) 3. Sorting

```bash
select
    species,
    sex,
    island
from little_penguins
order by island asc, sex desc;
```

![venv](2.png) 4. Exercise
Write a SQL query to select the sex and body mass columns from the little_penguins in that order, sorted such that the largest body mass appears first.

```bash
SELECT sex, body_mass_g
FROM little_penguins
ORDER BY body_mass_g DESC;
```

![venv](3.png)

5. Limiting Output

```bash
select
    species,
    sex,
    island
from penguins
order by species, sex, island
limit 10;
```

![venv](4.png)

6.Paging Output

```bash
select
    species,
    sex,
    island
from penguins
order by species, sex, island
limit 10 offset 3;
```

![venv](5.png)

7.Removing Duplicates

````bash
select distinct
    species,
    sex,
    island
from penguins;```
![venv](6.png)

8.
Exercise
Write a SQL query to select the islands and species from rows 50 to 60 inclusive of the penguins table. Your result should have 11 rows.

Modify your query to select distinct combinations of island and species from the same rows and compare the result to what you got in part 1.
```bash
SELECT island, species
FROM (
    SELECT *,
           ROW_NUMBER() OVER (ORDER BY species) AS row_num
    FROM penguins
) AS numbered_rows
WHERE row_num BETWEEN 50 AND 60;

SELECT DISTINCT island, species
FROM (
    SELECT *,
           ROW_NUMBER() OVER (ORDER BY species) AS row_num
    FROM penguins
) AS numbered_rows
WHERE row_num BETWEEN 50 AND 60;

````

![venv](7_1.png)
![venv](7_2.png)

9. Filtering Results

```bash
select distinct
    species,
    sex,
    island
from penguins
where island = 'Biscoe';
```

![venv](8.png)

10. Exercise
    Write a query to select the body masses from penguins that are less than 3000.0 grams.

Write another query to select the species and sex of penguins that weight less than 3000.0 grams. This shows that the columns displayed and those used in filtering are independent of each other.

```bash
SELECT body_mass_g
FROM penguins
WHERE body_mass_g < 3000.0;


SELECT species, sex
FROM penguins
WHERE body_mass_g < 3000.0;
```

![venv](9_1.png)
![venv](9_2.png)

11. Exercise
    Use the not operator to select penguins that are not Gentoos.

SQL’s or is an inclusive or: it succeeds if either or both conditions are true. SQL does not provide a specific operator for exclusive or, which is true if either but not both conditions are true, but the same effect can be achieved using and, or, and not. Write a query to select penguins that are female or on Torgersen Island but not both.

```bash
SELECT *
FROM penguins
WHERE species <> 'Gentoos';


SELECT *
FROM penguins
WHERE (sex = 'female' OR island = 'Torgersen') AND NOT (sex = 'female' AND island = 'Torgersen');

```

![venv](10_1.png)
![venv](10_2.png)

12. Doing Calculations

```bash
select
    flipper_length_mm / 10.0,
    body_mass_g / 1000.0
from penguins
limit 3;
```

![venv](11.png)

13. Exercise
    Write a single query that calculates and returns:

A column called what_where that has the species and island of each penguin separated by a single space.
A column called bill_ratio that has the ratio of bill length to bill depth.
You can use the || operator to concatenate text to solve part 1, or look at the documentation for SQLite’s format() function.

```bash
SELECT
    species || ' ' || island AS what_where,
    bill_length_mm / bill_depth_mm AS bill_ratio
FROM penguins;
```

![venv](12.png)

14. Calculating with Missing Values

```bash
select
    flipper_length_mm / 10.0 as flipper_cm,
    body_mass_g / 1000.0 as weight_kg,
    island as where_found
from penguins
limit 5;
```

![venv](13.png)

15. Exercise
    Use SQLite’s .nullvalue command to change the printed representation of null to the string null and then re-run the previous query. When will displaying null as null be easier to understand? When might it be misleading?

```bash
.nullvalue null
```

Użycie polecenia .nullvalue w SQLite pozwala zmienić drukowaną reprezentację wartości null na określony ciąg znaków, na przykład "null".

16. Null Equality

```bash
select distinct
    species,
    sex,
    island
from penguins
where island = 'Biscoe';

select distinct
    species,
    sex,
    island
from penguins
where island = 'Biscoe' and sex = 'FEMALE';
```

![venv](14_1.png)
![venv](14_2.png)

17. Null Inequality

```bash
select distinct
    species,
    sex,
    island
from penguins
where island = 'Biscoe' and sex != 'FEMALE';
```

![venv](15.png)

18. Handling Null Safely

```bash
select
    species,
    sex,
    island
from penguins
where sex is null;
```

![venv](16.png)

19. Exercise
    Write a query to find penguins whose body mass is known but whose sex is not.

Write another query to find penguins whose sex is known but whose body mass is not.

```bash
SELECT *
FROM penguins
WHERE body_mass_g IS NOT NULL AND sex IS NULL;

SELECT *
FROM penguins
WHERE sex IS NOT NULL AND body_mass_g IS NULL;
```

![venv](17_1.png)
![venv](17_2.png)

20. Aggregating

```bash
select sum(body_mass_g) as total_mass
from penguins;
```

![venv](18.png)

21. Common Aggregation Functions

```bash
select
    max(bill_length_mm) as longest_bill,
    min(flipper_length_mm) as shortest_flipper,
    avg(bill_length_mm) / avg(bill_depth_mm) as weird_ratio
from penguins;
```

![venv](19.png)

22. Exercise
    What is the average body mass of penguins that weight more than 3000.0 grams?

```bash
SELECT AVG(body_mass_g) AS średnia_masa_ciała
FROM penguins
WHERE body_mass_g > 3000.0;
```

To zapytanie oblicza średnią masę ciała (AVG(body_mass_g)) pingwinów z tabeli penguins, gdzie masa ciała jest większa niż 3000,0 gramów (WHERE body_mass_g > 3000.0).
![venv](20.png)

23. Counting

```bash
select
    count(*) as count_star,
    count(sex) as count_specific,
    count(distinct sex) as count_distinct
from penguins;
```

![venv](21.png)

24. Exercise
    How many different body masses are in the penguins dataset?

```bash
SELECT COUNT(DISTINCT body_mass_g) AS different_body_masses
FROM penguins;

```

![venv](22.png)

25. Grouping

```bash
select avg(body_mass_g) as average_mass_g
from penguins
group by sex;
```

![venv](23.png)

26. Exercise
    Explain why the output of the previous query has a blank line before the rows for female and male penguins.

Write a query that shows each distinct body mass in the penguin dataset and the number of penguins that weigh that much.

Wynik poprzedniego zapytania zawiera pustą linię przed wierszami dla pingwinów o płci żeńskiej i męskiej, ponieważ te wiersze zostały oddzielone przez wartość null w kolumnie płci. Spowodowane jest to faktem, że dla niektórych pingwinów nie jest znana płci, co jest reprezentowane przez wartość null.

Zapytanie wyświetlające każdą różną masę ciała w zestawie danych pingwinów oraz liczbę pingwinów o takiej masie:

```bash
SELECT body_mass_g, COUNT(*) AS num_penguins
FROM penguins
GROUP BY body_mass_g;

```

![venv](24.png)

27. Filtering Aggregated Values

```bash
select
    sex,
    avg(body_mass_g) as average_mass_g
from penguins
group by sex
having average_mass_g > 4000.0;
```

![venv](25.png)

28. Readable Output

```bash
select
    sex,
    round(avg(body_mass_g), 1) as average_mass_g
from penguins
group by sex
having average_mass_g > 4000.0;
```

![venv](26.png)

29. Exercise
    Write a query that uses filter to calculate the average body masses of heavy penguins (those over 4500 grams) and light penguins (those under 3500 grams) simultaneously. Is it possible to do this using where instead of filter?

Użycie klauzuli WHERE zamiast FILTER w tym przypadku nie zadziała, ponieważ WHERE służy do filtrowania wierszy, podczas gdy FILTER jest używane do stosowania warunków do funkcji agregujących.

```bash
select
SELECT
    AVG(body_mass_g) FILTER (WHERE body_mass_g > 4500) AS average_heavy_mass,
    AVG(body_mass_g) FILTER (WHERE body_mass_g < 3500) AS average_light_mass
FROM penguins;

```

![venv](27.png)

30. Creating In-memory Database

```bash
sqlite3 :memory:
```

31. Creating Tables

```bash
create table job (
    name text not null,
    billable real not null
);
create table work (
    person text not null,
    job text not null
);
```

![venv](28.png) 32. Inserting data

```bash
insert into job values
('calibrate', 1.5),
('clean', 0.5);
insert into work values
('mik', 'calibrate'),
('mik', 'clean'),
('mik', 'complain'),
('po', 'clean'),
('po', 'complain'),
('tay', 'complain');
```

![venv](29.png)

33. Updating Rows

```bash
update work
set person = 'tae'
where person = 'tay';
```

![venv](30.png)

34. What happens if you try to delete rows that don’t exist (e.g., all entries in work that refer to juna)?
    Operacja DELETE nie powoduje błędu ani żadnych zmian w bazie danych, jeśli nie ma pasujących wierszy do usunięcia.

35. Backing Up

```bash
create table backup (
    person text not null,
    job text not null
);

insert into backup
select
    person,
    job
from work
where person = 'tae';

delete from work
where person = 'tae';

select * from backup;
```

36. Exercise
    Saving and restoring data as text:

Re-create the notes table in an in-memory database and then use SQLite’s .output and .dump commands to save the database to a file called notes.sql. Inspect the contents of this file: how has your data been stored?

Start a fresh SQLite session and load notes.sql using the .read command. Inspect the database using .schema and select \*: is everything as you expected?

Saving and restoring data in binary format:

Re-create the notes table in an in-memory database once again and use SQLite’s .backup command to save it to a file called notes.db. Inspect this file using od -c notes.db or a text editor that can handle binary data: how has your data been stored?

Start a fresh SQLite session and load notes.db using the .restore command. Inspect the database using .schema and select \*: is everything as you expected?
