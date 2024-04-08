""" 7. Stwórz folder 'utils', a w nim plik 'obliczenia.py', w którym należy zaimplementować cztery wybrane funkcje matematyczne z modułu 'math'. 
   Następnie należy utworzyć plik 'skrypt7-nr_albumu.py' i zaimportować w nim ww. funkcje do obliczeń na przykładowych wartościach."""
from utils.obliczenia import my_abs, my_factorial, my_pow, iloczyn_skalarny  
lista=[1, 2, 3]
if __name__=='__main__':
   print(my_factorial(20))
   print(my_pow(2, 2))
   print(iloczyn_skalarny(lista))
   print(my_abs(2))