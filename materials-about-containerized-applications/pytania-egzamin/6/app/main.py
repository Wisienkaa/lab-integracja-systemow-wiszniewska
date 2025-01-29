import os

print("Zawartość katalogu /app/:")
for file in os.listdir("/app/"):
    print("-", file)
