def read_and_display_file(file_path):
    try:
        with open(file_path, 'r') as file:
            data = file.read()
            print("Zawartość pliku:")
            print(data)
    except FileNotFoundError:
        print(f"Plik '{file_path}' nie istnieje.")


file_path = 'C:\\Users\\kotko\\lab-integracja-systemow-wiszniewska\\egzamin\\project\\example.txt' 
read_and_display_file(file_path)
