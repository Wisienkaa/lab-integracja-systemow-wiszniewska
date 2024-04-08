""" 5. Napisz program (na dwa sposoby), który szuka pierwiastków liczb od 1 do 256 (włącznie) podzielnych bez reszty przez 2.
   Hint: skorzystaj z modułu 'math' i z tzw. 'list comprehensions'."""
import math       

def find_square_root(value):    
    return math.sqrt(value)

if __name__ == '__main__':
    
    square_roots=[]
    range_min = 1
    range_max = 256
    for i in range (range_min, range_max):
        if find_square_root(i) % 2 == 0:
            square_roots.append(i)
   
    if square_roots:
         print(f"Liczby, których  pierwiastek jest parzysty to {square_roots}")

