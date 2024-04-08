"""8. Napisz program, który generuje losowy ciąg znaków o długości 100, a następnie utwórz słownik którego kluczami będą unikalne znaki występujące w ciągu,
   a wartościami liczba ich wystąpień w ciągu znakowym. Utwórz listę, której każdy element to krotka (tupla), zawierająca kolejny klucz z ww. słownika i odpowiadającą mu wartość liczbową.
   Hint: skorzystaj z modułu 'collections' i klasy Counter()."""

import random
import string
from collections import Counter

def occurances(text):
    acc= []
    my_dict = Counter(text)
    for key, value in my_dict.items():
        acc.append((key, value))
    return acc

if __name__ == '__main__':

    text_value = ''.join(random.choices(string.ascii_letters, k=100))
    
    wynik = occurances(text_value)
    for index, liczba_wystapien in enumerate(wynik):
        print(f"{index + 1}: {liczba_wystapien}")