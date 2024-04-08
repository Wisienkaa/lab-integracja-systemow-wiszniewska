"""3. Napisz program, który szuka określonego ciągu znaków w łańcuchu znakowym i zwraca indeks pierwszego wystąpienia ciągu lub -1, gdy nie ma takiego ciągu.
   Hint: skorzystaj z funkcji find()."""
        

def znajdz_indeks(tekst, ciag_znakow):
    return tekst.find(ciag_znakow)

if __name__ == '__main__':
    input_wej = input("Podaj ciąg znaków: ")
    ciag= input("Czego szukasz: ?")
    if znajdz_indeks(input_wej, ciag)!= -1 :
        print(f"Ciąg znaków '{ciag}' został znaleziony na indeksie {znajdz_indeks(input_wej, ciag)}.")
    else:
       print(f"Ciąg znaków '{ciag}' nie został znaleziony.")
else:
    print("Wpisałeś za mało znaków")  

