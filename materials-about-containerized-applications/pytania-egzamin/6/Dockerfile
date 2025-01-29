FROM python:3.9-slim

# Ustawienie katalogu roboczego w kontenerze
WORKDIR /app

COPY app/requirements.txt /app/

RUN pip install --no-cache-dir -r requirements.txt

# Skopiowanie całego katalogu 'app' do katalogu roboczego w kontenerze
COPY app/ /app/

# Skopiowanie pliku README do katalogu roboczego
ADD hej.txt /app/

CMD ["python", "main.py"]