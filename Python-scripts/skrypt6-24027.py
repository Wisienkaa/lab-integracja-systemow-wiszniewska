""" 6. Napisz program, który tworzy słownik o nazwie zawierającej Twój numer albumu.
   Kluczami powinny być liczby od 10 do 20, a wartościami pseudolosowe łańcuch znaków o długości 8.
   Hint: skorzystaj z modułów 'string' i 'random'."""
import math       
import random
import string

def generate_pseudo_string(len_of_string):
    return ''.join(random.choices(string.ascii_letters, k=len_of_string))

zmienna_24027 = dict()
if __name__=='__main__':
    for i in range (10, 21):
     zmienna_24027[i] = generate_pseudo_string(8)

    print(zmienna_24027)