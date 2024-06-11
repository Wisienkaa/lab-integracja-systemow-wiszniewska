class Kalkulator:
    def dodaj(self, a, b):
        return a + b

    def odejmij(self, a, b):
        return a - b

    def pomnoz(self, a, b):
        return a * b

    def podziel(self, a, b):
        if b == 0:
            raise ValueError("Nie można dzielić przez zero!")
        return a / b


if __name__ == "__main__":
    kalkulator = Kalkulator()

    # Dodawanie
    wynik_dodawania = kalkulator.dodaj(10, 5)
    print(f"10 + 5 = {wynik_dodawania}")

    # Odejmowanie
    wynik_odejmowania = kalkulator.odejmij(10, 5)
    print(f"10 - 5 = {wynik_odejmowania}")

    # Mnożenie
    wynik_mnozenia = kalkulator.pomnoz(10, 5)
    print(f"10 * 5 = {wynik_mnozenia}")

    # Dzielenie
    try:
        wynik_dzielenia = kalkulator.podziel(10, 5)
        print(f"10 / 5 = {wynik_dzielenia}")
    except ValueError as e:
        print(e)
