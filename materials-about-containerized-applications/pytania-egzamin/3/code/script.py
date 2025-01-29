with open("/app/code/output.txt", "w") as file:
    file.write("To jest plik wygenerowany w kontenerze.\n")
print("Plik output.txt został zapisany w kontenerze.")
