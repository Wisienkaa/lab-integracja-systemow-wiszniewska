# Użyj obrazu Ruby jako bazowego
FROM ruby:2.7

# Instalacja zależności systemowych
RUN sed -i 's|http://deb.debian.org/debian|http://ftp.de.debian.org/debian|g' /etc/apt/sources.list && \
    apt-get update -qq && \
    apt-get install -y build-essential libpq-dev nodejs nano && \
    rm -rf /var/lib/apt/lists/*

# Instalacja Bundlera w wersji 2.4.22
RUN gem install bundler -v 2.4.22

# Ustawienie katalogu roboczego
WORKDIR /usr/src/app

# Kopiowanie Gemfile i Gemfile.lock
COPY Gemfile Gemfile.lock ./

# Skopiuj plik database.yml przed instalacją gemów
COPY config/database.yml ./config/database.yml

# Instalacja gemów
RUN bundle install

# Skopiuj resztę aplikacji
COPY . .

# Ustawienie domyślnego polecenia
CMD ["bundle", "exec", "rails", "server", "-b", "0.0.0.0"]
