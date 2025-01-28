FROM golang:1.20-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app

COPY backend/ /app



RUN go build -o backend .  # Buduje plik wykonywalny

USER appuser  # Używa użytkownika 'appuser' do uruchamiania aplikacji

CMD ["./backend"]  # Uruchamia plik 'backend'
