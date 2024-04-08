""" 2. Napisz program, który sprawdza czy wczytany łańcuch znakowy jest liczbą lub nie. 
   Muszą być wczytane co najmniej dwa znaki.
   Hint: skorzystaj z funkcji all()."""
def check_character(our_input):
    if our_input.isdigit():
        return True
    else:
        return False
        

if __name__ == '__main__':
    input_wej = input("Podaj conajmniej dwa znaki: ")
    if all(znak.isdigit() for znak in input_wej) and len(input_wej) >=2 :
        print("Podane znaki są liczbą")
    else:
        print("Podany ciąg nie jest liczbą")
else:
    print("Wpisałeś za mało znaków")  
