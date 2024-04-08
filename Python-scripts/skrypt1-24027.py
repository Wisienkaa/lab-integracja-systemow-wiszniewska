#Pierwszy sposób 
""" Napisz program (na dwa sposoby), który sprawdza czy wczytany pojedynczy znak jest cyfrą lub nie.
    Jeśli wczytamy więcej znaków, należy wziąć tylko pierwszy.
   Hint: skorzystaj z funkcji isdigit() i isinstance()."""
def check_character(our_input):
    if our_input.isdigit():
        return True
    else:
        return False

if __name__ == '__main__':
    input_wej = input("Podaj znak lub znaki: ")
    if check_character(input_wej[0]) and len(input_wej) > 0:
        print("Podany znak jest cyfrą")
    else:
        print("Podany znak nie jest cyfrą")
    print(check_character(input_wej))
else:
    print("Nie wpisałeś żadnego znaku")  


#Drugi sposób
    # txt='adwda'
    #isinstance(txt, int)  wypluje False
    #isistance(txt, string)  wypluje True