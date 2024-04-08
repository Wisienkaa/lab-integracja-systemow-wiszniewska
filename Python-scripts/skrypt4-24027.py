""" 4. Napisz program, który szuka określonego ciągu znaków w łańcuchu znakowym i zwraca indeksy wszystkich wystąpień ciągu lub -1, gdy nie ma takiego ciągu.
   Hint: skorzystaj z funkcji split()."""
        

def find_all_indexes(tekst, ciag_znakow):
    acc = []
    for index1, value in enumerate(tekst):
        if value == ciag_znakow:
            acc.append(index1)
    return acc

if __name__ == '__main__':
    input_wej = input("Podaj ciąg znaków: ")
    ciag= input("Czego szukasz: ")
    index = find_all_indexes(input_wej, ciag)
    if index:
        print(f"Szukane indeksy to {index}")
    else:
        print(f"Nic nie znaleziono")

