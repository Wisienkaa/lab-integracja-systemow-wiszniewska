FROM node:16-alpine

# Tworzenie użytkownika nierootowego
RUN addgroup -S appgroup && adduser -S appuser -G appgroup && \
    mkdir /app && chown -R appuser:appgroup /app

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install --production

COPY . .

USER appuser

EXPOSE 3000
CMD ["npm", "start"]
