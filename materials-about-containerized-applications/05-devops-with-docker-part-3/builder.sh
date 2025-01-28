#!/bin/bash

GITHUB_REPO=$1
DOCKER_REPO=$2

if [ -z "$GITHUB_REPO" ] || [ -z "$DOCKER_REPO" ]; then
  echo "Usage: $0 <github-repo> <docker-repo>"
  exit 1
fi

# Klonowanie repozytorium
git clone https://github.com/$GITHUB_REPO app
cd app

# Budowanie obrazu
docker build -t $DOCKER_REPO .

# Logowanie do Docker Hub
echo "$DOCKER_PWD" | docker login -u "$DOCKER_USER" --password-stdin

# Publikowanie obrazu
docker push $DOCKER_REPO

# Czyszczenie
cd ..
rm -rf app
