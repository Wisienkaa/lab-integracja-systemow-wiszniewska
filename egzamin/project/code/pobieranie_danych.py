import requests
import json

def pobierz_dane_z_api(url):
    # Wysłanie żądania GET do API
    response = requests.get(url)

    # Sprawdzenie, czy żądanie zostało pomyślnie wykonane (kod odpowiedzi 200)
    if response.status_code == 200:
        # Parsowanie danych JSON
        dane_json = response.json()
        return dane_json
    else:
        # Wyświetlenie komunikatu o błędzie
        print(f"Błąd przy pobieraniu danych. Kod odpowiedzi: {response.status_code}")
        return None

def zapisz_dane_do_pliku(dane, nazwa_pliku):
    with open(nazwa_pliku, 'w') as file:
        json.dump(dane, file, indent=4)

if __name__ == "__main__":
    # URL API
    url_api = "https://jsonplaceholder.typicode.com/photos"

    dane_api = pobierz_dane_z_api(url_api)

    if dane_api:
        nazwa_pliku = "dane_z_api.json"

        zapisz_dane_do_pliku(dane_api, nazwa_pliku)

        print(f"Dane z API zostały zapisane do pliku: {nazwa_pliku}")
