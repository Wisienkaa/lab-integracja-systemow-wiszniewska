"""   
10. Napisz program, który korzystająć z metody chr() wygeneruje łańcuch znakowy z alfabetem, czyli 'abc....xyz'. 
    Do pliku 'alfabet1-numeralbumu.txt' zapisz wygenerowany łańcuch znakowy, a do pliku 'alfabet2-numeralbumu.txt' zapisz litery z ww. łańcucha znakowego, 
    tylko że każda litera ma się znaleźć w osobnej linii w pliku.
    Hint: oprócz funkcji write() skorzystaj również z menedżera kontekstu 'with', żeby nie zapomnieć o zamknięciu pliku.  
"""

def ascii_to_letters(number):
    return chr(number)

if __name__ == '__main__':
    letters = []
    for i in range(65, 91):
        letters.append(ascii_to_letters(i))
    letters_string = ''.join(letters)


    with open("alfabet1-24027.txt", "w") as file:
        file.write(letters_string)

    with open("alfabet2-24027.txt", "w") as file:
        for literka in letters_string:
            file.write(f"{literka}\n")   