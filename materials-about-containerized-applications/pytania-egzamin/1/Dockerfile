# official Python image
FROM python:3.9-slim

WORKDIR /app

# Copy the code folder from the host to the container
COPY code /app/code

# Set the default command to run the Python script
CMD ["python", "/app/code/script.py"]