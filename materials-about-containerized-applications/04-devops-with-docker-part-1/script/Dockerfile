# Start from the Ubuntu 22.04 base image
FROM ubuntu:22.04

# Install curl
RUN apt-get update && apt-get install -y curl

# Copy the script to the container
COPY script.sh /usr/local/bin/script.sh

# Make the script executable
RUN chmod +x /usr/local/bin/script.sh

# Set the script as the default command
CMD ["/usr/local/bin/script.sh"]
