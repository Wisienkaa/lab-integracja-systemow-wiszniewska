"""9. Stwórz klasy Vehicle i Car z polami 'nazwa' 'rok_produkcji' i 'przebieg' oraz metodami is_old() i is_long_mileage().
   Stwórz po jednym obiekcie dla każdej z klas oraz trzeci obiekt, gdzie klasa Car dziedziczy z klasy Vehicle.
   Dla każdego z obiektów wywołaj obie metody, co najmniej raz użyj dekoratora '@property' w każdym z trzech przypadków."""

class Vehicle:
    def __init__(self, nazwa, rok_produkcji, przebieg):
        self.nazwa = nazwa
        self.rok_produkcji = rok_produkcji
        self.przebieg = przebieg

    def is_old(self):
        return self.rok_produkcji < 2010

    @property
    def is_long_mileage(self):
        return self.przebieg > 100000


class Car(Vehicle):
    def __init__(self, nazwa, rok_produkcji, przebieg, ilosc_drzwi):
        super().__init__(nazwa, rok_produkcji, przebieg)
        self.ilosc_drzwi = ilosc_drzwi

v1 = Vehicle("Samochód", 2005, 150000)
c2 = Car("BMW", 2015, 80000, 4)
c3 = Car("Audi", 2012, 120000, 4)

print("Pojazd 1:")
print("Czy stary?", v1.is_old())
print("Czy ma dużo przebiegu?", v1.is_long_mileage)

print("\nPojazd 2:")
print("Czy stary?", c2.is_old())
print("Czy ma dużo przebiegu?", c2.is_long_mileage)

print("\nPojazd 3:")
print("Czy stary?", c3.is_old())
print("Czy ma dużo przebiegu?", c3.is_long_mileage)
